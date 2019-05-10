package eu.xenit.alfred.initializr.generator.build.sdk.alfred.platform;

import eu.xenit.alfred.initializr.generator.build.alfresco.platform.AlfrescoPlatformModule;
import io.spring.initializr.generator.condition.ConditionalOnBuildSystem;
import io.spring.initializr.generator.project.ProjectGenerationConfiguration;
import io.spring.initializr.generator.project.ResolvedProjectDescription;
import org.springframework.context.annotation.Bean;

@ProjectGenerationConfiguration
@ConditionalOnBuildSystem("gradle")
public class AlfredSdkPlatformProjectGenerationConfiguration {

    @Bean
    public AlfredSdkPlatformModuleGradleCustomizer alfredSdkPlatformBuildCustomizer(AlfrescoPlatformModule module, ResolvedProjectDescription projectDescription) {
        return new AlfredSdkPlatformModuleGradleCustomizer(module, projectDescription);
    }

    @Bean
    public AlfredSdkPlatformProjectLayoutCustomizer projectLayoutCustomizer() {
        return new AlfredSdkPlatformProjectLayoutCustomizer();
    }

}
