package eu.xenit.alfred.initializr.start.sdk.alfred.compose.config;

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

//    composeUp {
//        dependsOn(':demo-platform-docker:buildDockerImage')
//        doFirst {
//            dockerCompose.environment.put 'DEMO_PLATFORM_DOCKER_IMAGE', project(':demo-platform-docker').buildDockerImage.getImageId()
//        }
//    }

    @Override
    public void accept(GradleTask.Builder builder) {
        this.usesDockerImageFrom.forEach(dockerImageProject -> {
            String dockerImageEnvName = module2dockerImageVariable(dockerImageProject);

            builder.invoke("dependsOn", "':"+dockerImageProject.getId()+":buildDockerImage'");
            builder.nested("doFirst", doFirst -> {

                doFirst.invoke("dockerCompose.environment.put",
                        "'" + dockerImageEnvName + "'",
                        "project(':" + dockerImageProject.getId()+ "').buildDockerImage.getImageId()");
            });
        });
    }

    private String module2dockerImageVariable(DockerProjectModule module) {
        return module.getId()
                .toUpperCase()
                .replace("-", "_") + "_IMAGE";

    }

    private boolean isDefault() {
        return this.usesDockerImageFrom.isEmpty();
    }



}