package eu.xenit.alfred.initializr.start.docker.compose;

import eu.xenit.alfred.initializr.start.project.docker.DockerProjectModule;

public interface DockerImageEnvNameProvider {

    String get(DockerProjectModule module);
}
