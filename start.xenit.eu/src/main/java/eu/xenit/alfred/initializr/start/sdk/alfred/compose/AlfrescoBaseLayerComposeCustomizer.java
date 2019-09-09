package eu.xenit.alfred.initializr.start.sdk.alfred.compose;

import static eu.xenit.alfred.initializr.generator.docker.compose.model.ComposeEnvironment.env;
import static eu.xenit.alfred.initializr.generator.docker.compose.model.ComposeVolumes.volume;

import eu.xenit.alfred.initializr.generator.docker.compose.DockerComposeFiles;
import eu.xenit.alfred.initializr.generator.docker.compose.DockerComposeCustomizer;
import eu.xenit.alfred.initializr.generator.docker.compose.model.ComposeServices;
import eu.xenit.alfred.initializr.start.docker.compose.DockerImageEnvNameProvider;
import eu.xenit.alfred.initializr.start.project.docker.DockerProjectModule;
import io.spring.initializr.generator.project.ProjectDescription;

public class AlfrescoBaseLayerComposeCustomizer implements DockerComposeCustomizer {

    private final ProjectDescription projectDescription;
    private final DockerProjectModule dockerPlatformProject;
    private final DockerImageEnvNameProvider envNameProvider;

    public AlfrescoBaseLayerComposeCustomizer(ProjectDescription projectDescription,
            DockerProjectModule dockerPlatformProject,
            DockerImageEnvNameProvider envNameProvider) {
        this.projectDescription = projectDescription;
        this.dockerPlatformProject = dockerPlatformProject;
        this.envNameProvider = envNameProvider;
    }

    @Override
    public void customize(DockerComposeFiles compose) {
        ComposeServices services = compose.main().getServices();

        String imageIdEnv = this.envNameProvider.get(this.dockerPlatformProject);
        services
            .service("alfresco")
                .image(String.format("${%s:-hub.xenit.eu/%s:latest}", imageIdEnv, projectDescription.getName()))
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
