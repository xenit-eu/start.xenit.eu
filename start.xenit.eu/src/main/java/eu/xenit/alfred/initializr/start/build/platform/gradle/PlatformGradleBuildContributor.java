package eu.xenit.alfred.initializr.start.build.platform.gradle;

import eu.xenit.alfred.initializr.generator.buildsystem.gradle.CustomGradleBuildWriter;
import eu.xenit.alfred.initializr.generator.buildsystem.gradle.GradleBuildContributor;
import eu.xenit.alfred.initializr.generator.buildsystem.BuildAssetWriter;
import io.spring.initializr.generator.io.IndentingWriterFactory;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PlatformGradleBuildContributor extends GradleBuildContributor implements BuildAssetWriter {

    PlatformGradleBuildContributor(
            CustomGradleBuildWriter buildWriter,
            PlatformGradleBuild build,
            IndentingWriterFactory indentingWriterFactory) {
        super(buildWriter, build, indentingWriterFactory);
    }
}
