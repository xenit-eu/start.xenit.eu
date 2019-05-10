package eu.xenit.alfred.initializr.generator.alfresco.platform;

import eu.xenit.alfred.initializr.generator.alfresco.AlfrescoConstants;
import eu.xenit.alfred.initializr.generator.alfresco.AlfrescoConstants.MavenRepositories;
import eu.xenit.alfred.initializr.generator.build.BuildCustomizer;
import eu.xenit.alfred.initializr.generator.alfresco.AlfrescoModuleCustomizer;
import eu.xenit.alfred.initializr.generator.alfresco.AlfrescoModulePropertiesContributor;
import eu.xenit.alfred.initializr.generator.alfresco.AlfrescoModulePropertiesWriter;
import io.spring.initializr.generator.buildsystem.BuildItemResolver;
import io.spring.initializr.generator.buildsystem.MavenRepository;
import io.spring.initializr.generator.io.IndentingWriterFactory;
import io.spring.initializr.generator.project.ProjectGenerationConfiguration;
import io.spring.initializr.generator.project.ResolvedProjectDescription;
import io.spring.initializr.metadata.InitializrMetadata;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.util.LambdaSafe;
import org.springframework.context.annotation.Bean;

@ProjectGenerationConfiguration
public class AlfrescoModuleGenerationConfiguration {


    @Bean
    public AlfrescoPlatformModule alfrescoPlatformModule(ObjectProvider<AlfrescoModuleCustomizer<?>> customizers) {
        return this.createAlfrescoPlatformModule(customizers.orderedStream().collect(Collectors.toList()));
    }

    private AlfrescoPlatformModule createAlfrescoPlatformModule(List<AlfrescoModuleCustomizer<?>> customizers) {
        AlfrescoPlatformModule alfrescoModule = new AlfrescoPlatformModule();

        LambdaSafe.callbacks(AlfrescoModuleCustomizer.class, customizers, alfrescoModule, new Object[0])
                .invoke((customizer) -> {
                    customizer.customize(alfrescoModule);
                });

        return alfrescoModule;
    }

    @Bean
    public SimpleAlfrescoModuleCustomizer platformModuleCustomizer(ResolvedProjectDescription description) {
        return new SimpleAlfrescoModuleCustomizer(description);
    }

    @Bean
    public AlfrescoModulePropertiesContributor modulePropertiesContributor(AlfrescoPlatformModule module,
            IndentingWriterFactory writerFactory, AlfrescoModulePropertiesWriter modulePropertiesWriter) {
        return new AlfrescoModulePropertiesContributor(module.getProjectLayout(), module.getModuleProperties(),
                writerFactory, modulePropertiesWriter);
    }

    @Bean
    public ModuleProjectLayoutContributor platformProjectLayoutContributor(AlfrescoPlatformModule module) {
        return new ModuleProjectLayoutContributor(module);
    }

    @Bean
    public AlfrescoModulePropertiesWriter modulePropertiesWriter() {
        return new AlfrescoModulePropertiesWriter();
    }

    @Bean
    public BuildCustomizer<PlatformBuild> simplePlatformCustomizer(
            AlfrescoPlatformModule module,
            ResolvedProjectDescription projectDescription)
    {
        return (build) -> {
            build.setArtifact(module.getId());
            build.setGroup(projectDescription.getGroupId());
            build.setDescription(module.getDescription());

            // Why is maven.properties not the same as gradle.ext ?
            // build.ext("alfrescoVersion", quote(this.platformModule.getAlfrescoVersion().toString()));

            build.repositories().add(MavenRepository.MAVEN_CENTRAL);
            build.repositories().add(MavenRepositories.ALFRESCO_PUBLIC);
        };
    }

    @Bean
    public BuildCustomizer<PlatformBuild> platformDependenciesCustomizer(
            AlfrescoPlatformModule module,
            ResolvedProjectDescription projectDescription,
            InitializrMetadata metadata,
            BuildItemResolver depResolver)
    {
        return new PlatformDependenciesBuildCustomizer(module, metadata, projectDescription, depResolver);
    }



}
