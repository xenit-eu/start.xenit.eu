package eu.xenit.alfred.initializr.generator.extensions.alfred.telemetry.graphite;

import eu.xenit.alfred.initializr.generator.condition.ConditionalOnRequestedFacet;
import eu.xenit.alfred.initializr.generator.docker.compose.DockerCompose;
import eu.xenit.alfred.initializr.generator.docker.compose.DockerComposeCustomizer;
import eu.xenit.alfred.initializr.generator.docker.compose.DockerComposeGradlePluginConfigurationCustomizer;
import eu.xenit.alfred.initializr.generator.docker.compose.DockerComposeLocationStrategy;
import eu.xenit.alfred.initializr.generator.docker.compose.DockerComposeYmlWriterDelegate;
import eu.xenit.alfred.initializr.generator.extensions.alfred.telemetry.grafana.provisioning.GrafanaDashboardProvider;
import eu.xenit.alfred.initializr.generator.extensions.alfred.telemetry.grafana.provisioning.GrafanaDashboardProvider.GrafanaDashboardOptions;
import eu.xenit.alfred.initializr.generator.extensions.alfred.telemetry.grafana.provisioning.GrafanaDashboardsResourcesContributor;
import eu.xenit.alfred.initializr.generator.extensions.alfred.telemetry.grafana.provisioning.GrafanaDataSource;
import eu.xenit.alfred.initializr.generator.extensions.alfred.telemetry.grafana.provisioning.GrafanaProvisioning;
import eu.xenit.alfred.initializr.generator.extensions.alfred.telemetry.grafana.provisioning.GrafanaProvisioningContributor;
import eu.xenit.alfred.initializr.generator.extensions.alfred.telemetry.grafana.provisioning.GrafanaProvisioningWriterDelegate;
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

    public final static String GRAPHITE = "graphite";

    @Bean
    public GraphiteDockerComposeCustomizer graphiteDockerComposeCustomizer() {
        return new GraphiteDockerComposeCustomizer();
    }

    @Bean
    public GraphiteDockerComposeYmlContributor graphiteDockerComposeContributor(DockerCompose compose,
            DockerComposeYmlWriterDelegate writer,
            IndentingWriterFactory indentingWriterFactory,
            DockerComposeLocationStrategy locationStrategy) {
        return new GraphiteDockerComposeYmlContributor(compose, writer, indentingWriterFactory, locationStrategy);
    }

    @Bean
    @ConditionalOnBuildSystem(GradleBuildSystem.ID)
    DockerComposeGradlePluginConfigurationCustomizer configureGraphiteDockerComposeGradlePlugin(
            GraphiteDockerComposeYmlContributor composeYml) {
        return (configuration) -> configuration.getUseComposeFiles().add(composeYml.composeFilename());
    }

    @Bean
    @ConditionalOnRequestedFacet("grafana")
    public GrafanaProvisioning graphiteGrafanaProvisioningConfig() {
        GrafanaProvisioning provisioning = new GrafanaProvisioning();
        provisioning.getDataSources().add(
                GrafanaDataSource.builder()
                        .name(GRAPHITE)
                        .type(GRAPHITE)
                        .access("proxy")
                        .url("http://graphite-api:8000")
                        .user("admin")
                        .password("admin")
                        .isDefault(false)
                        .editable(true)
                        .build()
        );

        provisioning.getDashboards().add(
                GrafanaDashboardProvider.builder()
                        .name("Graphite dashboard")
                        .editable(true)
                        .options(GrafanaDashboardOptions
                                .builder()
                                .path("/etc/grafana/provisioning/dashboards/graphite/")
                                .build())
                        .build()

        );
        return provisioning;
    }


    @Bean
    @ConditionalOnRequestedFacet("grafana")
    public GrafanaProvisioningContributor graphiteGrafanaProvisioningContributor(
            GrafanaProvisioning provisioning,
            GrafanaProvisioningWriterDelegate delegate, IndentingWriterFactory writerFactory,
            DockerComposeLocationStrategy locationStrategy) {
        return new GrafanaProvisioningContributor(GRAPHITE, provisioning, delegate, writerFactory, locationStrategy);
    }

    @Bean
    @ConditionalOnRequestedFacet("grafana")
    public DockerComposeCustomizer grafanaGraphiteConfigurationDockerComposeCustomizer() {
        return compose -> {

            DockerComposeModel composeGraphite = compose.file(GRAPHITE);
            ComposeServices services = composeGraphite.getServices();

            services.service("grafana")
                    .volumes(
                            "./grafana/provisioning/datasources/graphite.yml:/etc/grafana/provisioning/datasources/graphite.yml",
                            "./grafana/provisioning/dashboards/graphite.yml:/etc/grafana/provisioning/dashboards/graphite.yml",
                            "./grafana/provisioning/dashboards/graphite/:/etc/grafana/provisioning/dashboards/graphite/"
                    );

        };
    }

    @Bean
    @ConditionalOnRequestedFacet("grafana")
    public GrafanaDashboardsResourcesContributor graphiteDashboardsContributor(DockerComposeLocationStrategy locationStrategy) {
        return new GrafanaDashboardsResourcesContributor(GRAPHITE, locationStrategy);
    }


}
