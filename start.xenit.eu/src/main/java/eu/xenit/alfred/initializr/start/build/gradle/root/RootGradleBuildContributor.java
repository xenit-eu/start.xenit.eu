package eu.xenit.alfred.initializr.start.build.gradle.root;

import eu.xenit.alfred.initializr.start.build.gradle.CustomGradleBuildWriter;
import eu.xenit.alfred.initializr.start.build.gradle.GradleBuildContributor;
import eu.xenit.alfred.initializr.generator.buildsystem.BuildAssetWriter;
import io.spring.initializr.generator.io.IndentingWriterFactory;
import java.nio.file.Path;
import java.nio.file.Paths;

public class RootGradleBuildContributor extends GradleBuildContributor implements BuildAssetWriter {

    public RootGradleBuildContributor(
            CustomGradleBuildWriter buildWriter,
            RootGradleBuild build,
            IndentingWriterFactory indentingWriterFactory) {
        super(buildWriter, build, indentingWriterFactory);
    }

    @Override
    public Path relativePath() {
        return Paths.get("build.gradle");
    }
}
