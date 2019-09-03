package eu.xenit.alfred.initializr.generator.extensions.zipkin;

import eu.xenit.alfred.initializr.generator.docker.compose.DockerCompose;
import eu.xenit.alfred.initializr.generator.docker.compose.DockerComposeCustomizer;
import eu.xenit.alfred.initializr.model.docker.ComposeServices;
import eu.xenit.alfred.initializr.model.docker.DockerComposeModel;

public class ZipkinDockerComposeCustomizer implements DockerComposeCustomizer {

    @Override
    public void customize(DockerCompose compose) {
        DockerComposeModel zipkinModel = compose.file("zipkin");
        ComposeServices services = zipkinModel.getServices();
        services.service("zipkin")
                .image("openzipkin/zipkin")
                .ports("9411:9411");
    }



}
