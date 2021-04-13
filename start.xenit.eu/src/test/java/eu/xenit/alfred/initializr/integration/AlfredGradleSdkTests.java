package eu.xenit.alfred.initializr.integration;

import eu.xenit.alfred.initializr.asserts.build.gradle.GradleMultiProjectAssert;
import io.spring.initializr.web.project.ProjectRequest;
import org.junit.Test;

public class AlfredGradleSdkTests extends BaseGeneratorTests {

    @Test
    public void testDefaultBuild() {
        ProjectRequest request = createProjectRequest();

        GradleMultiProjectAssert result = generateGradleBuild(request);

        result.rootGradleBuild()
                .contains("alfrescoVersion = '6.1.2-ga'\n");

        result.platformBuildGradle()
                .hasPlugin("eu.xenit.alfresco")
                .hasPlugin("eu.xenit.amp")

                .hasDependency("alfrescoProvided", quote("org.alfresco:content-services-community:${alfrescoVersion}"));

    }
}