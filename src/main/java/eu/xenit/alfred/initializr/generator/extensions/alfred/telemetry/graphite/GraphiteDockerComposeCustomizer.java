package eu.xenit.alfred.initializr.generator.extensions.alfred.telemetry.graphite;

import static eu.xenit.alfred.initializr.model.docker.ComposeEnvironment.env;
import static eu.xenit.alfred.initializr.model.docker.ComposeVolumes.volume;

import eu.xenit.alfred.initializr.generator.docker.compose.DockerCompose;
import eu.xenit.alfred.initializr.generator.docker.compose.DockerComposeCustomizer;
import eu.xenit.alfred.initializr.model.docker.ComposeServices;
import eu.xenit.alfred.initializr.model.docker.DockerComposeModel;

public class GraphiteDockerComposeCustomizer implements DockerComposeCustomizer {

    @Override
    public void customize(DockerCompose compose) {

        DockerComposeModel composeGraphite = compose.file("graphite");
        ComposeServices services = composeGraphite.getServices();

        services.service("alfresco")
                .environment(
                        env("GLOBAL_alfred.telemetry.export.graphite.host", "carbon")
                );

        services.service("carbon")
                .image("hub.xenit.eu/carbon:0.0.1-4")
                .volumes("whisperdb:/opt/graphite/storage/whisper");

        services.service("monitor-graphite-api")
                .image("hub.xenit.eu/graphite-api:0.0.1-10")
                .volumes("whisperdb:/srv/graphite/whisper");

        composeGraphite.volumes(
                volume("whisperdb")
        );

    }
}
