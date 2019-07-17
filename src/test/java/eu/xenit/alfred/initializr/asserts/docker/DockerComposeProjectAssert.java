package eu.xenit.alfred.initializr.asserts.docker;

import static org.assertj.core.api.Assertions.assertThat;

import eu.xenit.alfred.initializr.web.project.DockerComposeGenerationResultSet;

public class DockerComposeProjectAssert {

    private final DockerComposeGenerationResultSet result;

    public DockerComposeProjectAssert(DockerComposeGenerationResultSet result)
    {
        this.result = result;
    }

    public DockerComposeAssert assertDockerCompose() {
        return new DockerComposeAssert(this.result.getDockerCompose());
    }

    public DockerComposeAssert assertDockerCompose(String layer) {
        return new DockerComposeAssert(this.result.getDockerComposeLayer(layer));
    }

    public DockerComposeProjectAssert assertSize(int size)
    {
        assertThat(this.result.size()).isEqualTo(size);
        return this;
    }


}
