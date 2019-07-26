package eu.xenit.alfred.initializr.generator.extensions.dynext;

import static org.springframework.util.StringUtils.quote;

import eu.xenit.alfred.initializr.generator.alfresco.platform.PlatformBuild;
import eu.xenit.alfred.initializr.generator.build.BuildCustomizer;
import eu.xenit.alfred.initializr.generator.build.gradle.platform.PlatformGradleBuild;
import eu.xenit.alfred.initializr.generator.build.gradle.root.RootGradleBuild;
import eu.xenit.alfred.initializr.generator.condition.ConditionalOnRequestedFacet;
import eu.xenit.alfred.initializr.generator.packaging.amp.AmpPackaging;
import eu.xenit.alfred.initializr.generator.sdk.alfred.AlfredSdk;
import eu.xenit.alfred.initializr.generator.sdk.alfred.AlfredSdk.Configurations;
import io.spring.initializr.generator.buildsystem.Dependency;
import io.spring.initializr.generator.buildsystem.gradle.GradleBuildSystem;
import io.spring.initializr.generator.buildsystem.gradle.GradleDependency;
import io.spring.initializr.generator.condition.ConditionalOnBuildSystem;
import io.spring.initializr.generator.condition.ConditionalOnPackaging;
import io.spring.initializr.generator.project.ProjectGenerationConfiguration;
import io.spring.initializr.generator.project.ResolvedProjectDescription;
import io.spring.initializr.generator.version.VersionReference;
import io.spring.initializr.metadata.support.MetadataBuildItemResolver;
import org.springframework.context.annotation.Bean;

@ProjectGenerationConfiguration
@ConditionalOnRequestedFacet("dynamic-extensions")
public class DynamicExtensionsProjectGenerationConfiguration {

    private final static String DYN_EXT = "dynamic-extensions";

    private final MetadataBuildItemResolver resolver;

    public DynamicExtensionsProjectGenerationConfiguration(MetadataBuildItemResolver resolver) {
        this.resolver = resolver;
    }

    @Bean
    @ConditionalOnBuildSystem(GradleBuildSystem.ID)
    public BuildCustomizer<RootGradleBuild> addRootDynamicExtensionsAmp(MetadataBuildItemResolver resolver) {
        return (build) -> {

            String dynamicExtensionVersion = this.getDynamicExtensionsDependency().getVersion().getValue();
            build.ext("dynamicExtensionsVersion", quote(dynamicExtensionVersion));

            Dependency alfrescoAmp = GradleDependency.from(this.getDynamicExtensionsDependency())
                    .type("amp")
                    .version(VersionReference.ofProperty("dynamic-extensions-version"))
                    .configuration(Configurations.ALFRESCO_AMP)
                    .build();
            build.dependencies().add(DYN_EXT, alfrescoAmp);
        };
    }

    @Bean
    @ConditionalOnBuildSystem(GradleBuildSystem.ID)
    @ConditionalOnPackaging(AmpPackaging.ID)
    public BuildCustomizer<PlatformGradleBuild> packageDynamicExtensionBundleAsAmp() {
        return (build) -> {

            build.customizeTask("sourceSets", sourceSets -> {
                sourceSets.nested("main", main -> {
                    main.nested("amp", amp -> {
                        amp.invoke("dynamicExtension");
                    });
                });
            });
        };
    }

    @Bean
    @ConditionalOnBuildSystem(GradleBuildSystem.ID)
    public BuildCustomizer<PlatformGradleBuild> platformConfigureDynamicExtensionsPlugin(
            ResolvedProjectDescription project) {
        return (build) -> {

            String pluginVersion = this.getDynamicExtensionsDependency().getVersion().getValue();
            build.addPlugin("eu.xenit.de", pluginVersion);

            // dependencies on -webscripts and -annotations are added by the plugin
            // TODO review we want to change this ?!

            build.customizeTask("jar", jar -> {
                jar.invoke("bnd",
                        "'Alfresco-Spring-Configuration': '"+project.getPackageName()+"'"
                );
            });
        };
    }

    private Dependency getDynamicExtensionsDependency() {
        return resolver.resolveDependency("dynamic-extensions");
    }

}
