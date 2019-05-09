package eu.xenit.alfred.initializr.generator.build.docker;

import eu.xenit.alfred.initializr.generator.build.BuildCustomizer;
import io.spring.initializr.generator.buildsystem.gradle.GradleBuild;
import io.spring.initializr.generator.condition.ConditionalOnBuildSystem;
import io.spring.initializr.generator.project.ProjectGenerationConfiguration;
import org.springframework.context.annotation.Bean;

@ProjectGenerationConfiguration
@ConditionalOnBuildSystem("gradle")
public class DockerBuildGenerationConfiguration {

    @Bean
    public BuildCustomizer<GradleBuild> addDockerAlfrescoPlugin() {
        return (build) -> {
            build.addPlugin("eu.xenit.docker-alfresco", "4.0.3");
            build.customizeTask("dockerAlfresco", (dockerAlfresco) -> {
                dockerAlfresco.set("baseImage", "\"hub.xenit.eu/alfresco-enterprise:${alfrescoVersion}\"");
                dockerAlfresco.set("leanImage", "true");
                dockerAlfresco.nested("dockerBuild", (dockerBuild) -> {
                    dockerBuild.set("repository", "\"hub.xenit.eu/sample-gradle-alfresco\"");
                    dockerBuild.set("automaticTags", "true");
                });

            });
        };
    }


}
