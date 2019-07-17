package eu.xenit.alfred.initializr.generator.sdk.alfred.docker;

import static org.springframework.util.StringUtils.quote;

import eu.xenit.alfred.initializr.generator.alfresco.platform.AlfrescoPlatformModule;
import eu.xenit.alfred.initializr.generator.build.BuildCustomizer;
import eu.xenit.alfred.initializr.generator.build.RootProjectBuild;
import eu.xenit.alfred.initializr.generator.build.gradle.root.RootGradleBuild;
import eu.xenit.alfred.initializr.generator.buildsystem.gradle.GradleProjectDependency;
import eu.xenit.alfred.initializr.generator.sdk.alfred.AlfredSdk;
import eu.xenit.alfred.initializr.generator.sdk.alfred.AlfredSdk.Dependencies;
import io.spring.initializr.generator.buildsystem.DependencyScope;
import io.spring.initializr.generator.buildsystem.gradle.GradleDependency;
import io.spring.initializr.generator.condition.ConditionalOnBuildSystem;
import io.spring.initializr.generator.project.ProjectGenerationConfiguration;
import io.spring.initializr.generator.project.ResolvedProjectDescription;
import io.spring.initializr.metadata.InitializrMetadata;
import java.util.AbstractMap;
import org.springframework.context.annotation.Bean;

@ProjectGenerationConfiguration
@ConditionalOnBuildSystem("gradle")
public class DockerBuildGenerationConfiguration {

    private final InitializrMetadata metadata;

    public DockerBuildGenerationConfiguration(InitializrMetadata metadata)
    {
        this.metadata = metadata;
    }

    @Bean
    public BuildCustomizer<RootGradleBuild> addDockerAlfrescoPlugin(ResolvedProjectDescription project) {
        return (build) -> {
            build.addPlugin("eu.xenit.docker-alfresco", "4.0.3");

            build.dependencies().add("alfresco-war", Dependencies.ALFRESCO_WAR);

            build.customizeTask("dockerAlfresco", (dockerAlfresco) -> {
                dockerAlfresco.set("baseImage", "\"hub.xenit.eu/alfresco-enterprise:${alfrescoVersion}\"");
                dockerAlfresco.set("leanImage", "true");
                dockerAlfresco.nested("dockerBuild", (dockerBuild) -> {
                    dockerBuild.set("repository", quote("hub.xenit.eu/" + project.getName()));
                    dockerBuild.set("automaticTags", "true");
                });
            });
        };
    }

    @Bean
    public BuildCustomizer<RootProjectBuild> addAlfrescoPlatformModuleDependency(
            ResolvedProjectDescription projectDescription,
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
    public BuildCustomizer<RootProjectBuild> addAlfrescoAmpDependencies(ResolvedProjectDescription projectDescription) {
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
    public BuildCustomizer<RootProjectBuild> addAlfrescoSMDependencies(ResolvedProjectDescription projectDescription) {
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

    private boolean hasFacet(String facet, String dependencyId) {
        return this.metadata.getDependencies().get(dependencyId).getFacets().contains(facet);
    }
}
