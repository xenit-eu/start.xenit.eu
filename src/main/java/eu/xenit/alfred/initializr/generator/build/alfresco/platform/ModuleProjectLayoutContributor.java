package eu.xenit.alfred.initializr.generator.build.alfresco.platform;

import eu.xenit.alfred.initializr.generator.build.alfresco.AlfrescoModule;
import eu.xenit.alfred.initializr.generator.build.alfresco.AlfrescoModuleProjectLayout;
import io.spring.initializr.generator.project.contributor.ProjectContributor;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.springframework.core.Ordered;

public class ModuleProjectLayoutContributor implements ProjectContributor {

    private final AlfrescoModule module;

    public ModuleProjectLayoutContributor(AlfrescoModule module)
    {
        this.module = module;
    }

    @Override
    public void contribute(Path projectRoot) throws IOException {
        AlfrescoModuleProjectLayout layout = module.getProjectLayout();

        Path moduleRoot = projectRoot.resolve(layout.getModuleRoot());

        Path srcConfig = moduleRoot.resolve(layout.getSourceConfigDir());
        Path srcModuleConfig = moduleRoot.resolve(layout.getSourceConfigModuleDir());

        Files.createDirectories(srcConfig);
        Files.createDirectories(srcModuleConfig);
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
