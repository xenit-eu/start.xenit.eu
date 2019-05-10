package eu.xenit.alfred.initializr.generator.build.sdk.alfred.platform;

import eu.xenit.alfred.initializr.generator.alfresco.platform.AlfrescoPlatformModule;
import eu.xenit.alfred.initializr.generator.build.BuildCustomizer;
import eu.xenit.alfred.initializr.generator.build.RootProjectBuild;
import eu.xenit.alfred.initializr.generator.build.sdk.alfred.AlfredSdk;
import eu.xenit.alfred.initializr.generator.buildsystem.ProjectDependency;
import io.spring.initializr.generator.condition.ConditionalOnBuildSystem;
import io.spring.initializr.generator.project.ProjectGenerationConfiguration;
import io.spring.initializr.generator.project.ResolvedProjectDescription;
import org.springframework.context.annotation.Bean;

@ProjectGenerationConfiguration
@ConditionalOnBuildSystem("gradle")
public class AlfredSdkPlatformProjectGenerationConfiguration {

    @Bean
    public AlfredSdkPlatformModuleGradleCustomizer alfredSdkPlatformBuildCustomizer(AlfrescoPlatformModule module, ResolvedProjectDescription projectDescription) {
        return new AlfredSdkPlatformModuleGradleCustomizer(module);
    }

    @Bean
    public BuildCustomizer<RootProjectBuild> addPlatformAmp(
            ResolvedProjectDescription projectDescription,
            AlfrescoPlatformModule platform
    ) {
        return (build) -> {
            build.dependencies().add("", new ProjectDependency(
                    platform,
                    projectDescription.getGroupId(),
                    AlfredSdk.Configurations.configurationForPackaging(projectDescription.getPackaging()),
                    projectDescription.getPackaging().id()));
        };
    }

    @Bean
    public AlfredSdkPlatformProjectLayoutCustomizer projectLayoutCustomizer() {
        return new AlfredSdkPlatformProjectLayoutCustomizer();
    }

}
