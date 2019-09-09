package eu.xenit.alfred.initializr.start.project.docker;

import eu.xenit.alfred.initializr.generator.project.ProjectModule;
import eu.xenit.alfred.initializr.start.project.alfresco.AlfrescoModule;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public abstract class DockerProjectModule implements ProjectModule {

    private final String id;

    public DockerProjectModule(String projectId) {
            this.id = projectId;
        }

}
