package eu.xenit.alfred.initializr.integration;

import eu.xenit.alfred.initializr.asserts.build.gradle.GradleMultiProjectAssert;
import eu.xenit.alfred.initializr.asserts.docker.DockerComposeProjectAssert;
import eu.xenit.alfred.initializr.start.extensions.alfred.telemetry.AlfredTelemetryConstants;
import eu.xenit.alfred.initializr.start.extensions.alfred.telemetry.AlfredTelemetryConstants;
import eu.xenit.alfred.initializr.start.extensions.alfred.telemetry.AlfredTelemetryConstants.Micrometer;
import io.spring.initializr.web.project.ProjectRequest;
import org.junit.Test;

public class DockerComposeTests extends BaseGeneratorTests {

    @Test
    public void testDefaultBuild() {
        ProjectRequest request = createProjectRequest();
        DockerComposeProjectAssert result = generateCompose(request);

        result.assertDockerCompose()
                .isNotBlank()
                .startsWith("version:")
                .contains("alfresco:");
    }

    @Test
    public void dockerComposeConfiguration() {
        // 'Micrometer.Graphite.ID should pull in additionally:
        // - docker-compose-graphite.yml
        // - docker-compose-grafana.yml

        ProjectRequest request = createProjectRequest(Micrometer.Graphite.ID);
        GradleMultiProjectAssert gradle = generateGradleBuild(request);

        gradle.rootGradleBuild()
                .assertTask("dockerCompose", dockerCompose -> {
                    dockerCompose.assertAttribute("useComposeFiles",
                            "['docker-compose.yml', 'docker-compose-graphite.yml', 'docker-compose-grafana.yml']");
                    dockerCompose.assertAttribute("dockerComposeWorkingDirectory", "'docker-compose/'");
                });

    }

}