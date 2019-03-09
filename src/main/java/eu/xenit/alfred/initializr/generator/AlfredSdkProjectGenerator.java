package eu.xenit.alfred.initializr.generator;

import eu.xenit.alfred.initializr.generator.model.Annotations;
import eu.xenit.alfred.initializr.generator.model.Imports;
import io.spring.initializr.generator.InvalidProjectRequestException;
import io.spring.initializr.generator.ProjectGenerator;
import io.spring.initializr.generator.ProjectRequest;
import io.spring.initializr.generator.ProjectResourceLocator;
import io.spring.initializr.metadata.InitializrMetadataProvider;
import io.spring.initializr.util.TemplateRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StreamUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Map;

public class AlfredSdkProjectGenerator extends ProjectGenerator {

    private static final Logger log = LoggerFactory.getLogger(ProjectGenerator.class);

    public AlfredSdkProjectGenerator(InitializrMetadataProvider metadataProvider) {
        super();

        this.setMetadataProvider(metadataProvider);
    }

    @Autowired
    private TemporaryFileSupport tempFileSupport = new TemporaryFileSupport();

    @Autowired
    private TemplateRenderer templateRenderer = new TemplateRenderer();

    @Autowired
    private ProjectResourceLocator projectResourceLocator = new ProjectResourceLocator();

    @Override
    protected Map<String, Object> resolveModel(ProjectRequest originalRequest) {
        Map<String, Object> model = super.resolveModel(originalRequest);

        if ("amp".equals(originalRequest.getPackaging())) {
            model.put("amp", true);
        }

        return model;
    }

    @Override
    public void cleanTempFiles(File dir) {
        this.tempFileSupport.cleanTempFiles(dir);
        super.cleanTempFiles(dir);
    }

    /**
     * Generate a project structure for the specified {@link ProjectRequest} and resolved
     * model.
     * @param request the project request
     * @param model the source model
     * @return the generated project structure
     */
    protected File generateProjectStructure(ProjectRequest request,
                                            Map<String, Object> model) {
        if (!isGradleBuild(request))
            throw new InvalidProjectRequestException("Project type '"+request.getBuild()+"' currently not supported");


        log.info("generateProjectStructure -> model:");
        model.forEach((key, value) -> log.info("-- {} -> {}", key, value));

        File rootDir;
        try {
            rootDir = File.createTempFile("tmp", "", this.tempFileSupport.getTemporaryDirectory());
        }
        catch (IOException ex) {
            throw new IllegalStateException("Cannot create temp dir", ex);
        }
        this.tempFileSupport.addTempFile(rootDir.getName(), rootDir);

        rootDir.delete();
        rootDir.mkdirs();

        File dir = initializerProjectDir(rootDir, request);


        // Root folder structure
        write(new File(dir, "build.gradle"), "alfred-sdk/root-build.gradle.mustache", model);
        write(new File(dir, "settings.gradle"), "alfred-sdk/settings.gradle.mustache", model);
        writeGradleWrapper(dir);

        // Docker Compose file
        write(new File(dir, "docker-compose.yml"), "alfred-sdk/docker-compose.yml.mustache", model);

        generateGitIgnore(dir, request);
        generateSubprojectRepo(request, model, dir);

//        generateSubprojectDocker(request, model, dir);

        return rootDir;
    }

    private void generateSubprojectDocker(ProjectRequest request, Map<String, Object> model, File rootDir) {

        // Create -docker folder structure
        File dir = new File(rootDir, request.getName() + "-docker");
        dir.mkdirs();

        write(new File(dir, "build.gradle"), "alfred-sdk/docker/build.gradle.mustache", model);

    }

    private void generateSubprojectRepo(ProjectRequest request, Map<String, Object> model, File dir) {
        // Create -repo folder structure
        File repoDir = new File(dir, request.getName() + "-repo");
        repoDir.mkdirs();

        write(new File(repoDir, "build.gradle"), "alfred-sdk/repo-build.gradle.mustache", model);

        File repoSrc = this.getCodeLocation(repoDir, "main", request.getLanguage(), request.getPackageName());
        repoSrc.mkdirs();

        if (request.getFacets().contains("webscripts")) {
            generateRepoWebscript(request, model, repoDir);
        }

        File ampSrcDir = this.getCodeLocation(repoDir, "main", "amp", "");
        ampSrcDir.mkdirs();

        write(new File(ampSrcDir, "module.properties"),
                "alfred-sdk/repo-module.properties.mustache", model);

        File ampModuleDir = this.getCodeLocation(repoDir, "main", "amp", "config.alfresco.module."+request.getName()+"-repo");
        ampModuleDir.mkdirs();
        write(new File(ampModuleDir, "module-context.xml"),
                "alfred-sdk/repo-module-context.xml.mustache", model);

        File test = this.getCodeLocation(repoDir, "test", request.getLanguage(), request.getPackageName());
        test.mkdirs();
    }

    private void generateRepoWebscript(ProjectRequest request, Map<String, Object> model, File projectDir) {
        File srcMainLang = this.getCodeLocation(projectDir, "main", request.getLanguage());
        String webscriptFilename = request.getApplicationName()+"Webscript."+request.getLanguage();
        File webscriptDir = new File(srcMainLang, request.getPackageName().replace(".", "/"));

        write(new File(webscriptDir, webscriptFilename),
                "webscripts/Webscript."+request.getLanguage()+".mustache", model);


//        write(new File(repoSrc, request.getApplicationName()+"WebScript."+request.getLanguage()),
//               "webscripts/Webscript."+request.getLanguage()+".mustache", model);
        File srcMainResources = this.getCodeLocation(projectDir, "main", "resources");
        File webscriptDescDir = new File(srcMainResources,
                "alfresco/extension/templates/webscripts/" + request.getPackageName().replace(".", "/") + "/");
        webscriptDescDir.mkdirs();

        write(new File(webscriptDescDir, request.getName() + ".get.desc.xml"),
               "webscripts/webscript.get.desc.xml.mustache", model);

        write(new File(webscriptDescDir, request.getName() + ".get.json.ftl"),
                "webscripts/webscript.get.json.ftl", model);



    }


    protected File getResourcesLocation(File projectDir, String sourceSet) {
        return this.getCodeLocation(projectDir, sourceSet, "resources");
    }

    protected File getCodeLocation(File projectDir, String sourceSet, String language)
    {
        return new File(projectDir, "src/" + sourceSet + "/" + language + "/");
    }

    @Deprecated
    protected File getCodeLocation(File projectDir, String sourceSet, String language, String packageName)
    {
        return new File(
                this.getCodeLocation(projectDir, sourceSet, language),
                packageName.replace(".", "/")
        );
    }


    @Override
    protected void setupApplicationModel(ProjectRequest request,
                                         Map<String, Object> model) {
        Imports imports = new Imports(request.getLanguage());
        Annotations annotations = new Annotations();

        // TODO check if we use Dynamic Extensions ?

        // AbstractWebScript webscripts:
//        imports.add("java.io.IOException")
//                .add("org.springframework.extensions.webscripts.AbstractWebScript")
//                .add("org.springframework.extensions.webscripts.WebScriptRequest")
//                .add("org.springframework.extensions.webscripts.WebScriptResponse");

        // DeclarativeWebScript
        imports.add("org.springframework.extensions.webscripts.Cache")
                .add("org.springframework.extensions.webscripts.DeclarativeWebScript")
                .add("org.springframework.extensions.webscripts.Status")
                .add("org.springframework.extensions.webscripts.WebScriptRequest")
                .add("java.util.HashMap")
                .add("java.util.Map");


        model.put("applicationImports", imports.toString());
        model.put("applicationAnnotations", annotations.toString());

    }

    private static boolean isGradleBuild(ProjectRequest request) {
        return "gradle".equals(request.getBuild());
    }

    private static boolean isMavenBuild(ProjectRequest request) {
        return "maven".equals(request.getBuild());
    }

    private File initializerProjectDir(File rootDir, ProjectRequest request) {
        if (request.getBaseDir() != null) {
            File dir = new File(rootDir, request.getBaseDir());
            dir.mkdirs();
            return dir;
        }
        else {
            return rootDir;
        }
    }

    private void writeGradleWrapper(File dir) {
        String gradlePrefix = "gradle4";

        writeTextResource(dir, "gradlew.bat", gradlePrefix + "/gradlew.bat");
        writeTextResource(dir, "gradlew", gradlePrefix + "/gradlew");

        File wrapperDir = new File(dir, "gradle/wrapper");
        wrapperDir.mkdirs();
        writeTextResource(wrapperDir, "gradle-wrapper.properties",
                gradlePrefix + "/gradle/wrapper/gradle-wrapper.properties");
        writeBinaryResource(wrapperDir, "gradle-wrapper.jar",
                gradlePrefix + "/gradle/wrapper/gradle-wrapper.jar");
    }

    private File writeBinaryResource(File dir, String name, String location) {
        return doWriteProjectResource(dir, name, location, true);
    }

    private File writeTextResource(File dir, String name, String location) {
        return doWriteProjectResource(dir, name, location, false);
    }

    private File doWriteProjectResource(File dir, String name, String location,
                                        boolean binary) {
        File target = new File(dir, name);
        if (binary) {
            writeBinary(target, this.projectResourceLocator
                    .getBinaryResource("classpath:project/" + location));
        }
        else {
            writeText(target, this.projectResourceLocator
                    .getTextResource("classpath:project/" + location));
        }
        return target;
    }

    private void writeText(File target, String body) {
        try (OutputStream stream = new FileOutputStream(target)) {
            StreamUtils.copy(body, Charset.forName("UTF-8"), stream);
        }
        catch (Exception ex) {
            throw new IllegalStateException("Cannot write file " + target, ex);
        }
    }

    private void writeBinary(File target, byte[] body) {
        try (OutputStream stream = new FileOutputStream(target)) {
            StreamUtils.copy(body, stream);
        }
        catch (Exception ex) {
            throw new IllegalStateException("Cannot write file " + target, ex);
        }
    }





}
