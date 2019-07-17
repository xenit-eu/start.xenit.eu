package eu.xenit.alfred.initializr.generator.extensions.alfred.telemetry.graphite;

import eu.xenit.alfred.initializr.generator.condition.ConditionalOnRequestedFacet;
import eu.xenit.alfred.initializr.generator.docker.compose.DockerCompose;
import eu.xenit.alfred.initializr.generator.docker.compose.DockerComposeCustomizer;
import eu.xenit.alfred.initializr.generator.docker.compose.DockerComposeGradlePluginConfigurationCustomizer;
import eu.xenit.alfred.initializr.generator.docker.compose.DockerComposeLocationStrategy;
import eu.xenit.alfred.initializr.generator.docker.compose.DockerComposeYmlWriter;
import eu.xenit.alfred.initializr.model.docker.ComposeServices;
import eu.xenit.alfred.initializr.model.docker.DockerComposeModel;
import io.spring.initializr.generator.buildsystem.gradle.GradleBuildSystem;
import io.spring.initializr.generator.condition.ConditionalOnBuildSystem;
import io.spring.initializr.generator.condition.ConditionalOnRequestedDependency;
import io.spring.initializr.generator.io.IndentingWriterFactory;
import io.spring.initializr.generator.project.ProjectGenerationConfiguration;
import org.springframework.context.annotation.Bean;

@ProjectGenerationConfiguration
@ConditionalOnRequestedDependency("micrometer-graphite")
public class GraphiteProjectGenerationConfiguration {

    @Bean
    public GraphiteDockerComposeCustomizer graphiteDockerComposeCustomizer() {
        return new GraphiteDockerComposeCustomizer();
    }

    @Bean
    public GraphiteDockerComposeYmlContributor graphiteDockerComposeContributor(DockerCompose compose,
            DockerComposeYmlWriter writer,
            IndentingWriterFactory indentingWriterFactory,
            DockerComposeLocationStrategy locationStrategy) {
        return new GraphiteDockerComposeYmlContributor(compose, writer, indentingWriterFactory, locationStrategy);
    }

    @Bean
    @ConditionalOnRequestedFacet("grafana")
    public DockerComposeCustomizer grafanaGraphiteConfigurationDockerComposeCustomizer() {
        return compose -> {

            DockerComposeModel composeGraphite = compose.file("graphite");
            ComposeServices services = composeGraphite.getServices();

            services.service("grafana")
                    .volumes(
                            "./graphite/grafana/graphite-datasources.yml:/etc/grafana-provisioning/datasources/graphite-datasources.yml",
                            "./graphite/grafana/graphite-dashboards.yml:/etc/grafana-provisioning/dashboards/graphite-dashboards.yml",
                            "./graphite/grafana/dashboards:/etc/grafana-provisioning/dashboards/graphite"
                    );

        };
    }

    @Bean
    @ConditionalOnBuildSystem(GradleBuildSystem.ID)
    DockerComposeGradlePluginConfigurationCustomizer configureGraphiteDockerComposeGradlePlugin(
            GraphiteDockerComposeYmlContributor composeYml) {
        return (configuration) -> configuration.getUseComposeFiles().add(composeYml.composeFilename());
    }
}
