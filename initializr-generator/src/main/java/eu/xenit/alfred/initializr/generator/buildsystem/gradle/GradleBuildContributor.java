/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package eu.xenit.alfred.initializr.generator.buildsystem.gradle;


import eu.xenit.alfred.initializr.generator.buildsystem.BuildAssetWriter;
import io.spring.initializr.generator.io.IndentingWriter;
import io.spring.initializr.generator.io.IndentingWriterFactory;
import io.spring.initializr.generator.project.contributor.ProjectContributor;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.util.Assert;

/**
 * {@link ProjectContributor} for the module's {@code build.gradle} file.
 *
 * Adopted from io.spring.initializr.generator.spring.build.gradle.GradleBuildProjectContributor
 *
 * Refactored to support multi-module projects
 *
 * @author Andy Wilkinson
 */
public class GradleBuildContributor implements BuildAssetWriter, ProjectContributor {

    protected final MultiProjectGradleBuild build;

    private final CustomGradleBuildWriter buildWriter;
    private final IndentingWriterFactory indentingWriterFactory;

    protected GradleBuildContributor(
            CustomGradleBuildWriter buildWriter,
            MultiProjectGradleBuild build,
            IndentingWriterFactory indentingWriterFactory) {
        this.buildWriter = buildWriter;
        this.build = build;
        this.indentingWriterFactory = indentingWriterFactory;
    }

    @Override
    public void contribute(Path projectRoot) throws IOException {
        // Get path for the build.gradle relative to the project root
        Path buildGradlePath = this.getRelativePath();
        Assert.isTrue(!buildGradlePath.isAbsolute(), "getRelativePath() returned an absolute path");

        // Resolve relative to the project root folder
        buildGradlePath = projectRoot.resolve(buildGradlePath);

        // Make sure directory structure exists
        Files.createDirectories(buildGradlePath.getParent());

        // Make sure we can create a file (and barf if it already exists)
        Files.createFile(buildGradlePath);

        // Write the contents to build.gradle
        writeBuild(Files.newBufferedWriter(buildGradlePath));
    }

    @Override
    public void writeBuild(Writer out) throws IOException {
        try (IndentingWriter writer = this.indentingWriterFactory
                .createIndentingWriter("gradle", out)) {
            this.buildWriter.writeTo(writer, this.build);
        }
    }

    public Path getRelativePath() {
        return Paths.get(build.getName(), "build.gradle");
    }

}
