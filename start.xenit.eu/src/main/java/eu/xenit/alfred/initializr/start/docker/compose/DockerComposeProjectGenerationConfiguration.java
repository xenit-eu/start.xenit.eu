package eu.xenit.alfred.initializr.start.docker.compose;

import eu.xenit.alfred.initializr.generator.docker.compose.DockerComposeCustomizer;
import eu.xenit.alfred.initializr.generator.docker.compose.DockerComposeFiles;
import eu.xenit.alfred.initializr.generator.docker.compose.DockerComposeLocationStrategy;
import eu.xenit.alfred.initializr.generator.docker.compose.DockerComposeYmlContributor;
import eu.xenit.alfred.initializr.generator.docker.compose.DockerComposeYmlWriterDelegate;
import io.spring.initializr.generator.io.IndentingWriterFactory;
import io.spring.initializr.generator.project.ProjectGenerationConfiguration;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.util.LambdaSafe;
import org.springframework.context.annotation.Bean;

@ProjectGenerationConfiguration
public class DockerComposeProjectGenerationConfiguration {

    @Bean
    DockerComposeFiles dockerComposeModel(ObjectProvider<DockerComposeCustomizer> customizers) {
        return this.createDockerComposeModel(customizers.orderedStream().collect(Collectors.toList()));
    }

    private DockerComposeFiles createDockerComposeModel(List<DockerComposeCustomizer> customizers) {

        DockerComposeFiles compose = new DockerComposeFiles();
        LambdaSafe.callbacks(DockerComposeCustomizer.class, customizers, compose, new Object[0])
                .invoke((customizer) -> {
                    customizer.customize(compose);
                });

        return compose;
    }

    @Bean
    DockerComposeYmlContributor mainDockerComposeContributor(DockerComposeFiles compose,
            DockerComposeYmlWriterDelegate writer,
            IndentingWriterFactory indentingWriterFactory,
            DockerComposeLocationStrategy composeLocation) {
        return new DockerComposeYmlContributor(compose, "", writer, indentingWriterFactory, composeLocation);
    }

    @Bean
    DockerComposeYmlWriterDelegate mainDockerComposeYmlWriter() {
        return new DockerComposeYmlWriterDelegate();
    }

    @Bean
    DockerImageEnvNameProvider nameProvider() {
        return module -> module.getId()
                .toUpperCase()
                .replace("-", "_") + "_IMAGE";
    }

}
