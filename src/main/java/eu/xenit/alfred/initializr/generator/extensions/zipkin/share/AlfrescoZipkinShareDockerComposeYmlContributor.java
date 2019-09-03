package eu.xenit.alfred.initializr.generator.extensions.zipkin.share;

import eu.xenit.alfred.initializr.generator.docker.compose.DockerCompose;
import eu.xenit.alfred.initializr.generator.docker.compose.DockerComposeLocationStrategy;
import eu.xenit.alfred.initializr.generator.docker.compose.DockerComposeYmlContributor;
import eu.xenit.alfred.initializr.generator.docker.compose.DockerComposeYmlWriterDelegate;
import io.spring.initializr.generator.io.IndentingWriterFactory;

public class AlfrescoZipkinShareDockerComposeYmlContributor extends DockerComposeYmlContributor {

    AlfrescoZipkinShareDockerComposeYmlContributor(DockerCompose compose,
            DockerComposeYmlWriterDelegate writer,
            IndentingWriterFactory indentingWriterFactory,
            DockerComposeLocationStrategy locationStrategy) {
        super(compose, "alfresco-zipkin-share", writer, indentingWriterFactory, locationStrategy);
    }

}
