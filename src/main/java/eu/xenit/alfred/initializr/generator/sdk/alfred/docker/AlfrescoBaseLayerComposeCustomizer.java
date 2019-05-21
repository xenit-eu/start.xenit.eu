package eu.xenit.alfred.initializr.generator.sdk.alfred.docker;

import static eu.xenit.alfred.initializr.model.docker.ComposeEnvironment.env;
import static eu.xenit.alfred.initializr.model.docker.ComposeVolumes.volume;

import eu.xenit.alfred.initializr.generator.docker.compose.DockerCompose;
import eu.xenit.alfred.initializr.generator.docker.compose.DockerComposeCustomizer;
import eu.xenit.alfred.initializr.model.docker.ComposeServices;
import eu.xenit.alfred.initializr.model.docker.ComposeVolumeInfo;
import io.spring.initializr.generator.project.ResolvedProjectDescription;

public class AlfrescoBaseLayerComposeCustomizer implements DockerComposeCustomizer {

    private final ResolvedProjectDescription projectDescription;

    public AlfrescoBaseLayerComposeCustomizer(ResolvedProjectDescription projectDescription) {
        this.projectDescription = projectDescription;
    }

    @Override
    public void customize(DockerCompose compose) {
        ComposeServices services = compose.main().getServices();

        services
            .service("alfresco")
                .image(String.format("${DOCKER_IMAGE:-hub.xenit.eu/%s:latest}", projectDescription.getName()))
                .volumes("alfresco:/opt/alfresco/alf_data")
                .ports("${SERVICE_ALFRESCO_TCP_8080:-8080:8080}")
                .environment(
                        env("DB_URL", "jdbc:postgresql://database:5432/alfresco"),
                        env("INDEX", "noindex")
                );

        services
            .service("database")
                .image("hub.xenit.eu/postgres:10.4")
                .volumes("postgres:/var/lib/postgresql/data")
                .environment(
                        env("POSTGRES_USER", "alfresco"),
                        env("POSTGRES_PASSWORD", "admin"),
                        env("POSTGRES_DB", "alfresco")
                );

        compose.main().volumes(
                volume("alfresco"),
                volume("postgres")
        );

    }
}
