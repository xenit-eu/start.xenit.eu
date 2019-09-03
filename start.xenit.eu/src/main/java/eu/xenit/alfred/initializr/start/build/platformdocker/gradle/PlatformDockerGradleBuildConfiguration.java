package eu.xenit.alfred.initializr.start.build.platformdocker.gradle;

import eu.xenit.alfred.initializr.generator.buildsystem.gradle.CustomGradleBuildWriter;
import eu.xenit.alfred.initializr.start.build.BuildCustomizer;
import eu.xenit.alfred.initializr.start.build.platform.gradle.PlatformGradleBuild;
import eu.xenit.alfred.initializr.start.build.platform.gradle.PlatformGradleBuildContributor;
import eu.xenit.alfred.initializr.start.build.root.gradle.RootGradleBuild;
import io.spring.initializr.generator.buildsystem.BuildItemResolver;
import io.spring.initializr.generator.buildsystem.gradle.GradleBuildSystem;
import io.spring.initializr.generator.condition.ConditionalOnBuildSystem;
import io.spring.initializr.generator.io.IndentingWriterFactory;
import io.spring.initializr.generator.project.ProjectDescription;
import io.spring.initializr.generator.project.ProjectGenerationConfiguration;
import java.util.stream.Collectors;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.util.LambdaSafe;
import org.springframework.context.annotation.Bean;

@ProjectGenerationConfiguration
@ConditionalOnBuildSystem(GradleBuildSystem.ID)
public class PlatformDockerGradleBuildConfiguration {

    @Bean
    public PlatformDockerGradleBuild platformDockerGradleBuild(
            ProjectDescription projectDescription,
            RootGradleBuild rootGradleBuild,
            ObjectProvider<BuildItemResolver> buildItemResolver,
            ObjectProvider<BuildCustomizer<?>> buildCustomizers) {

        PlatformDockerGradleBuild gradleBuild = new PlatformDockerGradleBuild(
                projectDescription.getArtifactId() + "-platform-docker",
                buildItemResolver.getIfAvailable(),
                rootGradleBuild);

        LambdaSafe.callbacks(
                BuildCustomizer.class, buildCustomizers.orderedStream().collect(Collectors.toList()),
                gradleBuild, new Object[0]).invoke((customizer) -> {
            customizer.customize(gradleBuild);
        });
        return gradleBuild;
    }

    @Bean
    public PlatformDockerGradleBuildContributor platformDockerGradleBuildContributor(
            CustomGradleBuildWriter buildWriter,
            PlatformDockerGradleBuild build,
            IndentingWriterFactory indentingWriterFactory) {
        return new PlatformDockerGradleBuildContributor(buildWriter, build, indentingWriterFactory);
    }

}
