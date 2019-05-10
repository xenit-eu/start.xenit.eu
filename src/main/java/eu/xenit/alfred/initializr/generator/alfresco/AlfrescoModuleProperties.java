package eu.xenit.alfred.initializr.generator.alfresco;

import lombok.Data;

@Data
public class AlfrescoModuleProperties {

    private String id;
    private String title;
    private String description;
    private String version;
    private String repoVersionMin;
    private String repoVersionMax;

}
