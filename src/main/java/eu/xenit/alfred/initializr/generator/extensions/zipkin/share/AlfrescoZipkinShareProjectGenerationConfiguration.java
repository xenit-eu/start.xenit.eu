package eu.xenit.alfred.initializr.generator.extensions.zipkin.share;


import static eu.xenit.alfred.initializr.generator.buildsystem.RepositoryConstants.MavenRepositories.SONATYPE_SNAPSHOT;
import static org.springframework.util.StringUtils.quote;

import eu.xenit.alfred.initializr.generator.build.BuildCustomizer;
import eu.xenit.alfred.initializr.generator.build.gradle.root.RootGradleBuild;
import eu.xenit.alfred.initializr.generator.condition.ConditionalOnRequestedFacet;
import eu.xenit.alfred.initializr.generator.docker.compose.DockerCompose;
import eu.xenit.alfred.initializr.generator.docker.compose.DockerComposeGradlePluginConfigurationCustomizer;
import eu.xenit.alfred.initializr.generator.docker.compose.DockerComposeLocationStrategy;
import eu.xenit.alfred.initializr.generator.docker.compose.DockerComposeYmlWriterDelegate;
import eu.xenit.alfred.initializr.generator.extensions.zipkin.ZipkinDockerComposeYmlContributor;
import eu.xenit.alfred.initializr.generator.sdk.alfred.AlfredSdk.Configurations;
import io.spring.initializr.generator.buildsystem.Dependency;
import io.spring.initializr.generator.buildsystem.gradle.GradleBuildSystem;
import io.spring.initializr.generator.buildsystem.gradle.GradleDependency;
import io.spring.initializr.generator.condition.ConditionalOnBuildSystem;
import io.spring.initializr.generator.io.IndentingWriterFactory;
import io.spring.initializr.generator.project.ProjectGenerationConfiguration;
import io.spring.initializr.generator.version.VersionReference;
import io.spring.initializr.metadata.support.MetadataBuildItemResolver;
import org.springframework.context.annotation.Bean;

@ProjectGenerationConfiguration
@ConditionalOnRequestedFacet("alfresco-zipkin-share")
public class AlfrescoZipkinShareProjectGenerationConfiguration {

    private final MetadataBuildItemResolver resolver;

    public AlfrescoZipkinShareProjectGenerationConfiguration(MetadataBuildItemResolver resolver) {
        this.resolver = resolver;
    }

    @Bean
    public AlfrescoZipkinShareDockerComposeCustomizer alfrescoZipkinShareDockerComposeCustomizer() {
        return new AlfrescoZipkinShareDockerComposeCustomizer();
    }

    @Bean
    public AlfrescoZipkinShareDockerComposeYmlContributor alfrescoZipkinShareDockerComposeYmlContributor(DockerCompose compose,
            DockerComposeYmlWriterDelegate writer,
            IndentingWriterFactory indentingWriterFactory,
            DockerComposeLocationStrategy locationStrategy) {
        return new AlfrescoZipkinShareDockerComposeYmlContributor(compose, writer, indentingWriterFactory, locationStrategy);
    }

    @Bean
    @ConditionalOnBuildSystem(GradleBuildSystem.ID)
    public BuildCustomizer<RootGradleBuild> addAlfrescoZipkinShareAmp(MetadataBuildItemResolver resolver) {
        return (build) -> {

            String alfrescoZipkinVersion = this.getAlfrescoZipkinShareDependency().getVersion().getValue();
            build.ext("alfrescoZipkinShareVersion", quote(alfrescoZipkinVersion));

            Dependency alfrescoZipkinShareAmp = GradleDependency.from(this.getAlfrescoZipkinShareDependency())
                    .type("amp")
                    .version(VersionReference.ofProperty("alfresco-zipkin-share-version"))
                    .configuration(Configurations.SHARE_AMP)
                    .build();
            build.dependencies().add("alfresco-zipkin-share", alfrescoZipkinShareAmp);
            build.repositories().add(SONATYPE_SNAPSHOT);
        };
    }

    @Bean
    @ConditionalOnBuildSystem(GradleBuildSystem.ID)
    DockerComposeGradlePluginConfigurationCustomizer configureAlfrescoZipkinShareDockerComposeGradlePlugin(
            AlfrescoZipkinShareDockerComposeYmlContributor composeYml) {
        return (configuration) -> configuration.getUseComposeFiles().add(composeYml.composeFilename());
    }

    private Dependency getAlfrescoZipkinShareDependency() {
        return resolver.resolveDependency("alfresco-zipkin-share");
    }

}
