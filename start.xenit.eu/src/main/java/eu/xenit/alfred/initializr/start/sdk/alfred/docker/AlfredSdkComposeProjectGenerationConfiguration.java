package eu.xenit.alfred.initializr.start.sdk.alfred.docker;

import eu.xenit.alfred.initializr.start.build.BuildCustomizer;
import eu.xenit.alfred.initializr.start.build.root.gradle.RootGradleBuild;
import eu.xenit.alfred.initializr.generator.docker.compose.DockerComposeCustomizer;
import eu.xenit.alfred.initializr.generator.docker.compose.DockerComposeLocationStrategy;
import eu.xenit.alfred.initializr.start.sdk.alfred.compose.AlfrescoBaseLayerComposeCustomizer;
import io.spring.initializr.generator.buildsystem.gradle.GradleBuildSystem;
import io.spring.initializr.generator.condition.ConditionalOnBuildSystem;
import io.spring.initializr.generator.project.ProjectGenerationConfiguration;
import io.spring.initializr.generator.project.ProjectDescription;
import java.nio.file.Paths;
import org.springframework.context.annotation.Bean;

@ProjectGenerationConfiguration
@ConditionalOnBuildSystem(GradleBuildSystem.ID)
public class AlfredSdkComposeProjectGenerationConfiguration {

    @Bean
    public DockerComposeCustomizer basicAlfrescoComposeCustomizer(ProjectDescription projectDescription) {
        return new AlfrescoBaseLayerComposeCustomizer(projectDescription);
    }

    @Bean
    public DockerComposeLocationStrategy dockerComposeLocationStrategy() {
        return new DockerComposeLocationStrategy(Paths.get("docker-compose"));
    }

    @Bean
    public BuildCustomizer<RootGradleBuild> rootGradleBuildDockerComposeConfiguration(
            DockerComposeLocationStrategy locationStrategy) {
        return (build) -> {

            build.tasks().customize("dockerCompose", (dockerCompose) -> {
                dockerCompose.attribute("dockerComposeWorkingDirectory",
                        "'" + locationStrategy.getLocation().toString() + "/'");
            });
        };
    }
}
