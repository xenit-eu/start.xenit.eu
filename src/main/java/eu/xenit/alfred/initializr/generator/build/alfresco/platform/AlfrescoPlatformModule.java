package eu.xenit.alfred.initializr.generator.build.alfresco.platform;

import eu.xenit.alfred.initializr.generator.build.alfresco.AlfrescoModule;
import eu.xenit.alfred.initializr.generator.build.alfresco.AlfrescoModuleProjectLayout;
import eu.xenit.alfred.initializr.generator.build.alfresco.AlfrescoModuleProperties;
import eu.xenit.alfred.initializr.generator.build.alfresco.SpringXmlConfigModel;
import io.spring.initializr.generator.version.Version;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class AlfrescoPlatformModule implements AlfrescoModule {

    public AlfrescoPlatformModule(String moduleId) {
        this.id = moduleId;
    }

    @Setter
    private String id;

    @Setter
    private String version;

    @Setter
    private Version alfrescoVersion;

    @Setter
    private String description;

    private AlfrescoModuleProperties moduleProperties = new AlfrescoModuleProperties();
    private SpringXmlConfigModel springContext = new SpringXmlConfigModel();
    private AlfrescoModuleProjectLayout projectLayout = new AlfrescoModuleProjectLayout(this);


}
