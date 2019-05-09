package eu.xenit.alfred.initializr.generator.build.sdk.alfred;

import eu.xenit.alfred.initializr.generator.build.alfresco.AlfrescoModuleCustomizer;
import eu.xenit.alfred.initializr.generator.build.alfresco.AlfrescoModuleProjectLayout;
import eu.xenit.alfred.initializr.generator.build.alfresco.platform.AlfrescoPlatformModule;
import java.nio.file.Paths;
import org.springframework.util.Assert;

public class AlfredSdkProjectLayoutCustomizer implements AlfrescoModuleCustomizer<AlfrescoPlatformModule> {

    @Override
    public void customize(AlfrescoPlatformModule module) {
        AlfrescoModuleProjectLayout projectLayout = module.getProjectLayout();
        Assert.notNull(module.getId(), "module.getId() should not be null");

        projectLayout.setSourceConfigDir("src/main/amp");
        projectLayout.setSourceConfigModuleDir("src/main/amp/config/alfresco/module/"+module.getId());

    }

}
