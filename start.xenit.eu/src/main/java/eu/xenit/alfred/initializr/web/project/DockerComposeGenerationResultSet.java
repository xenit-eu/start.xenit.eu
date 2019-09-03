package eu.xenit.alfred.initializr.web.project;

import io.spring.initializr.generator.project.ProjectDescription;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.Getter;
import org.springframework.util.StringUtils;

public class DockerComposeGenerationResultSet {

    @Getter
    private final ProjectDescription projectDescription;

    private final List<DockerComposeYmlResult> composeFileList;

    public DockerComposeGenerationResultSet(ProjectDescription projectDescription, List<DockerComposeYmlResult> files) {
        this.projectDescription = projectDescription;
        this.composeFileList = files;
    }

    public Optional<DockerComposeYmlResult> getCompose(String filename)
    {
        if (StringUtils.isEmpty(filename)) {
            return Optional.empty();
        }

        return this.composeFileList.stream().filter(compose -> filename.equals(compose.getFilename())).findFirst();
    }

    public String getComposeContent(String filename) {
        Optional<DockerComposeYmlResult> optional = this.getCompose(filename);
        return optional.map(DockerComposeYmlResult::getContent).orElse(null);
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

    public Collection<DockerComposeYmlResult> files() {
        return Collections.unmodifiableList(this.composeFileList);
    }


}
