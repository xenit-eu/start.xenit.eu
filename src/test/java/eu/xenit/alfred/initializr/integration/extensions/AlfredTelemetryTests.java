package eu.xenit.alfred.initializr.integration.extensions;

import eu.xenit.alfred.initializr.asserts.build.gradle.GradleMultiProjectAssert;
import eu.xenit.alfred.initializr.asserts.docker.DockerComposeAssert;
import eu.xenit.alfred.initializr.asserts.docker.DockerComposeProjectAssert;
import eu.xenit.alfred.initializr.integration.BaseGeneratorTests;
import io.spring.initializr.web.project.ProjectRequest;
import org.junit.Test;

public class AlfredTelemetryTests extends BaseGeneratorTests {

    @Test
    public void testAlfredTelemetryGradleDependencies() {
        ProjectRequest request = createProjectRequest("alfred-telemetry");

        GradleMultiProjectAssert result = generateGradleBuild(request);

        result.rootGradleBuild()
                .hasDependency("alfrescoAmp",
                        quote("eu.xenit.alfred.telemetry:alfred-telemetry-platform:0.1.0@amp"));

        result.platformGradleBuild()
                .hasDependency("alfrescoProvided", quote("io.micrometer:micrometer-core:1.0.6"));
    }
}
