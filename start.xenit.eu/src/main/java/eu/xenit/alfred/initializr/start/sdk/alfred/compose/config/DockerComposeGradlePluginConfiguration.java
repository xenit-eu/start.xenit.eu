package eu.xenit.alfred.initializr.start.sdk.alfred.compose.config;

import static org.springframework.util.StringUtils.quote;

import eu.xenit.alfred.initializr.generator.docker.compose.DockerComposeLocationStrategy;
import eu.xenit.alfred.initializr.start.project.docker.platform.DockerPlatformModule;
import io.spring.initializr.generator.buildsystem.gradle.GradleTask;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.Setter;

public class DockerComposeGradlePluginConfiguration implements Consumer<GradleTask.Builder> {

    @Getter
    private List<String> useComposeFiles = new ArrayList<>();

    @Getter @Setter
    private DockerComposeLocationStrategy locationStrategy;

    @Getter @Setter
    private DockerPlatformModule dockerPlatformModule;

    @Override
    public void accept(GradleTask.Builder dockerCompose) {
        if (this.shouldSetUseComposeFiles()) {
            dockerCompose.attribute("useComposeFiles", "[" + getUseComposeFilesAsString() + "]");
        }

        if (locationStrategy != null) {
            dockerCompose.attribute("dockerComposeWorkingDirectory",
                    "'" + this.locationStrategy.getLocation().toString() + "/'");
        }
        dockerCompose.invoke("fromProject", "project(" + quote(":" + dockerPlatformModule.getId()) + ")");
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
            return false;
        }
        return true;
    }

}
