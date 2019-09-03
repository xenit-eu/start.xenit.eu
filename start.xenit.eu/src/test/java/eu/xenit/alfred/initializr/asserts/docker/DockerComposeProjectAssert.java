package eu.xenit.alfred.initializr.asserts.docker;

import eu.xenit.alfred.initializr.web.project.DockerComposeGenerationResultSet;
import eu.xenit.alfred.initializr.web.project.DockerComposeYmlResult;
import java.util.stream.Collectors;
import org.assertj.core.api.AbstractAssert;

public class DockerComposeProjectAssert extends
        AbstractAssert<DockerComposeProjectAssert, DockerComposeGenerationResultSet> {

    private final DockerComposeGenerationResultSet result;

    public DockerComposeProjectAssert(DockerComposeGenerationResultSet result) {
        super(result, DockerComposeProjectAssert.class);
        this.result = result;
    }

    public DockerComposeAssert assertDockerCompose() {
        return new DockerComposeAssert(this.result.getDockerCompose());
    }

    public DockerComposeAssert assertDockerCompose(String layer) {
        return new DockerComposeAssert(this.result.getDockerComposeLayer(layer));
    }

    public DockerComposeProjectAssert assertSize(int expected) {
        if (this.actual.size() != expected) {
            failWithMessage("Expected <%s> compose.yml files, but got <%s>: %s",
                    expected, this.actual.size(),
                    this.actual.files().stream()
                            .map(DockerComposeYmlResult::getFilename)
                            .collect(Collectors.joining(", ")));
        }

        return this;
    }


}
