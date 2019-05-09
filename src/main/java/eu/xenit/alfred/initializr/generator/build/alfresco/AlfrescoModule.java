package eu.xenit.alfred.initializr.generator.build.alfresco;

import lombok.Data;

public interface AlfrescoModule {

    String getId();
    void setId(String name);

    AlfrescoModuleProperties getModuleProperties();
    SpringXmlConfigModel getSpringContext();

    AlfrescoModuleProjectLayout getProjectLayout();

}
