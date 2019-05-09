//package eu.xenit.alfred.initializr.asserts;
//
//import java.io.File;
//
//public class AlfrescoModulePackageAssert extends AlfrescoModuleAssert {
//
//    public AlfrescoModulePackageAssert(AlfredSdkProjectAssert module) {
//        super(module);
//    }
//
//    public AlfrescoModulePackageAssert hasModulePropertiesFile()
//    {
//        return this.hasFile("src/main/amp/module.properties");
//    }
//
//    public AlfrescoModulePackageAssert hasModuleContextFile()
//    {
//        String projectName = this.module.getDir().getId();
//        return this.hasFile("src/main/amp/config/alfresco/module/"+projectName+"/module-context.xml");
//    }
//
//    @Override
//    public AlfrescoModulePackageAssert hasFile(String... localPaths) {
//        super.hasFile(localPaths);
//        return this;
//    }
//}
