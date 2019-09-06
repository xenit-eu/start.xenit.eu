package eu.xenit.alfred.initializr.start.sdk.alfred.compose.config;

import eu.xenit.alfred.initializr.start.docker.compose.DockerImageEnvNameProvider;
import eu.xenit.alfred.initializr.start.project.docker.DockerProjectModule;
import io.spring.initializr.generator.buildsystem.gradle.GradleTask;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import lombok.Getter;
import lombok.experimental.Accessors;

public class ComposeUpGradleTaskConfiguration implements Consumer<GradleTask.Builder> {

    @Getter
    @Accessors(fluent = true)
    private final List<DockerProjectModule> usesDockerImageFrom = new ArrayList<>();

    private final DockerImageEnvNameProvider variableNameProvider;

    public ComposeUpGradleTaskConfiguration(DockerImageEnvNameProvider variableNameProvider)
    {
        this.variableNameProvider = variableNameProvider;
    }

    @Override
    public void accept(GradleTask.Builder builder) {
        this.usesDockerImageFrom.forEach(dockerImageProject -> {
            String dockerImageEnvName = this.variableNameProvider.get(dockerImageProject);

            builder.invoke("dependsOn", "':"+dockerImageProject.getId()+":buildDockerImage'");
            builder.nested("doFirst", doFirst -> {

                doFirst.invoke("dockerCompose.environment.put",
                        "'" + dockerImageEnvName + "'",
                        "project(':" + dockerImageProject.getId()+ "').buildDockerImage.getImageId()");
            });
        });
    }
}