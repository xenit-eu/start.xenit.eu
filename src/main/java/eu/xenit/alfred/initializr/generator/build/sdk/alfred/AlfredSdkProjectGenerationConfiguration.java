package eu.xenit.alfred.initializr.generator.build.sdk.alfred;

import eu.xenit.alfred.initializr.generator.build.alfresco.platform.AlfrescoPlatformModule;
import io.spring.initializr.generator.condition.ConditionalOnBuildSystem;
import io.spring.initializr.generator.project.ProjectGenerationConfiguration;
import org.springframework.context.annotation.Bean;

@ProjectGenerationConfiguration
@ConditionalOnBuildSystem("gradle")
public class AlfredSdkProjectGenerationConfiguration {

    @Bean
    public AlfredSdkPlatformModuleBuildCustomizer alfredSdkPlatformBuildCustomizer(AlfrescoPlatformModule module) {
        return new AlfredSdkPlatformModuleBuildCustomizer(module);
    }

    @Bean
    public AlfredSdkProjectLayoutCustomizer projectLayoutCustomizer() {
        return new AlfredSdkProjectLayoutCustomizer();
    }

}
