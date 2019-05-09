package eu.xenit.alfred.initializr.generator.build;

import io.spring.initializr.generator.project.ProjectGenerationConfiguration;
import io.spring.initializr.generator.project.ResolvedProjectDescription;
import org.springframework.context.annotation.Bean;

@ProjectGenerationConfiguration
public class BuildProjectGenerationConfiguration {

    @Bean
    public SimpleBuildCustomizer projectDescriptionBuildCustomizer(
            ResolvedProjectDescription projectDescription) {
        return new SimpleBuildCustomizer(projectDescription);
    }

//    @Bean
//    public SpringBootVersionRepositoriesBuildCustomizer repositoriesBuilderCustomizer(
//            ResolvedProjectDescription description) {
//        return new SpringBootVersionRepositoriesBuildCustomizer(
//                description.getPlatformVersion());
//    }

}
