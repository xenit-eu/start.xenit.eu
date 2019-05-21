package eu.xenit.alfred.initializr.web.project;

import java.nio.file.Path;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DockerComposeYml {

    @Getter
    private final Path path;

    @Getter
    private final String content;
}
