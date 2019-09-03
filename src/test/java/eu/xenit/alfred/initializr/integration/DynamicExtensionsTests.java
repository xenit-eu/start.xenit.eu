package eu.xenit.alfred.initializr.integration;

import eu.xenit.alfred.initializr.asserts.build.gradle.GradleMultiProjectAssert;
import eu.xenit.alfred.initializr.asserts.build.gradle.GradleTaskAssertion;
import io.spring.initializr.web.project.ProjectRequest;
import org.assertj.core.api.AbstractCharSequenceAssert;
import org.junit.Test;

public class DynamicExtensionsTests extends BaseGeneratorTests {

    @Test
    public void testDefaultBuild() {
        ProjectRequest request = createProjectRequest("dynamic-extensions");

        GradleMultiProjectAssert result = generateGradleBuild(request);

        result.rootGradleBuild()
                .hasDependency("alfrescoAmp", quote("eu.xenit:alfresco-dynamic-extensions-repo-52:${dynamicExtensionsVersion}@amp"));


        // Only packaging .amp
        result.platformGradleBuild()
                .hasPlugin("eu.xenit.de")
                .assertTask("sourceSets", sourceSets -> {
                   sourceSets.isNotBlank();
                   sourceSets.nested("main", main -> {
                       main.nested("amp", amp -> {
                           amp.isEqualTo("dynamicExtension()");
                       });
                   });
                });


    }

    @Test
    public void testDynamicExtensionsWithJarPackaging() {
        ProjectRequest request = createProjectRequest("dynamic-extensions");
        request.setPackaging("jar");

        GradleMultiProjectAssert result = generateGradleBuild(request);

        result.rootGradleBuild()
                .hasDependency("alfrescoAmp", quote("eu.xenit:alfresco-dynamic-extensions-repo-52:${dynamicExtensionsVersion}@amp"));


        // This should NOT be present for .jar packaging
        result.platformGradleBuild()
                .hasPlugin("eu.xenit.de")
                .assertTask("sourceSets", sourceSets -> {
                    sourceSets.nested("main", main -> {
                        main.nested("amp", amp -> {
                            amp.doesNotContain("dynamicExtension()");
                        });
                    });
                });
    }
}