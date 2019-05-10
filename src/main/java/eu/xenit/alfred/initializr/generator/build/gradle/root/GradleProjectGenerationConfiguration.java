package eu.xenit.alfred.initializr.generator.build.gradle.root;

import static org.springframework.util.StringUtils.quote;

import eu.xenit.alfred.initializr.generator.build.BuildCustomizer;
import eu.xenit.alfred.initializr.generator.build.gradle.CustomGradleBuildWriter;
import eu.xenit.alfred.initializr.generator.build.gradle.GradleBuildContributor;
import eu.xenit.alfred.initializr.generator.buildsystem.gradle.MultiProjectGradleBuild;
import io.spring.initializr.generator.buildsystem.BuildItemResolver;
import io.spring.initializr.generator.condition.ConditionalOnBuildSystem;
import io.spring.initializr.generator.io.IndentingWriterFactory;
import io.spring.initializr.generator.project.ProjectGenerationConfiguration;
import io.spring.initializr.generator.project.ResolvedProjectDescription;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.util.LambdaSafe;
import org.springframework.context.annotation.Bean;

@ProjectGenerationConfiguration
@ConditionalOnBuildSystem("gradle")
public class GradleProjectGenerationConfiguration {

    @Bean
    public RootGradleBuild gradleBuild(
            ResolvedProjectDescription projectDescription,
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
    public BuildCustomizer<RootGradleBuild> addDockerVersion(ResolvedProjectDescription projectDescription) {
        return (build) -> build.ext("alfrescoVersion", quote(projectDescription.getPlatformVersion().toString()));

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