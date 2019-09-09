package eu.xenit.alfred.initializr.generator.buildsystem;

import io.spring.initializr.generator.buildsystem.BuildWriter;
import java.nio.file.Path;

public interface BuildAssetWriter extends BuildWriter {

    Path getRelativePath();
}
