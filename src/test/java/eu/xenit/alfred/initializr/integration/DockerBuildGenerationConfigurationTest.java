package eu.xenit.alfred.initializr.integration;

import eu.xenit.alfred.initializr.asserts.build.gradle.GradleMultiProjectAssert;
import io.spring.initializr.web.project.ProjectRequest;
import org.junit.Test;

public class DockerBuildGenerationConfigurationTest extends BaseGeneratorTests {

    @Test
    public void testGradleBuild() {
        ProjectRequest request = createProjectRequest();

        GradleMultiProjectAssert result = generateGradleBuild(request);

        result.rootGradleBuild()
                .hasPlugin("eu.xenit.docker-alfresco")
                .contains("alfrescoVersion = ")
                .hasDependency(
                        "alfrescoAmp",
                        "project(path: ':demo-platform', configuration: 'amp')");


        result.platformGradleBuild()
                .doesNotHavePlugin("eu.xenit.docker-alfresco");

    }
}