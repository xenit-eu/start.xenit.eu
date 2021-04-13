package eu.xenit.alfred.initializr.start.sdk.alfred.compose;

import eu.xenit.alfred.initializr.start.build.BuildCustomizer;
import eu.xenit.alfred.initializr.start.build.root.gradle.RootGradleBuild;
import eu.xenit.alfred.initializr.generator.docker.compose.DockerComposeCustomizer;
import eu.xenit.alfred.initializr.generator.docker.compose.DockerComposeLocationStrategy;
import eu.xenit.alfred.initializr.start.docker.compose.DockerImageEnvNameProvider;
import eu.xenit.alfred.initializr.start.project.docker.platform.DockerPlatformModule;
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
    public DockerComposeCustomizer basicAlfrescoComposeCustomizer(
            ProjectDescription projectDescription,
            DockerPlatformModule dockerPlatformModule,
            DockerImageEnvNameProvider dockerImageEnvNameProvider) {
        return new AlfrescoBaseLayerComposeCustomizer(projectDescription, dockerPlatformModule, dockerImageEnvNameProvider);
    }

    @Bean
    public DockerComposeLocationStrategy dockerComposeLocationStrategy() {
        return new DockerComposeLocationStrategy(Paths.get("docker-compose"));
    }

    @Bean
    DockerComposeGradlePluginConfigurationCustomizer configureDockerComposeGradlePlugin(
            DockerComposeLocationStrategy locationStrategy) {
        return new DockerComposeGradlePluginConfigurationCustomizer() {

            @Override
            public void customize(DockerComposeGradlePluginConfiguration configuration) {
                configuration.getUseComposeFiles().add("docker-compose.yml");
                configuration.setLocationStrategy(locationStrategy);
            }

            // Make sure 'docker-compose.yml' is first
            public int getOrder() {
                return HIGHEST_PRECEDENCE;
            }
        };
    }

    @Bean
    DockerComposeGradlePluginConfiguration dockerComposeGradlePluginConfiguration(
            ObjectProvider<DockerComposeGradlePluginConfigurationCustomizer> customizers,
            DockerPlatformModule dockerPlatformModule) {

        DockerComposeGradlePluginConfiguration composePluginConfig = new DockerComposeGradlePluginConfiguration();
        composePluginConfig.setDockerPlatformModule(dockerPlatformModule);
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
            build.plugins().add("com.avast.gradle.docker-compose", plugin -> plugin.setVersion("0.14.2"));

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
            DockerImageEnvNameProvider nameProvider,
            ObjectProvider<ComposeUpGradleTaskConfigurationCustomizer> customizers) {

        ComposeUpGradleTaskConfiguration composeUp = new ComposeUpGradleTaskConfiguration(nameProvider);
        LambdaSafe.callbacks(ComposeUpGradleTaskConfigurationCustomizer.class,
                customizers.orderedStream().collect(Collectors.toList()),
                composeUp, new Object[0]).invoke((customizer) ->
        {
            customizer.customize(composeUp);
        });

        return composeUp;
    }

}
