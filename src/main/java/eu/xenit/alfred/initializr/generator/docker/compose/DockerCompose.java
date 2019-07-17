package eu.xenit.alfred.initializr.generator.docker.compose;


import eu.xenit.alfred.initializr.model.docker.DockerComposeModel;
import java.util.HashMap;
import java.util.Map;

public class DockerCompose {

    private Map<String, DockerComposeModel> files = new HashMap<>();

    public DockerCompose() {

        // create the default `main()` file
        this.file("");
    }

    public DockerComposeModel file(String file) {
        if (file == null) throw new IllegalArgumentException("argument 'file' cannot be null");

        return this.files.computeIfAbsent(file, DockerComposeModel::new);
    }

    public DockerComposeModel main() {
        return this.file("");
    }

}
