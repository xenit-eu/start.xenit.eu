package eu.xenit.alfred.initializr.start.build.platformdocker.gradle;

import eu.xenit.alfred.initializr.generator.buildsystem.BuildAssetWriter;
import eu.xenit.alfred.initializr.generator.buildsystem.gradle.CustomGradleBuildWriter;
import eu.xenit.alfred.initializr.generator.buildsystem.gradle.GradleBuildContributor;
import eu.xenit.alfred.initializr.generator.buildsystem.gradle.MultiProjectGradleBuild;
import eu.xenit.alfred.initializr.start.build.platform.gradle.PlatformGradleBuild;
import io.spring.initializr.generator.buildsystem.gradle.GradleBuild;
import io.spring.initializr.generator.io.IndentingWriterFactory;
import java.nio.file.Path;
import java.nio.file.Paths;

class PlatformDockerGradleBuildContributor extends GradleBuildContributor implements BuildAssetWriter {

    PlatformDockerGradleBuildContributor(
            CustomGradleBuildWriter buildWriter,
            PlatformDockerGradleBuild build,
            IndentingWriterFactory indentingWriterFactory) {
        super(buildWriter, build, indentingWriterFactory);
    }
}
