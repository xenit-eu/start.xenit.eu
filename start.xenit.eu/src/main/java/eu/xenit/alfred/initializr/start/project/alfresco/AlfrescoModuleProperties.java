package eu.xenit.alfred.initializr.start.project.alfresco;

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
