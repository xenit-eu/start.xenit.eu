package eu.xenit.alfred.initializr;

import eu.xenit.alfred.initializr.generator.AlfredSdkProjectGenerator;
import eu.xenit.alfred.initializr.generator.TemporaryFileSupport;
import eu.xenit.alfred.initializr.metadata.AlfredInitializrMetadataBuilder;
import io.spring.initializr.generator.ProjectGenerator;
import io.spring.initializr.metadata.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InitializrApplication {

	public static void main(String[] args) {
		SpringApplication.run(InitializrApplication.class, args);
	}

	@Bean
	public InitializrMetadataProvider initializrMetadataProvider(InitializrProperties properties) {
//		InitializrMetadata metadata = InitializrMetadataBuilder.fromInitializrProperties(properties).build();
		InitializrMetadata metadata = AlfredInitializrMetadataBuilder.withDefaults().build();
		return new SimpleInitializrMetadataProvider(metadata);
	}

	@Bean
	public ProjectGenerator projectGenerator(InitializrMetadataProvider provider)
	{
		return new AlfredSdkProjectGenerator(provider);
	}

	@Bean
	public TemporaryFileSupport temporaryFileSupport()
	{
		return new TemporaryFileSupport();
	}
}
