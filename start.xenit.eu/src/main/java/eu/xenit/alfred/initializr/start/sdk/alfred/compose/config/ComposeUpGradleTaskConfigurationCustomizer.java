package eu.xenit.alfred.initializr.start.sdk.alfred.compose.config;

import org.springframework.core.Ordered;

@FunctionalInterface
public interface ComposeUpGradleTaskConfigurationCustomizer extends Ordered {
    void customize(ComposeUpGradleTaskConfiguration composeUp);

    default int getOrder() {
        return 0;
    }
}


