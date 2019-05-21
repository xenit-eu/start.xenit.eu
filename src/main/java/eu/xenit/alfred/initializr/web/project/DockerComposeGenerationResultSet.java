package eu.xenit.alfred.initializr.web.project;

import io.spring.initializr.generator.project.ResolvedProjectDescription;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import lombok.Getter;

public class DockerComposeGenerationResultSet {

    @Getter
    private final ResolvedProjectDescription projectDescription;

    private final List<DockerComposeYml> composeFileList;

    DockerComposeGenerationResultSet(ResolvedProjectDescription projectDescription, List<DockerComposeYml> files) {
        this.projectDescription = projectDescription;
        this.composeFileList = files;
    }

    public Optional<DockerComposeYml> getCompose(Path path)
    {
        return this.composeFileList.stream().filter(compose -> path.equals(compose.getPath())).findFirst();
    }

    public String getComposeContent(Path path) {
        Optional<DockerComposeYml> optional = this.getCompose(path);
        return optional.map(DockerComposeYml::getContent).orElse(null);
    }

    public String getDockerCompose() {
        return this.getComposeContent(Paths.get("docker-compose.yml"));
    }

    public String getDockerComposeLayer(String layer) {
        return this.getComposeContent(Paths.get(String.format("docker-compose-%s.yml", layer)));
    }

    public int size() {
        return this.composeFileList.size();
    }

    public Stream<Path> getComposeFilePaths() {
        return this.composeFileList.stream().map(DockerComposeYml::getPath);
    }

}
