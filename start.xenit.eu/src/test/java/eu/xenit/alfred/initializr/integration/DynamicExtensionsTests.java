package eu.xenit.alfred.initializr.integration;

import eu.xenit.alfred.initializr.asserts.build.gradle.GradleMultiProjectAssert;
import io.spring.initializr.web.project.ProjectRequest;
import org.junit.Test;

public class DynamicExtensionsTests extends BaseGeneratorTests {

    private static final String DE_RELEASE = "2.0.1";

    @Test
    public void testDefaultBuild() {
        ProjectRequest request = createProjectRequest("dynamic-extensions");

        GradleMultiProjectAssert result = generateGradleBuild(request);

        // result.rootGradleBuild()
        // Should the DE-version number be in the root project ext ?

        result.platformBuildGradle()
                .hasDependency("implementation", quote("eu.xenit:alfresco-dynamic-extensions-repo:"+DE_RELEASE));

        // when creating platform docker image:
        result.platformDockerBuildGradle()
                .hasDependency("alfrescoAmp", quote("eu.xenit:alfresco-dynamic-extensions-repo:"+DE_RELEASE+"@amp"));


    }
}