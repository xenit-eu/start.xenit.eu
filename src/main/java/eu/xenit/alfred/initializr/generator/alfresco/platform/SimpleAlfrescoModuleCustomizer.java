package eu.xenit.alfred.initializr.generator.alfresco.platform;

import eu.xenit.alfred.initializr.generator.alfresco.AlfrescoModuleCustomizer;
import eu.xenit.alfred.initializr.generator.alfresco.AlfrescoModuleProperties;
import io.spring.initializr.generator.project.ResolvedProjectDescription;
import org.springframework.core.Ordered;

public class SimpleAlfrescoModuleCustomizer implements AlfrescoModuleCustomizer<AlfrescoPlatformModule> {

    private final ResolvedProjectDescription projectDescription;

    public SimpleAlfrescoModuleCustomizer(ResolvedProjectDescription description) {
        this.projectDescription = description;
    }

    @Override
    public void customize(AlfrescoPlatformModule module) {
        module.setId(this.projectDescription.getArtifactId() + "-platform");

        module.setVersion(this.projectDescription.getVersion());
        module.setDescription(this.projectDescription.getDescription());

        module.setAlfrescoVersion(this.projectDescription.getPlatformVersion());

        // configure module.properties - using variables that will be expanded by the build system
        AlfrescoModuleProperties moduleProperties = module.getModuleProperties();
        moduleProperties.setId("${project.group}.${project.name}");
        moduleProperties.setDescription("$project.description");
        moduleProperties.setVersion("$project.version");
        moduleProperties.setTitle("$project.name");
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
