package eu.xenit.alfred.initializr.generator;

import io.spring.initializr.generator.InvalidProjectRequestException;
import io.spring.initializr.generator.ProjectGenerator;
import io.spring.initializr.generator.ProjectRequest;
import io.spring.initializr.generator.ProjectResourceLocator;
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

        generateGitIgnore(dir, request);

        // Create -repo folder structure
        File repoDir = new File(dir, request.getName() + "-repo");
        repoDir.mkdirs();

        write(new File(repoDir, "build.gradle"), "alfred-sdk/repo-build.gradle.mustache", model);

//        String applicationName = request.getApplicationName();
        File src = this.getCodeLocation(repoDir, "main", request);
        src.mkdirs();
//        String extension = ("kotlin".equals(language) ? "kt" : language);
//        write(new File(src, applicationName + "." + extension),
//                "Application." + extension, model);
//
//        if ("war".equals(request.getPackaging())) {
//            String fileName = "ServletInitializer." + extension;
//            write(new File(src, fileName), fileName, model);
//        }
//
        File test = this.getCodeLocation(repoDir, "test", request);
        test.mkdirs();
//        setupTestModel(request, model);
//        write(new File(test, applicationName + "Tests." + extension),
//                "ApplicationTests." + extension, model);
//
//        File resources = new File(dir, "src/main/resources");
//        resources.mkdirs();
//        writeText(new File(resources, "application.properties"), "");
//
//        if (request.hasWebFacet()) {
//            new File(dir, "src/main/resources/templates").mkdirs();
//            new File(dir, "src/main/resources/static").mkdirs();
//        }

        return rootDir;
    }

    protected File getCodeLocation(File projectDir, String type, ProjectRequest request)
    {
        String language = request.getLanguage();
        return new File(new File(projectDir, "src/" + type + "/" + language),
                request.getPackageName().replace(".", "/"));
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
