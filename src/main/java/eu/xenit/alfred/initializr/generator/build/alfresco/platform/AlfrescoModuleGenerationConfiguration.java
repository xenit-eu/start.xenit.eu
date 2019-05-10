package eu.xenit.alfred.initializr.generator.build.alfresco.platform;

import eu.xenit.alfred.initializr.generator.build.alfresco.AlfrescoModuleCustomizer;
import eu.xenit.alfred.initializr.generator.build.alfresco.AlfrescoModulePropertiesContributor;
import eu.xenit.alfred.initializr.generator.build.alfresco.AlfrescoModulePropertiesWriter;
import io.spring.initializr.generator.io.IndentingWriterFactory;
import io.spring.initializr.generator.project.ProjectGenerationConfiguration;
import io.spring.initializr.generator.project.ResolvedProjectDescription;
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

}
