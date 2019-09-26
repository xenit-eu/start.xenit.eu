package eu.xenit.alfred.initializr.start.build.root.gradle;

import static org.springframework.util.StringUtils.quote;

import eu.xenit.alfred.initializr.start.build.BuildCustomizer;
import eu.xenit.alfred.initializr.generator.buildsystem.gradle.CustomGradleBuildWriter;
import io.spring.initializr.generator.buildsystem.BuildItemResolver;
import io.spring.initializr.generator.buildsystem.gradle.GradleBuildSystem;
import io.spring.initializr.generator.condition.ConditionalOnBuildSystem;
import io.spring.initializr.generator.io.IndentingWriterFactory;
import io.spring.initializr.generator.project.ProjectDescription;
import io.spring.initializr.generator.project.ProjectGenerationConfiguration;
import io.spring.initializr.generator.version.Version;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.util.LambdaSafe;
import org.springframework.context.annotation.Bean;

@ProjectGenerationConfiguration
@ConditionalOnBuildSystem(GradleBuildSystem.ID)
public class GradleProjectGenerationConfiguration {

    @Bean
    public RootGradleBuild gradleBuild(
            ProjectDescription projectDescription,
            ObjectProvider<BuildItemResolver> buildItemResolver,
            ObjectProvider<BuildCustomizer<?>> buildCustomizers) {
        return this.createGradleBuild(
                projectDescription.getName(),
                buildItemResolver.getIfAvailable(),
                buildCustomizers.orderedStream().collect(Collectors.toList()));
    }

    private RootGradleBuild createGradleBuild(String name, BuildItemResolver buildItemResolver,
            List<BuildCustomizer<?>> buildCustomizers) {

        RootGradleBuild build = new RootGradleBuild(name, buildItemResolver);
        LambdaSafe.callbacks(BuildCustomizer.class, buildCustomizers, build, new Object[0]).invoke((customizer) -> {
            customizer.customize(build);
        });
        return build;
    }

    @Bean
    public BuildCustomizer<RootGradleBuild> addDockerVersion(ProjectDescription projectDescription) {
        String alfrescoVersion;
        String alfrescoArtifactId;
        String alfrescoHub;
        String alfrescoDockerImage;
        Version version = projectDescription.getPlatformVersion();
        // TODO until https://github.com/xenit-eu/start.xenit.eu/issues/23 is resolved, just list translations
        switch (version.toString()) {
            case "5.2.5":
                alfrescoVersion = "5.2.5";
                alfrescoArtifactId = "alfresco-enterprise";
                alfrescoHub = "hub.xenit.eu/alfresco-enterprise";
                alfrescoDockerImage = "alfresco-enterprise";
                break;
            case "5.2.0.g":
                alfrescoVersion = "5.2.g";
                alfrescoArtifactId = "alfresco-platform";
                alfrescoHub = "xeniteu";
                alfrescoDockerImage = "alfresco-repository-community";
                break;
            default:
                throw new IllegalArgumentException(version.toString() + " is not a supported version for now");
        }
        return (build) -> {
            build.properties().property("alfrescoVersion", quote(alfrescoVersion));
            build.properties().property("alfrescoArtifactId", quote(alfrescoArtifactId));
            build.properties().property("alfrescoHub", quote(alfrescoHub));
            build.properties().property("alfrescoDockerImage", quote(alfrescoDockerImage));
        };
    }

    @Bean
    public RootGradleBuildContributor gradleBuildProjectContributor(CustomGradleBuildWriter buildWriter,
            RootGradleBuild build, IndentingWriterFactory indentingWriterFactory) {
        return new RootGradleBuildContributor(buildWriter, build, indentingWriterFactory);
    }
//    @Bean
//    public MultiProjectGradleBuildContributor gradleBuildProjectContributor(CustomGradleBuildWriter buildWriter,
//            MultiProjectGradleBuild build, IndentingWriterFactory indentingWriterFactory) {
//        return new MultiProjectGradleBuildContributor(buildWriter, build, indentingWriterFactory);
//    }

    @Bean
    public GradleWrapperContributor gradle4WrapperContributor() {
        return new GradleWrapperContributor("4");
    }

    @Bean
    public SettingsGradleProjectContributor settingsGradleProjectContributor(
            RootGradleBuild build, IndentingWriterFactory indentingWriterFactory) {
        return new SettingsGradleProjectContributor(build, indentingWriterFactory);
    }

    @Bean
    public CustomGradleBuildWriter gradleBuildWriter() {
        return new CustomGradleBuildWriter();
    }

}