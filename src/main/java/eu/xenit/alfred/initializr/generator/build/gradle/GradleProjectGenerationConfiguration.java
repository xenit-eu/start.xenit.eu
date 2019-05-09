package eu.xenit.alfred.initializr.generator.build.gradle;

import static org.springframework.util.StringUtils.quote;

import eu.xenit.alfred.initializr.generator.build.BuildCustomizer;
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
    public MultiProjectGradleBuild gradleBuild(ObjectProvider<BuildItemResolver> buildItemResolver,
            ObjectProvider<BuildCustomizer<?>> buildCustomizers) {
        return this.createGradleBuild(
                buildItemResolver.getIfAvailable(),
                buildCustomizers.orderedStream().collect(Collectors.toList()));
    }

    private MultiProjectGradleBuild createGradleBuild(BuildItemResolver buildItemResolver,
            List<BuildCustomizer<?>> buildCustomizers) {

        MultiProjectGradleBuild build = buildItemResolver != null ? new MultiProjectGradleBuild(buildItemResolver) : new MultiProjectGradleBuild();
        LambdaSafe.callbacks(BuildCustomizer.class, buildCustomizers, build, new Object[0]).invoke((customizer) -> {
            customizer.customize(build);
        });
        return build;
    }

    @Bean
    public BuildCustomizer<MultiProjectGradleBuild> addDockerVersion(ResolvedProjectDescription projectDescription) {
        return (build) -> build.ext("alfrescoVersion", quote(projectDescription.getPlatformVersion().toString()));

    }

//    @Bean
//    public GradleBuildContributor gradleBuildProjectContributor(GradleBuildWriter buildWriter,
//            MultiProjectGradleBuild build) {
//        return new GradleBuildContributor(buildWriter, build, this.indentingWriterFactory);
//    }
    @Bean
    public MultiProjectGradleBuildContributor gradleBuildProjectContributor(CustomGradleBuildWriter buildWriter,
            MultiProjectGradleBuild build, IndentingWriterFactory indentingWriterFactory) {
        return new MultiProjectGradleBuildContributor(buildWriter, build, indentingWriterFactory);
    }

    @Bean
    public GradleWrapperContributor gradle4WrapperContributor() {
        return new GradleWrapperContributor("4");
    }

    @Bean
    public SettingsGradleProjectContributor settingsGradleProjectContributor(
            MultiProjectGradleBuild build, IndentingWriterFactory indentingWriterFactory) {
        return new SettingsGradleProjectContributor(build, indentingWriterFactory);
    }

    @Bean
    public CustomGradleBuildWriter gradleBuildWriter() {
        return new CustomGradleBuildWriter();
    }

}