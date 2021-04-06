package eu.xenit.alfred.initializr.start.project.alfresco.artifacts;

import io.spring.initializr.generator.project.ProjectDescription;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class AlfrescoVersionArtifactSelectionConfiguration {

    @Bean
    public AlfrescoVersionArtifactSelector getInLogicArtifactSelector(ProjectDescription projectDescription) {
        return new InLogicArtifactSelectorImpl(projectDescription);
    }

}
