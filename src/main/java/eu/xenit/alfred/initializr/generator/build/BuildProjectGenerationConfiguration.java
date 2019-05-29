package eu.xenit.alfred.initializr.generator.build;

import io.spring.initializr.generator.project.ProjectGenerationConfiguration;
import io.spring.initializr.generator.project.ResolvedProjectDescription;
import io.spring.initializr.metadata.InitializrMetadata;
import org.springframework.context.annotation.Bean;

@ProjectGenerationConfiguration
public class BuildProjectGenerationConfiguration {

    @Bean
    public SimpleBuildCustomizer projectDescriptionBuildCustomizer(
            ResolvedProjectDescription projectDescription, InitializrMetadata metadata) {
        return new SimpleBuildCustomizer(projectDescription, metadata);
    }

//    @Bean
//    public SpringBootVersionRepositoriesBuildCustomizer repositoriesBuilderCustomizer(
//            ResolvedProjectDescription description) {
//        return new SpringBootVersionRepositoriesBuildCustomizer(
//                description.getPlatformVersion());
//    }

}
