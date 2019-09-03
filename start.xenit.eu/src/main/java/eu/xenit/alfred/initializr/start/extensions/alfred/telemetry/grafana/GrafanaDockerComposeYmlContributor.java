package eu.xenit.alfred.initializr.start.extensions.alfred.telemetry.grafana;

import eu.xenit.alfred.initializr.generator.docker.compose.DockerComposeFiles;
import eu.xenit.alfred.initializr.start.docker.compose.DockerComposeYmlContributor;
import eu.xenit.alfred.initializr.generator.docker.compose.DockerComposeYmlWriterDelegate;
import eu.xenit.alfred.initializr.generator.project.LocationStrategy;
import io.spring.initializr.generator.io.IndentingWriterFactory;

class GrafanaDockerComposeYmlContributor extends DockerComposeYmlContributor {

    GrafanaDockerComposeYmlContributor(DockerComposeFiles compose,
            DockerComposeYmlWriterDelegate writer,
            IndentingWriterFactory indentingWriterFactory,
            LocationStrategy locationStrategy) {
        super(compose, "grafana", writer, indentingWriterFactory, locationStrategy);
    }
}
