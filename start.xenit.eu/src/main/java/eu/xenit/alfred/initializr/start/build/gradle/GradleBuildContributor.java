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

package eu.xenit.alfred.initializr.start.build.gradle;


import eu.xenit.alfred.initializr.generator.buildsystem.BuildAssetWriter;
import eu.xenit.alfred.initializr.generator.buildsystem.gradle.MultiProjectGradleBuild;
import io.spring.initializr.generator.io.IndentingWriter;
import io.spring.initializr.generator.io.IndentingWriterFactory;
import io.spring.initializr.generator.project.contributor.ProjectContributor;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
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
public abstract class GradleBuildContributor implements BuildAssetWriter, ProjectContributor {

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
        // create module folder if not exists yet
        Files.createDirectories(projectRoot);

        Path buildPath = this.relativePath();
        Assert.isTrue(!buildPath.isAbsolute(), "relativePath() returned an absolute path");

        Path buildGradle = Files.createFile(projectRoot.resolve(buildPath));
        writeBuild(Files.newBufferedWriter(buildGradle));
    }

    @Override
    public void writeBuild(Writer out) throws IOException {
        try (IndentingWriter writer = this.indentingWriterFactory
                .createIndentingWriter("gradle", out)) {
            this.buildWriter.writeTo(writer, this.build);
        }
    }

}
