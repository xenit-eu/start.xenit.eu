package eu.xenit.alfred.initializr.generator.docker.compose.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class ComposeVolumeInfo {

    @Getter
    private final String name;

}
