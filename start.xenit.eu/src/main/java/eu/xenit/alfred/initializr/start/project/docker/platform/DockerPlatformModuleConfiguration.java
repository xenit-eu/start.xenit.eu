package eu.xenit.alfred.initializr.start.project.docker.platform;

import io.spring.initializr.generator.project.ProjectDescription;
import io.spring.initializr.generator.project.ProjectGenerationConfiguration;
import org.springframework.context.annotation.Bean;

@ProjectGenerationConfiguration
public class DockerPlatformModuleConfiguration {

    @Bean
    DockerPlatformModule dockerPlatformModule(ProjectDescription projectDescription) {
        return new DockerPlatformModule(projectDescription.getArtifactId() + "-platform-docker");
    }
}
