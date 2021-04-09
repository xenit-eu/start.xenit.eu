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
        request.setArtifactId("foo");

        DockerComposeProjectAssert result = generateCompose(request);

        result.assertDockerCompose()
                .isNotBlank()
                .startsWith("version:")
                .contains("alfresco:")
                .contains("image: ${FOO_PLATFORM_DOCKER_DOCKER_IMAGE:-demo:latest}");
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
                    dockerCompose.contains("fromProject project(':demo-platform-docker')");
                    dockerCompose.assertAttribute("useComposeFiles",
                            "['docker-compose.yml', 'docker-compose-graphite.yml', 'docker-compose-grafana.yml']");
                    dockerCompose.assertAttribute("dockerComposeWorkingDirectory", "'docker-compose/'");
                });
    }

    @Test
    public void testComposeUpConfiguration() {
        ProjectRequest request = createProjectRequest();
        GradleMultiProjectAssert gradle = generateGradleBuild(request);

        gradle.rootGradleBuild()
                .assertTask("composeUp", composeUp -> {
                    composeUp.doesNotContain("dependsOn ':demo-platform-docker:buildDockerImage'");
                    composeUp.assertNested("doFirst", doFirst -> {
                        doFirst.doesNotContain("dockerCompose.environment.put "
                                + "'DEMO_PLATFORM_DOCKER_IMAGE', "
                                + "project(':demo-platform-docker').buildDockerImage.getImageId()");
                    });
        });
    }

}