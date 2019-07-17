package eu.xenit.alfred.initializr.web.project;

import io.spring.initializr.generator.project.ResolvedProjectDescription;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import lombok.Getter;
import org.springframework.util.StringUtils;

public class DockerComposeGenerationResultSet {

    @Getter
    private final ResolvedProjectDescription projectDescription;

    private final List<DockerComposeYml> composeFileList;

    DockerComposeGenerationResultSet(ResolvedProjectDescription projectDescription, List<DockerComposeYml> files) {
        this.projectDescription = projectDescription;
        this.composeFileList = files;
    }

    public Optional<DockerComposeYml> getCompose(String filename)
    {
        if (StringUtils.isEmpty(filename)) {
            return Optional.empty();
        }

        return this.composeFileList.stream().filter(compose -> filename.equals(compose.getFilename())).findFirst();
    }

    public String getComposeContent(String filename) {
        Optional<DockerComposeYml> optional = this.getCompose(filename);
        return optional.map(DockerComposeYml::getContent).orElse(null);
    }

    public String getDockerCompose() {
        return this.getComposeContent("docker-compose.yml");
    }

    public String getDockerComposeLayer(String layer) {
        return this.getComposeContent(this.getComposeFilename(layer));
    }

    public String getComposeFilename(String layer) {
        return String.format("docker-compose-%s.yml", layer);
    }

    public int size() {
        return this.composeFileList.size();
    }

    public Collection<DockerComposeYml> files() {
        return Collections.unmodifiableList(this.composeFileList);
    }


}
