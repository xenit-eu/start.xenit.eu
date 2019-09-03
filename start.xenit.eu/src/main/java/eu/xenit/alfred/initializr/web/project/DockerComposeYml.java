package eu.xenit.alfred.initializr.web.project;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DockerComposeYml {

    @Getter
    private final String filename;

    @Getter
    private final String content;
}
