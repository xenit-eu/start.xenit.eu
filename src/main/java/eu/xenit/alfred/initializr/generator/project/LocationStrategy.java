package eu.xenit.alfred.initializr.generator.project;

import java.nio.file.Path;

@FunctionalInterface
public interface LocationStrategy {

    Path getLocation();
}
