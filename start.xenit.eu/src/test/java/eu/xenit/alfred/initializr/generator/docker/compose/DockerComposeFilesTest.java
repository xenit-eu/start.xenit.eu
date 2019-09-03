package eu.xenit.alfred.initializr.generator.docker.compose;

import static org.assertj.core.api.Assertions.assertThat;

import eu.xenit.alfred.initializr.generator.docker.compose.model.DockerComposeModel;
import org.junit.Test;

public class DockerComposeFilesTest {

    @Test
    public void file() {
        DockerComposeFiles dockerComposeFiles = new DockerComposeFiles();

        DockerComposeModel foobar = dockerComposeFiles.file("foobar");
        assertThat(foobar).isNotNull();
        assertThat(foobar).isEqualTo(dockerComposeFiles.file("foobar"));
    }

    @Test
    public void main() {
        DockerComposeFiles dockerComposeFiles = new DockerComposeFiles();

        assertThat(dockerComposeFiles.main()).isNotNull();
        assertThat(dockerComposeFiles.main()).isEqualTo(dockerComposeFiles.main());
        assertThat(dockerComposeFiles.main()).isEqualTo(dockerComposeFiles.file(""));


    }
}