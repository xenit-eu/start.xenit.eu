package eu.xenit.alfred.initializr.generator.extensions.alfred.telemetry.grafana;


import eu.xenit.alfred.initializr.generator.condition.ConditionalOnRequestedFacet;
import eu.xenit.alfred.initializr.generator.docker.compose.DockerCompose;
import eu.xenit.alfred.initializr.generator.docker.compose.DockerComposeCustomizer;
import eu.xenit.alfred.initializr.generator.docker.compose.DockerComposeGradlePluginConfigurationCustomizer;
import eu.xenit.alfred.initializr.generator.docker.compose.DockerComposeLocationStrategy;
import eu.xenit.alfred.initializr.generator.docker.compose.DockerComposeYmlWriter;
import io.spring.initializr.generator.buildsystem.gradle.GradleBuildSystem;
import io.spring.initializr.generator.condition.ConditionalOnBuildSystem;
import io.spring.initializr.generator.io.IndentingWriterFactory;
import io.spring.initializr.generator.project.ProjectGenerationConfiguration;
import org.springframework.context.annotation.Bean;

@ProjectGenerationConfiguration
@ConditionalOnRequestedFacet("grafana")
public class GrafanaDockerComposeProjectGenerationConfiguration {

    @Bean
    DockerComposeCustomizer grafanaDockerComposeCustomizer() {
        return (compose) -> compose.file("grafana").getServices()
                .service("grafana")
                .image("hub.xenit.eu/grafana:5.0")
                .volumes("./grafana:/etc/grafana:z")
                .ports("3000");
    }

    @Bean
    GrafanaDockerComposeYmlContributor grafanaDockerComposeYmlContributor(DockerCompose compose,
            DockerComposeYmlWriter writer,
            IndentingWriterFactory indentingWriterFactory,
            DockerComposeLocationStrategy locationStrategy) {
        return new GrafanaDockerComposeYmlContributor(compose, writer, indentingWriterFactory, locationStrategy);
    }

    @Bean
    @ConditionalOnBuildSystem(GradleBuildSystem.ID)
    DockerComposeGradlePluginConfigurationCustomizer configureGrafanaDockerComposeGradlePlugin(
            GrafanaDockerComposeYmlContributor composeYml) {
        return new GrafanaDockerComposeGradlePluginConfigurationCustomizer(composeYml.composeFilename());
    }

}
