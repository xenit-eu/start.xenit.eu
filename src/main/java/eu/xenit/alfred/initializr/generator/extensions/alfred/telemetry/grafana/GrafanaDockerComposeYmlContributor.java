package eu.xenit.alfred.initializr.generator.extensions.alfred.telemetry.grafana;

import eu.xenit.alfred.initializr.generator.docker.compose.DockerCompose;
import eu.xenit.alfred.initializr.generator.docker.compose.DockerComposeLocationStrategy;
import eu.xenit.alfred.initializr.generator.docker.compose.DockerComposeYmlContributor;
import eu.xenit.alfred.initializr.generator.docker.compose.DockerComposeYmlWriter;
import io.spring.initializr.generator.io.IndentingWriterFactory;

class GrafanaDockerComposeYmlContributor extends DockerComposeYmlContributor {

    GrafanaDockerComposeYmlContributor(DockerCompose compose,
            DockerComposeYmlWriter writer,
            IndentingWriterFactory indentingWriterFactory,
            DockerComposeLocationStrategy locationStrategy) {
        super(compose, "grafana", writer, indentingWriterFactory, locationStrategy);
    }
}
