package eu.xenit.alfred.initializr.start.build.gradle.platform;

import eu.xenit.alfred.initializr.start.build.gradle.CustomGradleBuildWriter;
import eu.xenit.alfred.initializr.start.build.gradle.GradleBuildContributor;
import eu.xenit.alfred.initializr.generator.buildsystem.BuildAssetWriter;
import io.spring.initializr.generator.io.IndentingWriterFactory;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PlatformGradleBuildContributor extends GradleBuildContributor implements BuildAssetWriter {

    public PlatformGradleBuildContributor(
            CustomGradleBuildWriter buildWriter,
            PlatformGradleBuild build,
            IndentingWriterFactory indentingWriterFactory) {
        super(buildWriter, build, indentingWriterFactory);
    }

    @Override
    public Path relativePath() {
        return Paths.get(build.getName(), "build.gradle");
    }
}
