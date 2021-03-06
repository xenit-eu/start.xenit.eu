package eu.xenit.alfred.initializr.start.sdk.alfred.platform;

import eu.xenit.alfred.initializr.start.project.alfresco.AlfrescoModuleCustomizer;
import eu.xenit.alfred.initializr.start.project.alfresco.AlfrescoModuleProjectLayout;
import eu.xenit.alfred.initializr.start.project.alfresco.platform.AlfrescoPlatformModule;
import org.springframework.util.Assert;

public class AlfredSdkPlatformProjectLayoutCustomizer implements AlfrescoModuleCustomizer<AlfrescoPlatformModule> {

    @Override
    public void customize(AlfrescoPlatformModule module) {
        AlfrescoModuleProjectLayout projectLayout = module.getProjectLayout();
        Assert.notNull(module.getId(), "module.getId() should not be null");

        projectLayout.setSourceConfigDir("src/main/amp");
        projectLayout.setSourceConfigModuleDir("src/main/amp/config/alfresco/module/"+module.getId());

    }

}
