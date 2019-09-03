package eu.xenit.alfred.initializr.generator.docker.compose;

import static org.assertj.core.api.Assertions.assertThat;

import eu.xenit.alfred.initializr.model.docker.DockerComposeModel;
import org.junit.Test;

public class DockerComposeTest {

    @Test
    public void file() {
        DockerCompose dockerCompose = new DockerCompose();

        DockerComposeModel foobar = dockerCompose.file("foobar");
        assertThat(foobar).isNotNull();
        assertThat(foobar).isEqualTo(dockerCompose.file("foobar"));
    }

    @Test
    public void main() {
        DockerCompose dockerCompose = new DockerCompose();

        assertThat(dockerCompose.main()).isNotNull();
        assertThat(dockerCompose.main()).isEqualTo(dockerCompose.main());
        assertThat(dockerCompose.main()).isEqualTo(dockerCompose.file(""));


    }
}