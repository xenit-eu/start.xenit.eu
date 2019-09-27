package eu.xenit.alfred.initializr.start.sdk.alfred.platform;

import eu.xenit.alfred.initializr.start.project.alfresco.artifacts.AlfrescoVersionArtifactSelector;
import eu.xenit.alfred.initializr.start.project.alfresco.platform.AlfrescoPlatformModule;
import io.spring.initializr.generator.condition.ConditionalOnBuildSystem;
import io.spring.initializr.generator.project.ProjectGenerationConfiguration;
import org.springframework.context.annotation.Bean;

@ProjectGenerationConfiguration
@ConditionalOnBuildSystem("gradle")
public class AlfredSdkPlatformProjectGenerationConfiguration {

    @Bean
    public AlfredSdkPlatformModuleGradleCustomizer alfredSdkPlatformBuildCustomizer(AlfrescoPlatformModule module,
            AlfrescoVersionArtifactSelector artifactSelector) {
        return new AlfredSdkPlatformModuleGradleCustomizer(module, artifactSelector);
    }

    @Bean
    public AlfredSdkPlatformProjectLayoutCustomizer projectLayoutCustomizer() {
        return new AlfredSdkPlatformProjectLayoutCustomizer();
    }


}
