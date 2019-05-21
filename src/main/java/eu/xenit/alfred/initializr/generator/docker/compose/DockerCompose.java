package eu.xenit.alfred.initializr.generator.docker.compose;


import eu.xenit.alfred.initializr.model.docker.DockerComposeModel;
import java.util.HashMap;
import java.util.Map;

public class DockerCompose {

    private Map<String, DockerComposeModel> files = new HashMap<>();

    public DockerCompose() {
        this.file("");
    }

    public DockerComposeModel file(String file) {
        if (file == null) throw new IllegalArgumentException("argument 'file' cannot be null");

        return this.files.putIfAbsent(file, new DockerComposeModel(file));
    }

    public DockerComposeModel main() {
        return this.file("");
    }


}
