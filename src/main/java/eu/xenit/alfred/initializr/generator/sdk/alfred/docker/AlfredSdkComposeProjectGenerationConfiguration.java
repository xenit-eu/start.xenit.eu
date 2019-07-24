package eu.xenit.alfred.initializr.generator.sdk.alfred.docker;

import eu.xenit.alfred.initializr.generator.build.BuildCustomizer;
import eu.xenit.alfred.initializr.generator.build.gradle.root.RootGradleBuild;
import eu.xenit.alfred.initializr.generator.docker.compose.DockerComposeCustomizer;
import eu.xenit.alfred.initializr.generator.docker.compose.DockerComposeLocationStrategy;
import io.spring.initializr.generator.buildsystem.gradle.GradleBuildSystem;
import io.spring.initializr.generator.condition.ConditionalOnBuildSystem;
import io.spring.initializr.generator.project.ProjectGenerationConfiguration;
import io.spring.initializr.generator.project.ResolvedProjectDescription;
import java.nio.file.Paths;
import org.springframework.context.annotation.Bean;

@ProjectGenerationConfiguration
@ConditionalOnBuildSystem(GradleBuildSystem.ID)
public class AlfredSdkComposeProjectGenerationConfiguration {

    @Bean
    public DockerComposeCustomizer basicAlfrescoComposeCustomizer(ResolvedProjectDescription projectDescription) {
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

            build.customizeTask("dockerCompose", (dockerCompose) -> {
                dockerCompose.set("dockerComposeWorkingDirectory",
                        "'" + locationStrategy.getLocation().toString() + "/'");
            });
        };
    }

}
