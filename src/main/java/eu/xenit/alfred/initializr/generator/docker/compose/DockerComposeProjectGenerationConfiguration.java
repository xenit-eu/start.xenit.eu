package eu.xenit.alfred.initializr.generator.docker.compose;

import io.spring.initializr.generator.buildsystem.BuildSystem;
import io.spring.initializr.generator.buildsystem.gradle.GradleBuildSystem;
import io.spring.initializr.generator.condition.ConditionalOnBuildSystem;
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
    public DockerCompose dockerComposeModel(ObjectProvider<DockerComposeCustomizer> customizers) {
        return this.createDockerComposeModel(customizers.orderedStream().collect(Collectors.toList()));
    }

    private DockerCompose createDockerComposeModel(List<DockerComposeCustomizer> customizers) {

        DockerCompose compose = new DockerCompose();
        LambdaSafe.callbacks(DockerComposeCustomizer.class, customizers, compose, new Object[0])
                .invoke((customizer) -> {
                    customizer.customize(compose);
                });

        return compose;
    }
    
    @Bean
    public DockerComposeYmlContributor mainDockerComposeContributor(DockerCompose compose,
            DockerComposeYmlWriter writer,
            IndentingWriterFactory indentingWriterFactory,
            DockerComposeLocationStrategy composeLocation) {
        return new DockerComposeYmlContributor(compose, "", writer, indentingWriterFactory, composeLocation);
    }

    @Bean
    public DockerComposeYmlWriter mainDockerComposeYmlWriter() {
        return new DockerComposeYmlWriter();
    }
}
