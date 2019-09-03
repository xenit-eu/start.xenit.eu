package eu.xenit.alfred.initializr.start.sdk.alfred.compose.config;

import eu.xenit.alfred.initializr.start.sdk.alfred.compose.config.DockerComposeGradlePluginConfiguration;
import org.springframework.core.Ordered;

@FunctionalInterface
public interface DockerComposeGradlePluginConfigurationCustomizer extends Ordered {
    void customize(DockerComposeGradlePluginConfiguration configuration);

    default int getOrder() {
        return 0;
    }
}

