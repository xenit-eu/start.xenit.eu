package eu.xenit.alfred.initializr.integration;

import eu.xenit.alfred.initializr.asserts.build.gradle.GradleMultiProjectAssert;
import eu.xenit.alfred.initializr.asserts.docker.DockerComposeProjectAssert;
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

        result.platformDockerBuildGradle()
                .hasDependency("baseAlfrescoWar", "\"org.alfresco:content-services-community:${alfrescoVersion}@war\"");

    }

}