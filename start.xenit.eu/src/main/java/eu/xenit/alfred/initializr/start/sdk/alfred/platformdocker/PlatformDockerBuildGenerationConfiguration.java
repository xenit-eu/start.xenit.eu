package eu.xenit.alfred.initializr.start.sdk.alfred.platformdocker;

import static eu.xenit.alfred.initializr.start.project.alfresco.AlfrescoConstants.MavenRepositories.ALFRESCO_PUBLIC;
import static org.springframework.util.StringUtils.quote;

import eu.xenit.alfred.initializr.generator.buildsystem.gradle.GradleProjectDependency;
import eu.xenit.alfred.initializr.start.build.BuildCustomizer;
import eu.xenit.alfred.initializr.start.build.platformdocker.gradle.PlatformDockerGradleBuild;
import eu.xenit.alfred.initializr.start.project.alfresco.artifacts.AlfrescoVersionArtifactSelector;
import eu.xenit.alfred.initializr.start.project.alfresco.platform.AlfrescoPlatformModule;
import eu.xenit.alfred.initializr.start.project.docker.platform.DockerPlatformModule;
import eu.xenit.alfred.initializr.start.sdk.alfred.AlfredSdk;
import eu.xenit.alfred.initializr.start.sdk.alfred.AlfredSdk.Dependencies;
import eu.xenit.alfred.initializr.start.sdk.alfred.compose.config.ComposeUpGradleTaskConfigurationCustomizer;
import io.spring.initializr.generator.buildsystem.DependencyScope;
import io.spring.initializr.generator.buildsystem.gradle.GradleBuildSystem;
import io.spring.initializr.generator.buildsystem.gradle.GradleDependency;
import io.spring.initializr.generator.condition.ConditionalOnBuildSystem;
import io.spring.initializr.generator.project.ProjectDescription;
import io.spring.initializr.generator.project.ProjectGenerationConfiguration;
import io.spring.initializr.metadata.InitializrMetadata;
import java.util.AbstractMap;
import org.springframework.context.annotation.Bean;

@ProjectGenerationConfiguration
@ConditionalOnBuildSystem(GradleBuildSystem.ID)
public class PlatformDockerBuildGenerationConfiguration {

    private final InitializrMetadata metadata;
    private AlfrescoVersionArtifactSelector artifactSelector;

    public PlatformDockerBuildGenerationConfiguration(InitializrMetadata metadata,
            AlfrescoVersionArtifactSelector artifactSelector) {
        this.metadata = metadata;
        this.artifactSelector = artifactSelector;
    }

    @Bean
    public BuildCustomizer<PlatformDockerGradleBuild> addDockerAlfrescoPlugin(ProjectDescription project) {
        return (build) -> {
            build.plugins().add("eu.xenit.docker-alfresco", plugin -> plugin.setVersion("5.2.0"));
            build.plugins().add("eu.xenit.docker-compose", plugin -> plugin.setVersion("5.2.0"));
            build.plugins().add("eu.xenit.docker-compose.auto", plugin -> plugin.setVersion("5.2.0"));

            build.repositories().add(ALFRESCO_PUBLIC);

            build.dependencies().add("alfresco-war",
                    Dependencies.getAlfrescoWarDependency(artifactSelector.getAlfrescoArtifactId()));

            build.tasks().customize("dockerBuild", (dockerBuild) -> {
                dockerBuild.attribute("repositories", "["+quote(project.getName())+"]");
                dockerBuild.attribute("tags", "[" +quote("latest") +"]");
                dockerBuild.nested("alfresco", (alfresco) -> {
                    alfresco.attribute("baseImage",
                            "\"" + artifactSelector.getDockerRegistry() + "/" + artifactSelector.getAlfrescoDockerImage()
                                    + ":${alfrescoVersion}\"");
                    alfresco.attribute("leanImage", "true");
                } );
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
