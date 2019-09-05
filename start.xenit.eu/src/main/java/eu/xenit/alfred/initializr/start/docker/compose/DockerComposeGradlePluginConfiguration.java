package eu.xenit.alfred.initializr.start.docker.compose;

import io.spring.initializr.generator.buildsystem.gradle.GradleTask;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import lombok.Getter;

public class DockerComposeGradlePluginConfiguration implements Consumer<GradleTask.Builder> {


    @Getter
    private List<String> useComposeFiles = new ArrayList<>();

    @Override
    public void accept(GradleTask.Builder dockerCompose) {
        if (!this.shouldSetUseComposeFiles()) {
            return;
        }

        dockerCompose.attribute("useComposeFiles", "[" + getUseComposeFilesAsString() + "]");
    }

    private String getUseComposeFilesAsString() {
        return useComposeFiles.stream()
                .map(s -> "'" + s + "'")
                .collect(Collectors.joining(", "));
    }

    private boolean shouldSetUseComposeFiles() {
        if (useComposeFiles.isEmpty()) {
            return false;
        }

        if (useComposeFiles.size() == 1 && "docker-compose.yml".equalsIgnoreCase(useComposeFiles.get(0))) {
            return false
                    ;
        }
        return true;
    }

}
