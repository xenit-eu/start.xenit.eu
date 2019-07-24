package eu.xenit.alfred.initializr.integration.extensions;

import eu.xenit.alfred.initializr.asserts.build.gradle.GradleMultiProjectAssert;
import eu.xenit.alfred.initializr.asserts.docker.DockerComposeAssert;
import eu.xenit.alfred.initializr.asserts.docker.DockerComposeProjectAssert;
import eu.xenit.alfred.initializr.asserts.grafana.GrafanaProvisioningAssert;
import eu.xenit.alfred.initializr.integration.BaseGeneratorTests;
import io.spring.initializr.web.project.ProjectRequest;
import org.junit.Test;

public class GrafanaTests extends BaseGeneratorTests {

    @Test
    public void testGradleBuildUsesGrafanaCompose() {
        ProjectRequest request = createProjectRequest("micrometer-graphite");

        GradleMultiProjectAssert result = generateGradleBuild(request);

        result.rootGradleBuild()
                .contains("docker-compose-grafana.yml")
                .contains(
                        "useComposeFiles = ['docker-compose.yml', 'docker-compose-graphite.yml', 'docker-compose-grafana.yml']");
    }

    @Test
    public void dockerComposeGrafanaTests() {
        ProjectRequest request = createProjectRequest("micrometer-graphite");
        DockerComposeProjectAssert composeProject = generateCompose(request);

        // Expecting 3 compose files: main, graphite, grafana
        composeProject.assertSize(3);

        // Check the -grafana compose file
        DockerComposeAssert composeGrafana = composeProject.assertDockerCompose("grafana");

        composeGrafana
                .isNotBlank()
                .startsWith("version:")
                .hasService("grafana");
    }

    @Test
    public void graphiteProvisioning() {
        ProjectRequest request = createProjectRequest("micrometer-graphite");
        GrafanaProvisioningAssert grafanaProvisioning = generateGrafanaProvisioning(request);

        grafanaProvisioning
                .doesNotHaveDataSource("prometheus.yml")
                .doesNotHaveDataSource("graphite")
                .assertDataSource("graphite.yml", ds -> ds
                        .isNotBlank()
                        .startsWith("apiVersion: 1")
                        .contains("datasources:")
                        .contains("name: graphite")
                        .contains("access: proxy")
                        .contains("url: http://graphite-api:8000")
                        .contains("version: 1")
                )

                .doesNotHaveDashboard("prometheus.yml")
                .doesNotHaveDashboard("graphite")
                .assertDashboard("graphite.yml", db -> db
                        .isNotBlank()
                        .startsWith("apiVersion: 1")
                        .contains("name: 'Graphite dashboard'")
                        .contains("folder: ''")
                        .contains("type: file")
                        .contains("editable: true")
                        .contains("path: /etc/grafana/provisioning/dashboards/graphite")
                );


    }

}
