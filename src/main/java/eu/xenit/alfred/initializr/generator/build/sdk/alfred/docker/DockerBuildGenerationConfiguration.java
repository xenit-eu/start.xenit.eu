package eu.xenit.alfred.initializr.generator.build.sdk.alfred.docker;

import static org.springframework.util.StringUtils.quote;

import eu.xenit.alfred.initializr.generator.build.BuildCustomizer;
import eu.xenit.alfred.initializr.generator.build.gradle.root.RootGradleBuild;
import eu.xenit.alfred.initializr.generator.build.sdk.alfred.AlfredSdk.Dependencies;
import io.spring.initializr.generator.condition.ConditionalOnBuildSystem;
import io.spring.initializr.generator.project.ProjectGenerationConfiguration;
import io.spring.initializr.generator.project.ResolvedProjectDescription;
import org.springframework.context.annotation.Bean;

@ProjectGenerationConfiguration
@ConditionalOnBuildSystem("gradle")
public class DockerBuildGenerationConfiguration {



    @Bean
    public BuildCustomizer<RootGradleBuild> addDockerAlfrescoPlugin(ResolvedProjectDescription project) {
        return (build) -> {
            build.addPlugin("eu.xenit.docker-alfresco", "4.0.3");

            build.dependencies().add("alfresco-war", Dependencies.ALFRESCO_WAR);
            //build.dependencies().add("platform", DEP_ALFRESCO_WAR);

//            dependencies {
//                baseAlfrescoWar "org.alfresco:content-services-community:6.0.7-ga@war"
//                alfrescoAmp "de.fmaul:javascript-console-repo:0.6@amp"
//                baseShareWar "org.alfresco:share:6.0.c@war"
//                shareAmp "de.fmaul:javascript-console-share:0.6@amp"
//            }

            build.customizeTask("dockerAlfresco", (dockerAlfresco) -> {
                dockerAlfresco.set("baseImage", "\"hub.xenit.eu/alfresco-enterprise:${alfrescoVersion}\"");
                dockerAlfresco.set("leanImage", "true");
                dockerAlfresco.nested("dockerBuild", (dockerBuild) -> {
                    dockerBuild.set("repository", quote("hub.xenit.eu/" + project.getName()));
                    dockerBuild.set("automaticTags", "true");
                });
            });
        };
    }


}
