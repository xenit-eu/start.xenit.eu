package eu.xenit.alfred.initializr.asserts.docker;

import eu.xenit.alfred.initializr.web.project.DockerComposeGenerationResultSet;

public class DockerComposeGenerationAssert {

    private final DockerComposeGenerationResultSet result;

    public DockerComposeGenerationAssert(DockerComposeGenerationResultSet result)
    {
        this.result = result;
    }

    public DockerComposeAssert assertDockerCompose() {
        return new DockerComposeAssert(this.result.getDockerCompose());
    }

    public DockerComposeAssert assertDockerComposeLayer(String layer) {
        return new DockerComposeAssert(this.result.getDockerComposeLayer(layer));
    }


}
