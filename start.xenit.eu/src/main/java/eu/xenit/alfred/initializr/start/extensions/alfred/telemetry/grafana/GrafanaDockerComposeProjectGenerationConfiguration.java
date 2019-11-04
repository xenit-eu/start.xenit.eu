package eu.xenit.alfred.initializr.start.extensions.alfred.telemetry.grafana;


import eu.xenit.alfred.initializr.generator.condition.ConditionalOnRequestedFacet;
import eu.xenit.alfred.initializr.generator.docker.compose.DockerComposeFiles;
import eu.xenit.alfred.initializr.generator.docker.compose.DockerComposeCustomizer;
import eu.xenit.alfred.initializr.start.sdk.alfred.compose.config.DockerComposeGradlePluginConfigurationCustomizer;
import eu.xenit.alfred.initializr.generator.docker.compose.DockerComposeYmlWriterDelegate;
import eu.xenit.alfred.initializr.start.extensions.alfred.telemetry.grafana.provisioning.GrafanaProvisioningWriterDelegate;
import eu.xenit.alfred.initializr.generator.project.LocationStrategy;
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
                .image("hub.xenit.eu/public/grafana:5")
                //.volumes("./grafana:/etc/grafana:z")
                .ports("3000");
    }

    @Bean
    GrafanaDockerComposeYmlContributor grafanaDockerComposeYmlContributor(DockerComposeFiles compose,
            DockerComposeYmlWriterDelegate writer,
            IndentingWriterFactory indentingWriterFactory,
            LocationStrategy locationStrategy) {
        return new GrafanaDockerComposeYmlContributor(compose, writer, indentingWriterFactory, locationStrategy);
    }

    @Bean
    @ConditionalOnBuildSystem(GradleBuildSystem.ID)
    DockerComposeGradlePluginConfigurationCustomizer configureGrafanaDockerComposeGradlePlugin(
            GrafanaDockerComposeYmlContributor composeYml) {
        return new GrafanaDockerComposeGradlePluginConfigurationCustomizer(composeYml.composeFilename());
    }

    @Bean
    public GrafanaProvisioningWriterDelegate grafanaProvisioningWriterDelegate()
    {
        return new GrafanaProvisioningWriterDelegate();
    }

}
