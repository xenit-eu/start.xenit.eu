package eu.xenit.alfred.initializr.start.extensions.alfred.telemetry.graphite;

import static eu.xenit.alfred.initializr.generator.docker.compose.model.ComposeEnvironment.env;
import static eu.xenit.alfred.initializr.generator.docker.compose.model.ComposeVolumes.volume;

import eu.xenit.alfred.initializr.generator.docker.compose.DockerComposeFiles;
import eu.xenit.alfred.initializr.generator.docker.compose.DockerComposeCustomizer;
import eu.xenit.alfred.initializr.generator.docker.compose.model.ComposeServices;
import eu.xenit.alfred.initializr.generator.docker.compose.model.DockerComposeModel;

public class GraphiteDockerComposeCustomizer implements DockerComposeCustomizer {

    @Override
    public void customize(DockerComposeFiles compose) {

        DockerComposeModel composeGraphite = compose.file("graphite");
        ComposeServices services = composeGraphite.getServices();

        services.service("alfresco")
                .environment(
                        env("GLOBAL_alfred.telemetry.export.graphite.host", "carbon"),

                        // disabling caches-binder because it outputs too many metrics
                        // for carbon to swallow at this point
                        env("GLOBAL_alfred.telemetry.binder.cache.enabled", "false")
                );

        services.service("carbon")
                .image("hub.xenit.eu/carbon:0.0.1-4")
                .volumes("whisperdb:/opt/graphite/storage/whisper");

        services.service("graphite-api")
                .image("hub.xenit.eu/graphite-api:0.0.1-10")
                .volumes("whisperdb:/srv/graphite/whisper");

        composeGraphite.volumes(
                volume("whisperdb")
        );

    }
}
