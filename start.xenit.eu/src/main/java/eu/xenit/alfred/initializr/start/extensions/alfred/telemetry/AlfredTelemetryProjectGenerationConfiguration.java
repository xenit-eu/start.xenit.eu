package eu.xenit.alfred.initializr.start.extensions.alfred.telemetry;

import eu.xenit.alfred.initializr.start.build.platform.PlatformBuild;
import eu.xenit.alfred.initializr.start.build.BuildCustomizer;
import eu.xenit.alfred.initializr.start.build.root.gradle.RootGradleBuild;
import eu.xenit.alfred.initializr.generator.condition.ConditionalOnRequestedFacet;
import eu.xenit.alfred.initializr.start.extensions.alfred.telemetry.AlfredTelemetryConstants.AlfredTelemetry;
import eu.xenit.alfred.initializr.start.extensions.alfred.telemetry.AlfredTelemetryConstants.Micrometer;
import eu.xenit.alfred.initializr.start.sdk.alfred.AlfredSdk;
import io.spring.initializr.generator.buildsystem.Dependency;
import io.spring.initializr.generator.buildsystem.gradle.GradleBuildSystem;
import io.spring.initializr.generator.buildsystem.gradle.GradleDependency;
import io.spring.initializr.generator.condition.ConditionalOnBuildSystem;
import io.spring.initializr.generator.project.ProjectGenerationConfiguration;
import io.spring.initializr.generator.version.VersionProperty;
import io.spring.initializr.generator.version.VersionReference;
import io.spring.initializr.metadata.InitializrMetadata;
import io.spring.initializr.metadata.support.MetadataBuildItemMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.util.Assert;

@ProjectGenerationConfiguration
@ConditionalOnRequestedFacet("telemetry")
public class AlfredTelemetryProjectGenerationConfiguration {

    private final InitializrMetadata metadata;

    public AlfredTelemetryProjectGenerationConfiguration(InitializrMetadata metadata) {
        this.metadata = metadata;
    }

    @Bean
    @ConditionalOnBuildSystem(GradleBuildSystem.ID)
    public BuildCustomizer<RootGradleBuild> addRootAlfredTelemetryAmp() {
        return (build) -> {
            io.spring.initializr.generator.buildsystem.Dependency dependency = getDependency(
                    AlfredTelemetry.ID);
            GradleDependency telemetryAmp = GradleDependency.from(dependency)
                    .type("amp")
                    .version(VersionReference.ofProperty("alfred-telemetry-version"))
                    .configuration(AlfredSdk.Configurations.ALFRESCO_AMP)
                    .build();

            // FIXME those .VERSION properties should NOT be hard coded, but
            build.properties().version(VersionProperty.of("micrometer-version"), Micrometer.VERSION);
            build.properties().version(VersionProperty.of("alfred-telemetry-version"), AlfredTelemetry.VERSION);

            build.dependencies().add(AlfredTelemetry.ID, telemetryAmp);
        };
    }

    @Bean
    @ConditionalOnBuildSystem(GradleBuildSystem.ID)
    public BuildCustomizer<PlatformBuild> addPlatformMicrometerDependency() {
        return (build) -> {
            GradleDependency micrometer = GradleDependency
                    .from(Micrometer.DEPENDENCY)
                    .configuration(AlfredSdk.Configurations.ALFRESCO_PROVIDED)
                    .build();

            build.dependencies().add("micrometer", micrometer);
        };
    }

    private Dependency getDependency(String dependencyId) {
        io.spring.initializr.metadata.Dependency alfredTelemetryDep = metadata.getDependencies().get(dependencyId);
        Assert.notNull(alfredTelemetryDep, "Dependency with id '"+dependencyId+"' not found");

        return MetadataBuildItemMapper.toDependency(alfredTelemetryDep);
    }

}
