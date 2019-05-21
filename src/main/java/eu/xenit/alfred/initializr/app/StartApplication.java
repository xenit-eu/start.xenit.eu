package eu.xenit.alfred.initializr.app;

import eu.xenit.alfred.initializr.web.HomeController;
import eu.xenit.alfred.initializr.web.project.CustomProjectGenerationInvoker;
import io.spring.initializr.generator.io.IndentingWriterFactory;
import io.spring.initializr.generator.io.SimpleIndentStrategy;
import io.spring.initializr.metadata.InitializrMetadataProvider;
import io.spring.initializr.web.project.ProjectGenerationInvoker;
import io.spring.initializr.web.project.ProjectRequestToDescriptionConverter;
import io.spring.initializr.web.support.InitializrMetadataUpdateStrategy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.resource.ResourceUrlProvider;

@SpringBootApplication
public class StartApplication {

    public static void main(String[] args) {
        SpringApplication.run(StartApplication.class, args);
    }

    @Bean
    public InitializrMetadataUpdateStrategy initializrMetadataUpdateStrategy() {
        return (metadata) -> metadata;
    }

    @Bean
    public IndentingWriterFactory indentingWriterFactory() {
        return IndentingWriterFactory.withDefaultSettings();
    }

    @Bean
    public HomeController homeController(InitializrMetadataProvider metadataProvider,
            ResourceUrlProvider resourceUrlProvider) {
        return new HomeController(metadataProvider, resourceUrlProvider);
    }

    @Bean
    public CustomProjectGenerationInvoker projectGenerationInvoker(
            ApplicationContext applicationContext,
            ApplicationEventPublisher eventPublisher,
            ProjectRequestToDescriptionConverter projectRequestToDescriptionConverter) {
        return new CustomProjectGenerationInvoker(applicationContext, eventPublisher,
                projectRequestToDescriptionConverter);
    }

}
