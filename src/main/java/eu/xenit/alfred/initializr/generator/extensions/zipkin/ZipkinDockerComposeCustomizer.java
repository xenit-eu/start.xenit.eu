package eu.xenit.alfred.initializr.generator.extensions.zipkin;

import static eu.xenit.alfred.initializr.model.docker.ComposeEnvironment.env;


import eu.xenit.alfred.initializr.generator.docker.compose.DockerCompose;
import eu.xenit.alfred.initializr.generator.docker.compose.DockerComposeCustomizer;
import eu.xenit.alfred.initializr.model.docker.ComposeServices;
import eu.xenit.alfred.initializr.model.docker.DockerComposeModel;

public class ZipkinDockerComposeCustomizer implements DockerComposeCustomizer {

    @Override
    public void customize(DockerCompose compose) {
        DockerComposeModel zipkinModel = compose.file("zipkin");
        ComposeServices services = zipkinModel.getServices();

        services.service("alfresco")
                .environment(
                        env("DB_DRIVER","com.p6spy.engine.spy.P6SpyDriver"),
                        env("DB_URL","jdbc:p6spy:postgresql://database:5432/alfresco"),
                        env("GLOBAL_zipkin.collector","http://zipkin:9411/api/v2/spans")
//                        env("INDEX","solr4")
                );
        services.service("zipkin")
                .image("openzipkin/zipkin")
                .ports("9411:9411");
    }



}
