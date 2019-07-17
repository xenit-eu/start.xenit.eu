package eu.xenit.alfred.initializr.generator.extensions.alfred.telemetry;

import eu.xenit.alfred.initializr.generator.alfresco.platform.PlatformBuild;
import eu.xenit.alfred.initializr.generator.build.BuildCustomizer;
import eu.xenit.alfred.initializr.generator.build.RootProjectBuild;
import eu.xenit.alfred.initializr.generator.condition.ConditionalOnRequestedFacet;
import eu.xenit.alfred.initializr.generator.sdk.alfred.AlfredSdk;
import io.spring.initializr.generator.buildsystem.Dependency;
import io.spring.initializr.generator.buildsystem.DependencyScope;
import io.spring.initializr.generator.buildsystem.gradle.GradleBuildSystem;
import io.spring.initializr.generator.buildsystem.gradle.GradleDependency;
import io.spring.initializr.generator.condition.ConditionalOnBuildSystem;
import io.spring.initializr.generator.project.ProjectGenerationConfiguration;
import io.spring.initializr.generator.version.VersionReference;
import io.spring.initializr.metadata.InitializrMetadata;
import io.spring.initializr.metadata.support.MetadataBuildItemMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.util.Assert;

@ProjectGenerationConfiguration
@ConditionalOnRequestedFacet("telemetry")
public class AlfredTelemetryProjectGenerationConfiguration {

    private final InitializrMetadata metadata;

    private static final String ALFRED_TELEMETRY = "alfred-telemetry";

    private static final Dependency DEP_MICROMETER = Dependency
            .withCoordinates("io.micrometer", "micrometer-core")
            .scope(DependencyScope.PROVIDED_RUNTIME)
            .version(VersionReference.ofValue("1.0.6"))
            .build();

    public AlfredTelemetryProjectGenerationConfiguration(InitializrMetadata metadata) {
        this.metadata = metadata;
    }

    @Bean
    @ConditionalOnBuildSystem(GradleBuildSystem.ID)
    public BuildCustomizer<RootProjectBuild> addRootAlfredTelemetryAmp() {
        return (build) -> {
            io.spring.initializr.generator.buildsystem.Dependency dependency = getDependency(ALFRED_TELEMETRY);
            GradleDependency telemetryAmp = GradleDependency.from(dependency)
                    .type("amp")
                    .configuration(AlfredSdk.Configurations.ALFRESCO_AMP)
                    .build();

            build.dependencies().add(ALFRED_TELEMETRY, telemetryAmp);
        };
    }

    @Bean
    @ConditionalOnBuildSystem(GradleBuildSystem.ID)
    public BuildCustomizer<PlatformBuild> addPlatformMicrometerDependency() {
        return (build) -> {
            GradleDependency micrometer = GradleDependency
                    .from(DEP_MICROMETER)
                    .configuration(AlfredSdk.Configurations.ALFRESCO_PROVIDED)
                    .build();

            build.dependencies().add("micrometer", micrometer);
        };
    }

    private Dependency getDependency(String dependencyId) {
        io.spring.initializr.metadata.Dependency alfredTelemetryDep = metadata.getDependencies().get(dependencyId);
        Assert.notNull(alfredTelemetryDep, "Dependency with id 'alfred-telemetry' not found");

        return MetadataBuildItemMapper.toDependency(alfredTelemetryDep);
    }

}
