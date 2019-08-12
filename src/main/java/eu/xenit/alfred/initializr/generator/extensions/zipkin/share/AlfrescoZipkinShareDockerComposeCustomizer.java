package eu.xenit.alfred.initializr.generator.extensions.zipkin.share;

import static eu.xenit.alfred.initializr.model.docker.ComposeEnvironment.env;

import eu.xenit.alfred.initializr.generator.docker.compose.DockerCompose;
import eu.xenit.alfred.initializr.generator.docker.compose.DockerComposeCustomizer;
import eu.xenit.alfred.initializr.model.docker.ComposeServices;
import eu.xenit.alfred.initializr.model.docker.DockerComposeModel;

public class AlfrescoZipkinShareDockerComposeCustomizer implements DockerComposeCustomizer {

    @Override
    public void customize(DockerCompose compose) {
        //TODO enable this once repository and share are split in different services
//        DockerComposeModel zipkinModel = compose.file("alfresco-zipkin-share");
//        ComposeServices services = zipkinModel.getServices();
//        services.service("share")
//                .environment(
//                        env("JAVA_OPTS=-Dzipkin.collector=http://zipkin:9411/api/v2/spans")
//                );
    }



}
