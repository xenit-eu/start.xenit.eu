package eu.xenit.alfred.initializr.start.project.alfresco.platform.context;

import org.springframework.core.Ordered;

@FunctionalInterface
public interface SpringContextModelCustomizer<C extends SpringContextModel> extends Ordered {

    void customize(C context);

    default int getOrder() {
        return 0;
    }
}


