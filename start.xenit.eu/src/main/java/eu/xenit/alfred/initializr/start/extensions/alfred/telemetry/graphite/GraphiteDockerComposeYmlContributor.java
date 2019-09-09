package eu.xenit.alfred.initializr.start.extensions.alfred.telemetry.graphite;

import eu.xenit.alfred.initializr.generator.docker.compose.DockerComposeFiles;
import eu.xenit.alfred.initializr.generator.docker.compose.DockerComposeLocationStrategy;
import eu.xenit.alfred.initializr.generator.docker.compose.DockerComposeYmlContributor;
import eu.xenit.alfred.initializr.generator.docker.compose.DockerComposeYmlWriterDelegate;
import io.spring.initializr.generator.io.IndentingWriterFactory;

class GraphiteDockerComposeYmlContributor extends DockerComposeYmlContributor {

    GraphiteDockerComposeYmlContributor(DockerComposeFiles compose,
            DockerComposeYmlWriterDelegate writer,
            IndentingWriterFactory indentingWriterFactory,
            DockerComposeLocationStrategy locationStrategy) {
        super(compose, "graphite", writer, indentingWriterFactory, locationStrategy);
    }
}
