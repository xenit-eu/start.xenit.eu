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
                .hasPlugin("eu.xenit.docker-alfresco"); // only when we package as docker ?

        result.platformGradleBuild()
                .hasPlugin("eu.xenit.alfresco")
                .hasPlugin("eu.xenit.amp")
                .contains("alfrescoVersion = ")
                .hasDependency("alfrescoProvided", quote("org.alfresco:alfresco-repository:${alfrescoVersion}"));

    }

    private static String quote(String string) {
        return "\"" + string + "\"";
    }
}