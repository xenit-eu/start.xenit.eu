package eu.xenit.alfred.initializr.integration.extensions;

import eu.xenit.alfred.initializr.asserts.build.gradle.GradleMultiProjectAssert;
import eu.xenit.alfred.initializr.asserts.docker.DockerComposeAssert;
import eu.xenit.alfred.initializr.asserts.docker.DockerComposeProjectAssert;
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
                .contains("useComposeFiles = ['docker-compose.yml', 'docker-compose-graphite.yml', 'docker-compose-grafana.yml']");
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
}
