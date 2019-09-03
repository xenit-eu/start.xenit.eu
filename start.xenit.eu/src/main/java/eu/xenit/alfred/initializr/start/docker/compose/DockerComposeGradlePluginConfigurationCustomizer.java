package eu.xenit.alfred.initializr.start.docker.compose;

import org.springframework.core.Ordered;

@FunctionalInterface
public interface DockerComposeGradlePluginConfigurationCustomizer extends Ordered {
    void customize(DockerComposeGradlePluginConfiguration configuration);

    default int getOrder() {
        return 0;
    }
}

