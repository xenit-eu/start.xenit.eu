package eu.xenit.alfred.initializr.start.sdk.alfred.compose;

import eu.xenit.alfred.initializr.start.build.BuildCustomizer;
import eu.xenit.alfred.initializr.start.build.root.gradle.RootGradleBuild;
import eu.xenit.alfred.initializr.generator.docker.compose.DockerComposeCustomizer;
import eu.xenit.alfred.initializr.generator.docker.compose.DockerComposeLocationStrategy;
import eu.xenit.alfred.initializr.start.sdk.alfred.compose.config.ComposeUpGradleTaskConfiguration;
import eu.xenit.alfred.initializr.start.sdk.alfred.compose.config.ComposeUpGradleTaskConfigurationCustomizer;
import eu.xenit.alfred.initializr.start.sdk.alfred.compose.config.DockerComposeGradlePluginConfiguration;
import eu.xenit.alfred.initializr.start.sdk.alfred.compose.config.DockerComposeGradlePluginConfigurationCustomizer;
import io.spring.initializr.generator.buildsystem.gradle.GradleBuildSystem;
import io.spring.initializr.generator.condition.ConditionalOnBuildSystem;
import io.spring.initializr.generator.project.ProjectGenerationConfiguration;
import io.spring.initializr.generator.project.ProjectDescription;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.util.LambdaSafe;
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
    DockerComposeGradlePluginConfigurationCustomizer configureDockerComposeGradlePlugin(
            DockerComposeLocationStrategy locationStrategy) {
        return (configuration) -> {
            configuration.getUseComposeFiles().add("docker-compose.yml");
            configuration.setLocationStrategy(locationStrategy);
        };
    }

    @Bean
    DockerComposeGradlePluginConfiguration dockerComposeGradlePluginConfiguration(
            ObjectProvider<DockerComposeGradlePluginConfigurationCustomizer> customizers) {

        DockerComposeGradlePluginConfiguration composePluginConfig = new DockerComposeGradlePluginConfiguration();
        LambdaSafe.callbacks(DockerComposeGradlePluginConfigurationCustomizer.class,
                customizers.orderedStream().collect(Collectors.toList()),
                composePluginConfig,
                new Object[0])
                .invoke((customizer) -> {
                    customizer.customize(composePluginConfig);
                });

        return composePluginConfig;
    }

    @Bean
    BuildCustomizer<RootGradleBuild> configureComposeGradlePlugin(
            DockerComposeGradlePluginConfiguration composePluginConfiguration) {
        return (build) -> {
            // version number aligned with what the `eu.xenit.docker` plugin puts on the classpath
            build.plugins().add("com.avast.gradle.docker-compose", plugin -> plugin.setVersion("0.8.12"));

            build.tasks().customize("dockerCompose", composePluginConfiguration);
        };
    }

    @Bean
    BuildCustomizer<RootGradleBuild> configureComposeUpTaskInRootGradleBuild(ComposeUpGradleTaskConfiguration composeUpConfig)
    {
        return build -> build.tasks().customize("composeUp", composeUpConfig);
    }

    @Bean
    ComposeUpGradleTaskConfiguration createComposeUpConfigurationInRootGradleBuild(
            ObjectProvider<ComposeUpGradleTaskConfigurationCustomizer> customizers) {

        ComposeUpGradleTaskConfiguration composeUp = new ComposeUpGradleTaskConfiguration();
        LambdaSafe.callbacks(ComposeUpGradleTaskConfigurationCustomizer.class,
                customizers.orderedStream().collect(Collectors.toList()),
                composeUp, new Object[0]).invoke((customizer) ->
        {
            customizer.customize(composeUp);
        });

        return composeUp;
    }

}
