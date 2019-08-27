package eu.xenit.alfred.initializr.generator.sdk.alfred.docker;

import static eu.xenit.alfred.initializr.model.docker.ComposeEnvironment.env;
import static eu.xenit.alfred.initializr.model.docker.ComposeVolumes.volume;

import eu.xenit.alfred.initializr.generator.docker.compose.DockerCompose;
import eu.xenit.alfred.initializr.generator.docker.compose.DockerComposeCustomizer;
import eu.xenit.alfred.initializr.model.docker.ComposeServices;
import io.spring.initializr.generator.project.ProjectDescription;

public class AlfrescoBaseLayerComposeCustomizer implements DockerComposeCustomizer {

    private final ProjectDescription projectDescription;

    public AlfrescoBaseLayerComposeCustomizer(ProjectDescription projectDescription) {
        this.projectDescription = projectDescription;
    }

    @Override
    public void customize(DockerCompose compose) {
        ComposeServices services = compose.main().getServices();

        services
            .service("alfresco")
                .image(String.format("${DOCKER_IMAGE:-hub.xenit.eu/%s:latest}", projectDescription.getName()))
                .volumes("alfresco:/opt/alfresco/alf_data")
                .ports("8080")
                .environment(
                        env("DB_URL", "jdbc:postgresql://database:5432/alfresco"),
                        env("INDEX", "noindex")
                );

        services
            .service("database")
                .image("xeniteu/postgres:latest")
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
