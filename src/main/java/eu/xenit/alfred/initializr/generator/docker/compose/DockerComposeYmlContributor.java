package eu.xenit.alfred.initializr.generator.docker.compose;

import eu.xenit.alfred.initializr.model.docker.DockerComposeModel;
import io.spring.initializr.generator.io.IndentingWriter;
import io.spring.initializr.generator.io.IndentingWriterFactory;
import io.spring.initializr.generator.project.contributor.ProjectContributor;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import lombok.Getter;

public class DockerComposeYmlContributor implements ProjectContributor, DockerComposeWriter {

    private final DockerCompose compose;

    @Getter
    private final String name;

    private final DockerComposeYmlWriter writer;
    private final IndentingWriterFactory indentingWriterFactory;
    private final DockerComposeLocationStrategy locationStrategy;

    public DockerComposeYmlContributor(DockerCompose compose, String name, DockerComposeYmlWriter writer,
            IndentingWriterFactory indentingWriterFactory, DockerComposeLocationStrategy locationStrategy) {
        this.compose = compose;
        this.name = name;
        this.writer = writer;
        this.indentingWriterFactory = indentingWriterFactory;
        this.locationStrategy = locationStrategy;
    }

    @Override
    public void contribute(Path projectRoot) throws IOException {
        // make sure directory exists
        Files.createDirectories(projectRoot.resolve(this.locationStrategy.getComposePath()));

        Path yml = Files.createFile(projectRoot.resolve(composeFile()));
        writeCompose(Files.newBufferedWriter(yml));
    }

    @Override
    public void writeCompose(Writer out) throws IOException {
        DockerComposeModel model = this.compose.file(this.name);
        try (IndentingWriter writer = this.indentingWriterFactory.createIndentingWriter("compose", out)) {
            this.writer.writeTo(writer, model);
        }
    }

    @Override
    public Path composeFile() {
        Path composePath = this.locationStrategy.getComposePath();

        return composePath.resolve(this.composeFilename());
    }

    @Override
    public String composeFilename() {
        if ("".equalsIgnoreCase(this.name)) {
            return "docker-compose.yml";
        }

        return String.format("docker-compose-%s.yml", this.name);
    }
}
