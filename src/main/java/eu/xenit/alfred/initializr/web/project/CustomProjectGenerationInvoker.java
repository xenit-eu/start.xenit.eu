package eu.xenit.alfred.initializr.web.project;

import io.spring.initializr.generator.buildsystem.BuildItemResolver;
import io.spring.initializr.generator.buildsystem.BuildWriter;
import io.spring.initializr.generator.project.ProjectAssetGenerator;
import io.spring.initializr.generator.project.ProjectDescription;
import io.spring.initializr.generator.project.ProjectGenerationContext;
import io.spring.initializr.generator.project.ProjectGenerationException;
import io.spring.initializr.generator.project.ProjectGenerator;
import io.spring.initializr.generator.project.ResolvedProjectDescription;
import io.spring.initializr.generator.project.contributor.ProjectContributor;
import io.spring.initializr.metadata.InitializrMetadata;
import io.spring.initializr.metadata.InitializrMetadataProvider;
import io.spring.initializr.metadata.support.MetadataBuildItemResolver;
import io.spring.initializr.web.project.ProjectFailedEvent;
import io.spring.initializr.web.project.ProjectGeneratedEvent;
import io.spring.initializr.web.project.ProjectGenerationInvoker;
import io.spring.initializr.web.project.ProjectRequest;
import io.spring.initializr.web.project.ProjectRequestToDescriptionConverter;
import io.spring.initializr.web.project.WebProjectRequest;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * A {@link ProjectGenerationInvoker} consumes a {@link ProjectRequest} and invokes the project generation API. This
 * intermediate layer is used by both the web-frontend and the integration tests
 *
 * The base class has support for generating both a full project or the build-system: build.gradle for Gradle or pom.xml
 * for Maven.
 *
 * Full project generation happens by requesting all the {@link ProjectContributor}s to contribute their part. The scope
 * of the genration can be limited, by not going over {@link ProjectContributor}, but simply select a single (or
 * subset?), using a marker interface like {@link BuildWriter}.
 *
 * This {@link CustomProjectGenerationInvoker} attempts to generalize that concept.
 */
public class CustomProjectGenerationInvoker extends ProjectGenerationInvoker {

    private final ApplicationContext parentApplicationContext;
    private final ApplicationEventPublisher eventPublisher;
    private final ProjectRequestToDescriptionConverter converter;

    public CustomProjectGenerationInvoker(ApplicationContext parentApplicationContext,
            ApplicationEventPublisher eventPublisher,
            ProjectRequestToDescriptionConverter converter) {
        super(parentApplicationContext, eventPublisher, converter);
        this.parentApplicationContext = parentApplicationContext;
        this.eventPublisher = eventPublisher;
        this.converter = converter;
    }


    /**
     * Invokes the project generation API that knows how to just write *all* build-related files.
     *
     * {@link WebProjectRequest}.
     *
     * @param request the project request
     * @return the {@link BuildGenerationResult}
     */
    public BuildGenerationResult invokeProjectBuildGeneration(ProjectRequest request) {
        InitializrMetadata metadata = this.parentApplicationContext.getBean(InitializrMetadataProvider.class).get();
        try {
            ProjectDescription projectDescription = this.converter.convert(request, metadata);
            ProjectGenerator projectGenerator = new ProjectGenerator(
                    (projectGenerationContext) ->
                            customizeProjectGenerationContext(projectGenerationContext, metadata));

            return projectGenerator.generate(projectDescription, generateBuildAssets(request));
        } catch (ProjectGenerationException ex) {
            publishProjectFailedEvent(request, metadata, ex);
            throw ex;
        }

    }



    public DockerComposeGenerationResultSet invokeProjectComposeGeneration(ProjectRequest request) {
        InitializrMetadata metadata = this.parentApplicationContext.getBean(InitializrMetadataProvider.class).get();
        try {
            ProjectDescription projectDescription = this.converter.convert(request, metadata);
            ProjectGenerator projectGenerator = new ProjectGenerator(
                    (projectGenerationContext) ->
                            customizeProjectGenerationContext(projectGenerationContext, metadata));

            return projectGenerator.generate(projectDescription, generateComposeAssets(request));
        } catch (ProjectGenerationException ex) {
            publishProjectFailedEvent(request, metadata, ex);
            throw ex;
        }
    }

    private ProjectAssetGenerator<BuildGenerationResult> generateBuildAssets(ProjectRequest request) {
        return (context) -> {
            Map<Path, String> result = new BuildAssetGenerator().generate(context);
            publishProjectGeneratedEvent(request, context);
            return new BuildGenerationResult(context.getBean(ResolvedProjectDescription.class), result);
        };
    }

    private ProjectAssetGenerator<DockerComposeGenerationResultSet> generateComposeAssets(ProjectRequest request) {
        return (context) -> {
            List<DockerComposeYml> result = new DockerComposeAssetGenerator().generate(context);
            publishProjectGeneratedEvent(request, context);
            return new DockerComposeGenerationResultSet(context.getBean(ResolvedProjectDescription.class), result);
        };
    }

    /**
     * Invokes the project generation API that knows how to just write the build file. Returns a directory containing
     * the project for the specified {@link WebProjectRequest}.
     *
     * @param request the project request
     * @return the generated build content
     */
    public byte[] invokeBuildGeneration(ProjectRequest request) {

        InitializrMetadata metadata = this.parentApplicationContext.getBean(InitializrMetadataProvider.class).get();
        try {
            ProjectDescription projectDescription = this.converter.convert(request,
                    metadata);
            ProjectGenerator projectGenerator = new ProjectGenerator(
                    (projectGenerationContext) -> customizeProjectGenerationContext(
                            projectGenerationContext, metadata));
            return projectGenerator.generate(projectDescription, generateBuild(request, BuildWriter.class));
        } catch (ProjectGenerationException ex) {
            publishProjectFailedEvent(request, metadata, ex);
            throw ex;
        }
    }

    private ProjectAssetGenerator<byte[]> generateBuild(ProjectRequest request,
            Class<? extends BuildWriter> buildWriterClass) {
        return (context) -> {
            byte[] content = generateBuild(context, buildWriterClass);
            publishProjectGeneratedEvent(request, context);
            return content;
        };
    }

    private byte[] generateBuild(ProjectGenerationContext context, Class<? extends BuildWriter> buildWriterClass)
            throws IOException {
        ResolvedProjectDescription projectDescription = context
                .getBean(ResolvedProjectDescription.class);
        StringWriter out = new StringWriter();
        BuildWriter buildWriter = context.getBeanProvider(buildWriterClass)
                .getIfAvailable();
        if (buildWriter != null) {
            buildWriter.writeBuild(out);
            return out.toString().getBytes();
        } else {
            throw new IllegalStateException("No BuildWriter implementation found for "
                    + projectDescription.getLanguage());
        }
    }

    private void customizeProjectGenerationContext(
            AnnotationConfigApplicationContext context, InitializrMetadata metadata) {
        context.setParent(this.parentApplicationContext);
        context.registerBean(InitializrMetadata.class, () -> metadata);
        context.registerBean(BuildItemResolver.class, () -> new MetadataBuildItemResolver(
                metadata,
                context.getBean(ResolvedProjectDescription.class).getPlatformVersion()));
    }

    private void publishProjectGeneratedEvent(ProjectRequest request,
            ProjectGenerationContext context) {
        InitializrMetadata metadata = context.getBean(InitializrMetadata.class);
        ProjectGeneratedEvent event = new ProjectGeneratedEvent(request, metadata);
        this.eventPublisher.publishEvent(event);
    }

    private void publishProjectFailedEvent(ProjectRequest request,
            InitializrMetadata metadata, Exception cause) {
        ProjectFailedEvent event = new ProjectFailedEvent(request, metadata, cause);
        this.eventPublisher.publishEvent(event);
    }
}
