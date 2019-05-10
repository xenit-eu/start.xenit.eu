package eu.xenit.alfred.initializr.generator.build.sdk.alfred;

import static org.assertj.core.api.Assertions.assertThat;

import eu.xenit.alfred.initializr.generator.alfresco.AlfrescoModuleProjectLayout;
import eu.xenit.alfred.initializr.generator.alfresco.platform.AlfrescoPlatformModule;
import eu.xenit.alfred.initializr.generator.build.sdk.alfred.platform.AlfredSdkPlatformProjectLayoutCustomizer;
import org.junit.Test;

public class AlfredSdkProjectLayoutCustomizerTest {

    @Test
    public void testCustomizeLayout() {
        AlfredSdkPlatformProjectLayoutCustomizer customizer = new AlfredSdkPlatformProjectLayoutCustomizer();

        AlfrescoPlatformModule module = new AlfrescoPlatformModule("test");
        customizer.customize(module);

        AlfrescoModuleProjectLayout layout = module.getProjectLayout();

        assertThat(layout.getModulePropertiesPath().toString()).isEqualTo("src/main/amp/module.properties");
        assertThat(layout.getSourceConfigModuleDir().toString()).isEqualTo("src/main/amp/config/alfresco/module/test");
    }


}