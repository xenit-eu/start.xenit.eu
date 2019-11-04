package eu.xenit.alfred.initializr.start.sdk.alfred.platformdocker;

import static org.springframework.util.StringUtils.quote;

import eu.xenit.alfred.initializr.start.project.alfresco.platform.AlfrescoPlatformModule;
import eu.xenit.alfred.initializr.start.build.BuildCustomizer;
import eu.xenit.alfred.initializr.start.build.platformdocker.gradle.PlatformDockerGradleBuild;
import eu.xenit.alfred.initializr.generator.buildsystem.gradle.GradleProjectDependency;
import eu.xenit.alfred.initializr.start.project.docker.platform.DockerPlatformModule;
import eu.xenit.alfred.initializr.start.sdk.alfred.AlfredSdk;
import eu.xenit.alfred.initializr.start.sdk.alfred.AlfredSdk.Dependencies;
import eu.xenit.alfred.initializr.start.sdk.alfred.compose.config.ComposeUpGradleTaskConfigurationCustomizer;
import io.spring.initializr.generator.buildsystem.DependencyScope;
import io.spring.initializr.generator.buildsystem.gradle.GradleBuildSystem;
import io.spring.initializr.generator.buildsystem.gradle.GradleDependency;
import io.spring.initializr.generator.condition.ConditionalOnBuildSystem;
import io.spring.initializr.generator.project.ProjectGenerationConfiguration;
import io.spring.initializr.generator.project.ProjectDescription;
import io.spring.initializr.metadata.InitializrMetadata;
import java.util.AbstractMap;
import org.springframework.context.annotation.Bean;

@ProjectGenerationConfiguration
@ConditionalOnBuildSystem(GradleBuildSystem.ID)
public class PlatformDockerBuildGenerationConfiguration {

    private final InitializrMetadata metadata;

    public PlatformDockerBuildGenerationConfiguration(InitializrMetadata metadata) {
        this.metadata = metadata;
    }

    @Bean
    public BuildCustomizer<PlatformDockerGradleBuild> addDockerAlfrescoPlugin(ProjectDescription project) {
        return (build) -> {
            build.plugins().add("eu.xenit.docker-alfresco", plugin -> plugin.setVersion("4.0.3"));

            build.dependencies().add("alfresco-war", Dependencies.ALFRESCO_WAR);

            build.tasks().customize("dockerAlfresco", (dockerAlfresco) -> {
                dockerAlfresco.attribute("baseImage", "\"hub.xenit.eu/alfresco-enterprise/alfresco-enterprise:${alfrescoVersion}\"");
                dockerAlfresco.attribute("leanImage", "true");
                dockerAlfresco.nested("dockerBuild", (dockerBuild) -> {
                    dockerBuild.attribute("repository", quote(project.getName()));
                    dockerBuild.attribute("automaticTags", "true");
                });
            });
        };
    }

    @Bean
    public BuildCustomizer<PlatformDockerGradleBuild> addAlfrescoPlatformModuleDependency(
            ProjectDescription projectDescription,
            AlfrescoPlatformModule platformModule) {
        return (build) -> {
            build.dependencies().add("platform",
                    GradleProjectDependency.from(platformModule)
                            .projectConfiguration("amp")
                            .configuration("alfrescoAmp")
                            .scope(DependencyScope.COMPILE)
                            .build());
        };
    }

    @Bean
    public BuildCustomizer<PlatformDockerGradleBuild> addAlfrescoAmpDependencies(
            ProjectDescription projectDescription) {
        return (build) -> {
            projectDescription.getRequestedDependencies()
                    .entrySet().stream()
                    .filter(entry -> this.hasFacet("alfrescoAmp", entry.getKey()))
                    .map(entry -> new AbstractMap.SimpleEntry<>(
                            entry.getKey(),
                            GradleDependency.from(entry.getValue())
                                    .configuration(AlfredSdk.Configurations.ALFRESCO_AMP)
                                    .type("amp")
                                    .build()))
                    .forEach(entry -> build.dependencies().add(entry.getKey(), entry.getValue()));

        };
    }

    @Bean
    public BuildCustomizer<PlatformDockerGradleBuild> addAlfrescoSMDependencies(ProjectDescription projectDescription) {
        return (build) -> {
            projectDescription.getRequestedDependencies()
                    .entrySet().stream()
                    .filter(entry -> this.hasFacet("alfrescoSM", entry.getKey()))
                    .map(entry -> new AbstractMap.SimpleEntry<>(
                            entry.getKey(),
                            GradleDependency.from(entry.getValue())
                                    .configuration(AlfredSdk.Configurations.ALFRESCO_SM)
                                    .build()))
                    .forEach(entry -> build.dependencies().add(entry.getKey(), entry.getValue()));

        };
    }

    @Bean
    public BuildCustomizer<PlatformDockerGradleBuild> disableComposeTasksInPlatformDockerCustomizer() {
        return gradleBuild -> {
            gradleBuild.tasks().customize("composeUp", composeUp -> composeUp.attribute("enabled", "false"));
            gradleBuild.tasks().customize("composeDown", composeUp -> composeUp.attribute("enabled", "false"));
            gradleBuild.tasks().customize("composeDownForced", composeUp -> composeUp.attribute("enabled", "false"));
        };
    }

    private boolean hasFacet(String facet, String dependencyId) {
        return this.metadata.getDependencies().get(dependencyId).getFacets().contains(facet);
    }

    @Bean
    ComposeUpGradleTaskConfigurationCustomizer platformComposeUpCustomizer(
            DockerPlatformModule dockerPlatformModule) {
        return composeUp -> composeUp.usesDockerImageFrom().add(dockerPlatformModule);
    }
}
