package eu.xenit.alfred.initializr.asserts;

public abstract class AlfrescoModuleAssert {

    protected AlfredSdkProjectAssert project;

    protected AlfrescoModuleAssert(AlfredSdkProjectAssert project)
    {
        this.project = project;
    }

    public AlfrescoModuleAssert hasFile(String... localPaths) {
        this.project.hasFile(localPaths);
        return this;
    }


}
