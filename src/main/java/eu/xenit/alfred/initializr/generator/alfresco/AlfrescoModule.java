package eu.xenit.alfred.initializr.generator.alfresco;

import eu.xenit.alfred.initializr.generator.project.Project;

public interface AlfrescoModule extends Project {

    AlfrescoModuleProperties getModuleProperties();
    SpringXmlConfigModel getSpringContext();

    AlfrescoModuleProjectLayout getProjectLayout();

}
