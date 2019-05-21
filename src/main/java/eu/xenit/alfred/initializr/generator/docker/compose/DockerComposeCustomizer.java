package eu.xenit.alfred.initializr.generator.docker.compose;

import org.springframework.core.Ordered;

@FunctionalInterface
public interface DockerComposeCustomizer extends Ordered {
    void customize(DockerCompose compose);

    default int getOrder() {
        return 0;
    }
}

