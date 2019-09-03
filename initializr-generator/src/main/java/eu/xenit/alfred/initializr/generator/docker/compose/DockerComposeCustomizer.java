package eu.xenit.alfred.initializr.generator.docker.compose;

import org.springframework.core.Ordered;

@FunctionalInterface
public interface DockerComposeCustomizer extends Ordered {
    void customize(DockerComposeFiles compose);

    default int getOrder() {
        return 0;
    }
}

