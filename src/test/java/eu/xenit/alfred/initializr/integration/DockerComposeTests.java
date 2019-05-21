package eu.xenit.alfred.initializr.integration;

import eu.xenit.alfred.initializr.asserts.docker.DockerComposeGenerationAssert;
import io.spring.initializr.web.project.ProjectRequest;
import org.junit.Test;

public class DockerComposeTests extends BaseGeneratorTests {

    @Test
    public void testDefaultBuild() {
        ProjectRequest request = createProjectRequest();

        DockerComposeGenerationAssert result = generateCompose(request);

        result.assertDockerCompose()
                .isNotBlank()
                .startsWith("version:")
                .contains("alfresco:");

    }

}