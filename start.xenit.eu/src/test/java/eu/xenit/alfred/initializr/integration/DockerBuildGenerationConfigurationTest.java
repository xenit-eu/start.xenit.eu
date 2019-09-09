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
                .doesNotHavePlugin("eu.xenit.docker-alfresco");

        result.platformBuildGradle()
                .doesNotHavePlugin("eu.xenit.docker-alfresco");

        result.platformDockerBuildGradle()
                .hasPlugin("eu.xenit.docker-alfresco")
                .hasDependency("alfrescoAmp", "project(path: ':demo-platform', configuration: 'amp')")
        ;


    }
}