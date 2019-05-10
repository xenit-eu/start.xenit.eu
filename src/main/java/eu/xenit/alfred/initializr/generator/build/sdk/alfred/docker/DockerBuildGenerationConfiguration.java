package eu.xenit.alfred.initializr.generator.build.sdk.alfred.docker;

import static org.springframework.util.StringUtils.quote;

import eu.xenit.alfred.initializr.generator.build.BuildCustomizer;
import io.spring.initializr.generator.buildsystem.Dependency;
import io.spring.initializr.generator.buildsystem.DependencyScope;
import io.spring.initializr.generator.buildsystem.gradle.GradleBuild;
import io.spring.initializr.generator.condition.ConditionalOnBuildSystem;
import io.spring.initializr.generator.project.ProjectGenerationConfiguration;
import io.spring.initializr.generator.project.ResolvedProjectDescription;
import io.spring.initializr.generator.version.VersionReference;
import org.springframework.context.annotation.Bean;

@ProjectGenerationConfiguration
@ConditionalOnBuildSystem("gradle")
public class DockerBuildGenerationConfiguration {

    public final Dependency DEP_ALFRESCO_WAR = new Dependency(
            "org.alfresco",
            "alfresco-enterprise",
            VersionReference.ofProperty("alfresco-version"),
            DependencyScope.PROVIDED_RUNTIME,
            "war");

    @Bean
    public BuildCustomizer<GradleBuild> addDockerAlfrescoPlugin(ResolvedProjectDescription project) {
        return (build) -> {
            build.addPlugin("eu.xenit.docker-alfresco", "4.0.3");

            build.dependencies()
                    .add("baseAlfrescoWar", DEP_ALFRESCO_WAR);

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
