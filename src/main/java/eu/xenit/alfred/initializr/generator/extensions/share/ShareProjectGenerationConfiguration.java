package eu.xenit.alfred.initializr.generator.extensions.share;


import eu.xenit.alfred.initializr.generator.build.BuildCustomizer;
import eu.xenit.alfred.initializr.generator.build.gradle.root.RootGradleBuild;
import eu.xenit.alfred.initializr.generator.condition.ConditionalOnRequestedFacet;
import eu.xenit.alfred.initializr.generator.docker.compose.DockerComposeGradlePluginConfigurationCustomizer;
import eu.xenit.alfred.initializr.generator.extensions.zipkin.share.AlfrescoZipkinShareDockerComposeYmlContributor;
import eu.xenit.alfred.initializr.generator.sdk.alfred.AlfredSdk.Configurations;
import io.spring.initializr.generator.buildsystem.Dependency;
import io.spring.initializr.generator.buildsystem.gradle.GradleBuildSystem;
import io.spring.initializr.generator.buildsystem.gradle.GradleDependency;
import io.spring.initializr.generator.condition.ConditionalOnBuildSystem;
import io.spring.initializr.generator.project.ProjectGenerationConfiguration;
import io.spring.initializr.generator.version.VersionReference;
import io.spring.initializr.metadata.support.MetadataBuildItemResolver;
import org.springframework.context.annotation.Bean;

@ProjectGenerationConfiguration
@ConditionalOnRequestedFacet("share-war")
public class ShareProjectGenerationConfiguration {

    private final MetadataBuildItemResolver resolver;

    public ShareProjectGenerationConfiguration(MetadataBuildItemResolver resolver) {
        this.resolver = resolver;
    }

    @Bean
    @ConditionalOnBuildSystem(GradleBuildSystem.ID)
    public BuildCustomizer<RootGradleBuild> addShareWar(MetadataBuildItemResolver resolver) {
        return (build) -> {
            Dependency alfrescoZipkinShareAmp = GradleDependency.from(this.getShareDependency())
                    .type("war")
                    .version(VersionReference.ofProperty("alfresco-version"))
                    .configuration(Configurations.BASE_SHARE_WAR)
                    .build();
            build.dependencies().add("share-war", alfrescoZipkinShareAmp);
        };
    }

    private Dependency getShareDependency() {
        return resolver.resolveDependency("share-war");
    }

}
