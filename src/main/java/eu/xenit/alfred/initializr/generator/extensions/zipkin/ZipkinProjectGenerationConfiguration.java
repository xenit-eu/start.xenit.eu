package eu.xenit.alfred.initializr.generator.extensions.zipkin;


import eu.xenit.alfred.initializr.generator.condition.ConditionalOnRequestedFacet;
import eu.xenit.alfred.initializr.generator.docker.compose.DockerCompose;
import eu.xenit.alfred.initializr.generator.docker.compose.DockerComposeGradlePluginConfigurationCustomizer;
import eu.xenit.alfred.initializr.generator.docker.compose.DockerComposeLocationStrategy;
import eu.xenit.alfred.initializr.generator.docker.compose.DockerComposeYmlWriterDelegate;
import io.spring.initializr.generator.buildsystem.gradle.GradleBuildSystem;
import io.spring.initializr.generator.condition.ConditionalOnBuildSystem;
import io.spring.initializr.generator.io.IndentingWriterFactory;
import io.spring.initializr.generator.project.ProjectGenerationConfiguration;
import io.spring.initializr.metadata.support.MetadataBuildItemResolver;
import org.springframework.context.annotation.Bean;

@ProjectGenerationConfiguration
@ConditionalOnRequestedFacet("zipkin")
public class ZipkinProjectGenerationConfiguration {
    private final MetadataBuildItemResolver resolver;

    public ZipkinProjectGenerationConfiguration(MetadataBuildItemResolver resolver) {
        this.resolver = resolver;
    }

    @Bean
    public ZipkinDockerComposeCustomizer zipkinDockerComposeCustomizer() {
        return new ZipkinDockerComposeCustomizer();
    }

    @Bean
    public ZipkinDockerComposeYmlContributor ZipkinDockerComposeYmlContributor(DockerCompose compose,
            DockerComposeYmlWriterDelegate writer,
            IndentingWriterFactory indentingWriterFactory,
            DockerComposeLocationStrategy locationStrategy) {
        return new ZipkinDockerComposeYmlContributor(compose, writer, indentingWriterFactory, locationStrategy);
    }

    @Bean
    @ConditionalOnBuildSystem(GradleBuildSystem.ID)
    DockerComposeGradlePluginConfigurationCustomizer configureZipkinDockerComposeGradlePlugin(
            ZipkinDockerComposeYmlContributor composeYml) {
        return (configuration) -> configuration.getUseComposeFiles().add(composeYml.composeFilename());
    }

}
