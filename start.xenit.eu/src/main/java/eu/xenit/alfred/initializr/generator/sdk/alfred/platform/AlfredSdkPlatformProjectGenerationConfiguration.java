package eu.xenit.alfred.initializr.generator.sdk.alfred.platform;

import eu.xenit.alfred.initializr.generator.alfresco.platform.AlfrescoPlatformModule;
import eu.xenit.alfred.initializr.generator.build.BuildCustomizer;
import eu.xenit.alfred.initializr.generator.build.RootProjectBuild;
import io.spring.initializr.generator.buildsystem.Dependency;
import io.spring.initializr.generator.condition.ConditionalOnBuildSystem;
import io.spring.initializr.generator.project.ProjectGenerationConfiguration;
import io.spring.initializr.generator.project.ProjectDescription;
import org.springframework.context.annotation.Bean;

@ProjectGenerationConfiguration
@ConditionalOnBuildSystem("gradle")
public class AlfredSdkPlatformProjectGenerationConfiguration {

    @Bean
    public AlfredSdkPlatformModuleGradleCustomizer alfredSdkPlatformBuildCustomizer(AlfrescoPlatformModule module,
            ProjectDescription projectDescription) {
        return new AlfredSdkPlatformModuleGradleCustomizer(module);
    }

    @Bean
    public BuildCustomizer<RootProjectBuild> addPlatformAmp(
            ProjectDescription projectDescription,
            AlfrescoPlatformModule platform
    ) {
        return (build) -> {
            build.dependencies().add("platform",
                    Dependency.withCoordinates(projectDescription.getGroupId(), platform.getId())
                            .type(projectDescription.getPackaging().id()).build()
            );
        };
    }

    @Bean
    public AlfredSdkPlatformProjectLayoutCustomizer projectLayoutCustomizer() {
        return new AlfredSdkPlatformProjectLayoutCustomizer();
    }

}
