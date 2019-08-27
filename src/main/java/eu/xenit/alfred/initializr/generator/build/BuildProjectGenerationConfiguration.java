package eu.xenit.alfred.initializr.generator.build;

import io.spring.initializr.generator.project.ProjectDescription;
import io.spring.initializr.generator.project.ProjectGenerationConfiguration;
import org.springframework.context.annotation.Bean;

@ProjectGenerationConfiguration
public class BuildProjectGenerationConfiguration {

    @Bean
    public SimpleBuildCustomizer projectDescriptionBuildCustomizer(
            ProjectDescription projectDescription) {
        return new SimpleBuildCustomizer(projectDescription);
    }

}
