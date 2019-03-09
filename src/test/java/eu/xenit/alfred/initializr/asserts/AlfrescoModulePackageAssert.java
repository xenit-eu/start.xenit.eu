package eu.xenit.alfred.initializr.asserts;

import java.io.File;

public class AlfrescoModulePackageAssert extends AlfrescoModuleAssert {

    public AlfrescoModulePackageAssert(AlfredSdkProjectAssert project) {
        super(project);
    }

    public AlfrescoModulePackageAssert hasModulePropertiesFile()
    {
        return this.hasFile("src/main/amp/module.properties");
    }

    public AlfrescoModulePackageAssert hasModuleContextFile()
    {
        String projectName = this.project.getDir().getName();
        return this.hasFile("src/main/amp/config/alfresco/module/"+projectName+"/module-context.xml");
    }

    @Override
    public AlfrescoModulePackageAssert hasFile(String... localPaths) {
        super.hasFile(localPaths);
        return this;
    }
}
