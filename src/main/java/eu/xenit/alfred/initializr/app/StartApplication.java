package eu.xenit.alfred.initializr.app;

import eu.xenit.alfred.initializr.web.HomeController;
import io.spring.initializr.generator.io.IndentingWriterFactory;
import io.spring.initializr.generator.io.SimpleIndentStrategy;
import io.spring.initializr.metadata.InitializrMetadataProvider;
import io.spring.initializr.web.support.InitializrMetadataUpdateStrategy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
        return IndentingWriterFactory.create(new SimpleIndentStrategy("    "));
    }

    @Bean
    public HomeController homeController(InitializrMetadataProvider metadataProvider,
            ResourceUrlProvider resourceUrlProvider) {
        return new HomeController(metadataProvider, resourceUrlProvider);
    }
}
