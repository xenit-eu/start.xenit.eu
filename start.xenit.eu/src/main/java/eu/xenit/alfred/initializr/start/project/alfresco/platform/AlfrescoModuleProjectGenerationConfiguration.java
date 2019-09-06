package eu.xenit.alfred.initializr.start.project.alfresco.platform;

import eu.xenit.alfred.initializr.start.project.alfresco.AlfrescoConstants.MavenRepositories;
import eu.xenit.alfred.initializr.start.project.alfresco.AlfrescoModuleCustomizer;
import eu.xenit.alfred.initializr.start.project.alfresco.AlfrescoModulePropertiesContributor;
import eu.xenit.alfred.initializr.start.project.alfresco.AlfrescoModulePropertiesWriter;
import eu.xenit.alfred.initializr.start.project.alfresco.platform.context.SpringContextModel;
import eu.xenit.alfred.initializr.start.project.alfresco.platform.context.SpringContextModelContributor;
import eu.xenit.alfred.initializr.start.project.alfresco.platform.context.SpringContextModelCustomizer;
import eu.xenit.alfred.initializr.start.build.BuildCustomizer;
import eu.xenit.alfred.initializr.start.build.platform.PlatformBuild;
import io.spring.initializr.generator.buildsystem.BuildItemResolver;
import io.spring.initializr.generator.buildsystem.MavenRepository;
import io.spring.initializr.generator.io.IndentingWriterFactory;
import io.spring.initializr.generator.project.ProjectGenerationConfiguration;
import io.spring.initializr.generator.project.ProjectDescription;
import io.spring.initializr.metadata.InitializrMetadata;
import java.util.stream.Collectors;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.util.LambdaSafe;
import org.springframework.context.annotation.Bean;

@ProjectGenerationConfiguration
public class AlfrescoModuleProjectGenerationConfiguration {


    @Bean
    public AlfrescoPlatformModule alfrescoPlatformModule(ObjectProvider<AlfrescoModuleCustomizer<?>> customizers) {
        AlfrescoPlatformModule alfrescoModule = new AlfrescoPlatformModule();

        LambdaSafe.callbacks(AlfrescoModuleCustomizer.class, customizers.orderedStream().collect(Collectors.toList()),
                alfrescoModule, new Object[0])
                .invoke((customizer) -> customizer.customize(alfrescoModule));

        return alfrescoModule;
    }

    @Bean
    public SpringContextModel springModuleContextModel(ObjectProvider<SpringContextModelCustomizer<?>> customizers) {
        SpringContextModel contextModel = new SpringContextModel();

        LambdaSafe.callbacks(SpringContextModelCustomizer.class,
                customizers.orderedStream().collect(Collectors.toList()), contextModel, new Object[0])
                .invoke((customizer) -> customizer.customize(contextModel));

        return contextModel;
    }


    @Bean
    public SimpleAlfrescoModuleCustomizer platformModuleCustomizer(ProjectDescription description) {
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
            ProjectDescription projectDescription) {
        return (build) -> {
            build.settings().artifact(module.getId());
            build.settings().group(projectDescription.getGroupId());

            build.repositories().add(MavenRepository.MAVEN_CENTRAL);
            build.repositories().add(MavenRepositories.ALFRESCO_PUBLIC);
        };
    }

    @Bean
    public BuildCustomizer<PlatformBuild> platformDependenciesCustomizer(
            AlfrescoPlatformModule module,
            ProjectDescription projectDescription,
            InitializrMetadata metadata,
            BuildItemResolver depResolver) {
        return new PlatformDependenciesBuildCustomizer(module, metadata, projectDescription, depResolver);
    }

    @Bean
    public SpringContextModelContributor springContextModelContributor(
            SpringContextModel contextModel,
            IndentingWriterFactory writerFactory,
            AlfrescoPlatformModule module) {
        return new SpringContextModelContributor(contextModel, writerFactory, module.getProjectLayout());
    }

}
