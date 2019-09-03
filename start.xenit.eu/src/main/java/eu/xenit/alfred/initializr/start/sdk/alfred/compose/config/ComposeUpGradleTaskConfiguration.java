package eu.xenit.alfred.initializr.start.sdk.alfred.compose.config;

import eu.xenit.alfred.initializr.generator.buildsystem.gradle.DockerGradleBuild;
import io.spring.initializr.generator.buildsystem.gradle.GradleTask;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import lombok.Getter;
import lombok.experimental.Accessors;

public class ComposeUpGradleTaskConfiguration implements Consumer<GradleTask.Builder> {

    @Getter
    @Accessors(fluent = true)
    private final List<DockerGradleBuild> usesDockerImageFrom = new ArrayList<>();

    @Override
    public void accept(GradleTask.Builder builder) {
        this.usesDockerImageFrom.forEach(dockerImageProject -> {
            builder.nested("doFirst", doFirst -> {
                String dockerImageEnvName = projectName2dockerImageVariable(dockerImageProject);
                doFirst.invoke("dependsOn",
                        "'" + dockerImageEnvName + "', project(':" + dockerImageProject.getName()
                                + "').buildDockerImage.getImageId()");
            });
        });
    }

    private String projectName2dockerImageVariable(DockerGradleBuild gradleBuild) {
        return gradleBuild.getName()
                .toUpperCase()
                .replace("-", "_") + "_IMAGE";

    }

    private boolean isDefault() {
        return this.usesDockerImageFrom.isEmpty();
    }



}