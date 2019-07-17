package eu.xenit.alfred.initializr.generator.extensions.alfred.telemetry.graphite;

import eu.xenit.alfred.initializr.generator.docker.compose.DockerCompose;
import eu.xenit.alfred.initializr.generator.docker.compose.DockerComposeLocationStrategy;
import eu.xenit.alfred.initializr.generator.docker.compose.DockerComposeYmlContributor;
import eu.xenit.alfred.initializr.generator.docker.compose.DockerComposeYmlWriter;
import io.spring.initializr.generator.io.IndentingWriterFactory;

class GraphiteDockerComposeYmlContributor extends DockerComposeYmlContributor {

    GraphiteDockerComposeYmlContributor(DockerCompose compose,
            DockerComposeYmlWriter writer,
            IndentingWriterFactory indentingWriterFactory,
            DockerComposeLocationStrategy locationStrategy) {
        super(compose, "graphite", writer, indentingWriterFactory, locationStrategy);
    }
}
