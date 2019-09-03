package eu.xenit.alfred.initializr.integration;

import eu.xenit.alfred.initializr.asserts.build.gradle.GradleMultiProjectAssert;
import io.spring.initializr.web.project.ProjectRequest;
import org.junit.Test;

public class GradleGeneratorTests extends BaseGeneratorTests {

    @Test
    public void testGradleBuild() {
        ProjectRequest request = createProjectRequest();

        GradleMultiProjectAssert result = generateGradleBuild(request);
        result.rootGradleBuild().isNotBlank();
        result.platformBuildGradle()
                .isNotBlank();
    }

}
