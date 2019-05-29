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

        result.rootGradleBuild()
                .hasDependency("alfrescoAmp", quote("eu.xenit:alfresco-dynamic-extensions-repo-52:"+DE_RELEASE+"@amp"));


        result.platformGradleBuild()
                .hasDependency("implementation", quote("eu.xenit:alfresco-dynamic-extensions-repo-52:"+DE_RELEASE));

    }

    private static String quote(String string) {
        return quote(string, "'");
    }

    private static String quote(String string, String quote) {
        return quote + string + quote;
    }
}