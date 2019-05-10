package eu.xenit.alfred.initializr.generator.build.sdk.alfred;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import eu.xenit.alfred.initializr.generator.build.alfresco.AlfrescoModuleProjectLayout;
import eu.xenit.alfred.initializr.generator.build.alfresco.platform.AlfrescoPlatformModule;
import eu.xenit.alfred.initializr.generator.build.sdk.alfred.platform.AlfredSdkPlatformProjectLayoutCustomizer;
import org.junit.Test;

public class AlfredSdkProjectLayoutCustomizerTest {

    @Test
    public void customize() {
        AlfredSdkPlatformProjectLayoutCustomizer customizer = new AlfredSdkPlatformProjectLayoutCustomizer();

        AlfrescoPlatformModule module = new AlfrescoPlatformModule("test");
        customizer.customize(module);

        AlfrescoModuleProjectLayout layout = module.getProjectLayout();

        assertThat(layout.getModulePropertiesPath().toString(), is("src/main/amp/module.properties"));
        assertThat(layout.getSourceConfigModuleDir().toString(), is("src/main/amp/config/alfresco/module/test"));
    }


}