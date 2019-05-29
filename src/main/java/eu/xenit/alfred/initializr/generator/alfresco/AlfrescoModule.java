package eu.xenit.alfred.initializr.generator.alfresco;

import eu.xenit.alfred.initializr.generator.project.ProjectModule;

public interface AlfrescoModule extends ProjectModule {

    AlfrescoModuleProperties getModuleProperties();
    SpringXmlConfigModel getSpringContext();

    AlfrescoModuleProjectLayout getProjectLayout();

}
