package eu.xenit.alfred.initializr.web.project;

import io.spring.initializr.generator.buildsystem.BuildItemResolver;
import io.spring.initializr.generator.project.ProjectAssetGenerator;
import io.spring.initializr.generator.project.ProjectDescription;
import io.spring.initializr.generator.project.ProjectGenerationContext;
import io.spring.initializr.generator.project.ProjectGenerationException;
import io.spring.initializr.generator.project.ProjectGenerator;
import io.spring.initializr.metadata.InitializrMetadata;
import io.spring.initializr.metadata.InitializrMetadataProvider;
import io.spring.initializr.metadata.support.MetadataBuildItemResolver;
import io.spring.initializr.web.project.ProjectFailedEvent;
import io.spring.initializr.web.project.ProjectGeneratedEvent;
import io.spring.initializr.web.project.ProjectGenerationInvoker;
import io.spring.initializr.web.project.ProjectRequest;
import io.spring.initializr.web.project.ProjectRequestToDescriptionConverter;
import io.spring.initializr.web.project.WebProjectRequest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Invokes the project generation API. This is an intermediate layer that can consume a {@link ProjectRequest} and
 * trigger project generation based on the request.
 *
 * This customized invoker attempts to enhance the super class implementation on a few fronts:
 * <ul>
 *   <li>Generalize the project-generation invocation by making the {@link ProjectAssetGenerator<>} a parameter @{@link
 * #invokeGeneration}.</li>
 *   <li>Move the responsibility to publish a success|failed-event away from  of the {@link
 * ProjectAssetGenerator<>} and into the generalized @{@link #invokeGeneration}.</li>
 * </ul>
 *
 * @param <R> the concrete {@link ProjectRequest} type
 * @author Toon Geens
 */
public class CustomProjectGenerationInvoker<R extends ProjectRequest> extends ProjectGenerationInvoker<R> {

    private final ApplicationContext parentApplicationContext;
    private final ApplicationEventPublisher eventPublisher;
    private final ProjectRequestToDescriptionConverter<R> requestConverter;

    public CustomProjectGenerationInvoker(ApplicationContext parentApplicationContext,
            ProjectRequestToDescriptionConverter<R> requestConverter) {
        super(parentApplicationContext, requestConverter);
        this.parentApplicationContext = parentApplicationContext;
        this.eventPublisher = parentApplicationContext;
        this.requestConverter = requestConverter;
    }

    protected <G> G invokeGeneration(R request, ProjectAssetGenerator<G> assetGenerator) {
        InitializrMetadata metadata = this.parentApplicationContext.getBean(InitializrMetadataProvider.class).get();
        try {
            ProjectDescription description = this.requestConverter.convert(request, metadata);
            ProjectGenerator projectGenerator = new ProjectGenerator((projectGenerationContext) -> {
                customizeProjectGenerationContext(projectGenerationContext, metadata);
            });

            // decorate asset-generation with project-generation-success event-publisher
            assetGenerator = decorateWithProjectGenerationEventPublication(request, assetGenerator);
            G generated = projectGenerator.generate(description, assetGenerator);

            return generated;
        } catch (ProjectGenerationException ex) {
            publishProjectFailedEvent(request, metadata, ex);
            throw ex;
        }
    }

    private <A> ProjectAssetGenerator<A> decorateWithProjectGenerationEventPublication(R request,
            ProjectAssetGenerator<A> assetGenerator) {
        return context -> {
            A result = assetGenerator.generate(context);
            publishProjectGeneratedEvent(request, context);
            return result;
        };
    }

    /**
     * Invokes the project generation API that knows how to just write *all* build-related files.
     *
     * {@link WebProjectRequest}.
     *
     * @param request the project request
     * @return the {@link BuildGenerationResult}
     */
    public BuildGenerationResult invokeProjectBuildGeneration(R request) {
        return this.invokeGeneration(request, new BuildAssetGenerator());
    }

    /**
     * Invokes the project generation API that generates all docker-compose yml files.
     *
     * @param request the project request
     * @return the {@link DockerComposeGenerationResultSet}
     */
    public DockerComposeGenerationResultSet invokeProjectComposeGeneration(R request) {
        return this.invokeGeneration(request, new DockerComposeAssetGenerator());
    }

    /**
     * Invokes the project generation API that generates Grafana provisioning configuration
     *
     * @param request the project request
     * @return the {@link GrafanaProvisioningResult}
     */
    public GrafanaProvisioningResult invokeGrafanaProvisioningGeneration(R request) {
        return this.invokeGeneration(request, new GrafanaProvisioningAssetGenerator());
    }

    // Duplicating super class implementation because it's private
    private void customizeProjectGenerationContext(
            AnnotationConfigApplicationContext context, InitializrMetadata metadata) {
        context.setParent(this.parentApplicationContext);
        context.registerBean(InitializrMetadata.class, () -> metadata);
        context.registerBean(BuildItemResolver.class, () -> new MetadataBuildItemResolver(
                metadata,
                context.getBean(ProjectDescription.class).getPlatformVersion()));
    }

    // Duplicating super class implementation because it's private
    private void publishProjectGeneratedEvent(ProjectRequest request,
            ProjectGenerationContext context) {
        InitializrMetadata metadata = context.getBean(InitializrMetadata.class);
        ProjectGeneratedEvent event = new ProjectGeneratedEvent(request, metadata);
        this.eventPublisher.publishEvent(event);
    }

    // Duplicating super class implementation because it's private
    private void publishProjectFailedEvent(ProjectRequest request,
            InitializrMetadata metadata, Exception cause) {
        ProjectFailedEvent event = new ProjectFailedEvent(request, metadata, cause);
        this.eventPublisher.publishEvent(event);
    }
}
