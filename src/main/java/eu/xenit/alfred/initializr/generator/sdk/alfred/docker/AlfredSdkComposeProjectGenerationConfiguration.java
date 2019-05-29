package eu.xenit.alfred.initializr.generator.sdk.alfred.docker;

import eu.xenit.alfred.initializr.generator.alfresco.platform.AlfrescoPlatformModule;
import eu.xenit.alfred.initializr.generator.build.BuildCustomizer;
import eu.xenit.alfred.initializr.generator.build.RootProjectBuild;
import eu.xenit.alfred.initializr.generator.buildsystem.gradle.GradleProjectDependency;
import eu.xenit.alfred.initializr.generator.docker.compose.DockerComposeCustomizer;
import io.spring.initializr.generator.condition.ConditionalOnBuildSystem;
import io.spring.initializr.generator.project.ProjectGenerationConfiguration;
import io.spring.initializr.generator.project.ResolvedProjectDescription;
import io.spring.initializr.metadata.InitializrConfiguration.Env.Gradle;
import org.springframework.context.annotation.Bean;

@ProjectGenerationConfiguration
@ConditionalOnBuildSystem("gradle")
public class AlfredSdkComposeProjectGenerationConfiguration {

    @Bean
    public DockerComposeCustomizer basicAlfrescoComposeCustomizer(ResolvedProjectDescription projectDescription) {
        return new AlfrescoBaseLayerComposeCustomizer(projectDescription);
    }



}
