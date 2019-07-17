package eu.xenit.alfred.initializr.generator.extensions.alfred.telemetry;

import eu.xenit.alfred.initializr.generator.docker.compose.DockerCompose;
import eu.xenit.alfred.initializr.generator.docker.compose.DockerComposeCustomizer;

public class GrafanaDockerComposeCustomizer implements DockerComposeCustomizer {

    @Override
    public void customize(DockerCompose compose) {

        compose.file("grafana").getServices()
                .service("grafana")
                .image("hub.xenit.eu/grafana:5.0")
                .volumes("./grafana:/etc/grafana:z")
                .ports("3000");
    }
}
