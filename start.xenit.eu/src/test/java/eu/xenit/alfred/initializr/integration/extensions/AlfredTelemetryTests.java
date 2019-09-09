package eu.xenit.alfred.initializr.integration.extensions;

import eu.xenit.alfred.initializr.asserts.build.gradle.GradleMultiProjectAssert;
import eu.xenit.alfred.initializr.integration.BaseGeneratorTests;
import eu.xenit.alfred.initializr.start.extensions.alfred.telemetry.AlfredTelemetryConstants.AlfredTelemetry;
import eu.xenit.alfred.initializr.start.extensions.alfred.telemetry.AlfredTelemetryConstants.Micrometer;
import io.spring.initializr.web.project.ProjectRequest;
import org.junit.Test;

public class AlfredTelemetryTests extends BaseGeneratorTests {

    @Test
    public void testAlfredTelemetryGradleDependencies() {
        ProjectRequest request = createProjectRequest("alfred-telemetry");

        GradleMultiProjectAssert result = generateGradleBuild(request);

        String telemetryVersion = AlfredTelemetry.VERSION;
        String micrometerVersion = Micrometer.VERSION;

        result.rootGradleBuild()
                .hasDependency("alfrescoAmp",
                        quote("eu.xenit.alfred.telemetry:alfred-telemetry-platform:${alfredTelemetryVersion}@amp"))
                .contains("alfredTelemetryVersion = \"" + telemetryVersion + "\"")
                .contains("micrometerVersion = \"" + micrometerVersion + "\"");

        result.platformBuildGradle()
                .hasDependency("alfrescoProvided", quote("io.micrometer:micrometer-core:${micrometerVersion}"));
    }
}
