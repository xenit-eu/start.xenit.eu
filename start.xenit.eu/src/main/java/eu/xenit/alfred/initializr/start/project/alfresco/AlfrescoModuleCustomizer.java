package eu.xenit.alfred.initializr.start.project.alfresco;

import org.springframework.core.Ordered;

@FunctionalInterface
public interface AlfrescoModuleCustomizer<M extends AlfrescoModule> extends Ordered {
    void customize(M module);

    default int getOrder() {
        return 0;
    }
}