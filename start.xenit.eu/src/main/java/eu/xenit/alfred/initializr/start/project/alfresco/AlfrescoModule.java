package eu.xenit.alfred.initializr.start.project.alfresco;

import eu.xenit.alfred.initializr.generator.project.ProjectModule;

public interface AlfrescoModule extends ProjectModule {

    AlfrescoModuleProperties getModuleProperties();
    AlfrescoModuleProjectLayout getProjectLayout();

}
