package eu.xenit.alfred.initializr.start.build.platform.gradle;

import eu.xenit.alfred.initializr.start.build.BuildCustomizer;
import eu.xenit.alfred.initializr.start.project.alfresco.platform.AlfrescoPlatformModule;
import eu.xenit.alfred.initializr.generator.buildsystem.gradle.CustomGradleBuildWriter;
import eu.xenit.alfred.initializr.start.build.root.gradle.RootGradleBuild;
import io.spring.initializr.generator.buildsystem.BuildItemResolver;
import io.spring.initializr.generator.buildsystem.gradle.GradleBuildSystem;
import io.spring.initializr.generator.condition.ConditionalOnBuildSystem;
import io.spring.initializr.generator.io.IndentingWriterFactory;
import io.spring.initializr.generator.project.ProjectGenerationConfiguration;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.util.LambdaSafe;
import org.springframework.context.annotation.Bean;

@ProjectGenerationConfiguration
@ConditionalOnBuildSystem(GradleBuildSystem.ID)
public class PlatformGradleBuildConfiguration {

    @Bean
    public PlatformGradleBuild platformGradleBuild(
            AlfrescoPlatformModule module,
            RootGradleBuild rootGradleBuild,
            ObjectProvider<BuildItemResolver> buildItemResolver,
            ObjectProvider<BuildCustomizer<?>> buildCustomizers) {
        return this.createGradleBuild(
                module.getId(),
                rootGradleBuild,
                buildItemResolver.getIfAvailable(),
                buildCustomizers.orderedStream().collect(Collectors.toList()));
    }

    private PlatformGradleBuild createGradleBuild(
            String name,
            RootGradleBuild rootGradleBuild,
            BuildItemResolver buildItemResolver,
            List<BuildCustomizer<?>> buildCustomizers) {

        PlatformGradleBuild build = new PlatformGradleBuild(name, buildItemResolver, rootGradleBuild);
        LambdaSafe.callbacks(BuildCustomizer.class, buildCustomizers, build, new Object[0]).invoke((customizer) -> {
            customizer.customize(build);
        });
        return build;
    }

    @Bean
    public PlatformGradleBuildContributor platformGradleBuildContributor(
            CustomGradleBuildWriter buildWriter,
            PlatformGradleBuild build,
            IndentingWriterFactory indentingWriterFactory) {
        return new PlatformGradleBuildContributor(buildWriter, build, indentingWriterFactory);
    }

}
