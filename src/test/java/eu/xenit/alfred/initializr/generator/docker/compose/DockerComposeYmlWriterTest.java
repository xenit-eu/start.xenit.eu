package eu.xenit.alfred.initializr.generator.docker.compose;

import static eu.xenit.alfred.initializr.model.docker.ComposeEnvironment.env;
import static eu.xenit.alfred.initializr.model.docker.ComposeVolumes.volume;
import static org.assertj.core.api.Assertions.assertThat;

import eu.xenit.alfred.initializr.model.docker.DockerComposeModel;
import io.spring.initializr.generator.io.IndentingWriter;
import io.spring.initializr.generator.io.IndentingWriterFactory;
import java.io.IOException;
import java.io.StringWriter;
import org.junit.Test;

public class DockerComposeYmlWriterTest {

    private DockerComposeYmlWriter composeWriter = new DockerComposeYmlWriter();
    private IndentingWriterFactory indentingWriterFactory = IndentingWriterFactory.withDefaultSettings();

    @Test
    public void test() throws IOException {
        StringWriter out = new StringWriter();

        try (IndentingWriter writer = createWriter(out)) {
            this.composeWriter.writeTo(writer, new DockerComposeModel());
        }

        String yml = out.toString();
        assertThat(yml).contains("version: '2.2'");
    }

    @Test
    public void testService() throws IOException {
        StringWriter out = new StringWriter();

        DockerComposeModel compose = new DockerComposeModel();
        compose.getServices()
                .service("test")
                    .image("ubuntu:latest")
                    .volumes("./test:/data")
                    .ports("80")
                    .environment(
                            env("DB_URL", "jdbc:postgresql://database:5432/alfresco"),
                            env("INDEX", "noindex")
                    );

        try (IndentingWriter writer = createWriter(out)) {
            this.composeWriter.writeTo(writer, compose);
        }

        String yml = out.toString();
        assertThat(yml)
                .isNotBlank()
                .isEqualTo(String.join("\n",
                        "version: '2.2'",
                        "",
                        "services:",
                        "    test:",
                        "        image: ubuntu:latest",
                        "        ports:",
                        "        - 80",
                        "        volumes:",
                        "        - ./test:/data",
                        "        environment:",
                        "        - DB_URL=jdbc:postgresql://database:5432/alfresco",
                        "        - INDEX=noindex",
                        "" // EOF on new line
                ));
    }

    @Test
    public void testVolumes() throws IOException {
        StringWriter out = new StringWriter();

        DockerComposeModel compose = new DockerComposeModel().volumes(
                volume("alfresco"),
                volume("postgres")
        );

        try (IndentingWriter writer = createWriter(out)) {
            this.composeWriter.writeTo(writer, compose);
        }

        String yml = out.toString();
        assertThat(yml)
                .isNotBlank()
                .isEqualTo(String.join("\n",
                        "version: '2.2'",
                        "",
                        "volumes:",
                        "    alfresco:",
                        "    postgres:",
                        "" // EOF on new line
                ));

    }

    private IndentingWriter createWriter(StringWriter out) {
        return this.indentingWriterFactory.createIndentingWriter("compose", out);
    }

}