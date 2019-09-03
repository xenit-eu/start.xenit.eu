package eu.xenit.alfred.initializr.generator.extensions.zipkin;

import eu.xenit.alfred.initializr.generator.docker.compose.DockerCompose;
import eu.xenit.alfred.initializr.generator.docker.compose.DockerComposeLocationStrategy;
import eu.xenit.alfred.initializr.generator.docker.compose.DockerComposeYmlContributor;
import eu.xenit.alfred.initializr.generator.docker.compose.DockerComposeYmlWriterDelegate;
import io.spring.initializr.generator.io.IndentingWriterFactory;

public class ZipkinDockerComposeYmlContributor extends DockerComposeYmlContributor {

    ZipkinDockerComposeYmlContributor(DockerCompose compose,
            DockerComposeYmlWriterDelegate writer,
            IndentingWriterFactory indentingWriterFactory,
            DockerComposeLocationStrategy locationStrategy) {
        super(compose, "zipkin", writer, indentingWriterFactory, locationStrategy);
    }

}
