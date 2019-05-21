package eu.xenit.alfred.initializr.generator.docker.compose;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Path;

public interface DockerComposeWriter {

    String getName();
    void writeCompose(Writer out) throws IOException;
    Path composeFile();

}
