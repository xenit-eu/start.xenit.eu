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

package eu.xenit.alfred.initializr.generator.build.gradle.root;

import eu.xenit.alfred.initializr.generator.buildsystem.gradle.MultiProjectGradleBuild;
import io.spring.initializr.generator.io.IndentingWriter;
import io.spring.initializr.generator.io.IndentingWriterFactory;
import io.spring.initializr.generator.project.contributor.ProjectContributor;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * {@link ProjectContributor} for the module's {@code settings.gradle} file.
 *
 * @author Andy Wilkinson
 */
class SettingsGradleProjectContributor implements ProjectContributor {

    private final RootGradleBuild build;

    private final IndentingWriterFactory indentingWriterFactory;

    private final CustomGradleSettingsWriter settingsWriter;

    SettingsGradleProjectContributor(RootGradleBuild build, IndentingWriterFactory indentingWriterFactory) {
        this.build = build;
        this.indentingWriterFactory = indentingWriterFactory;
        this.settingsWriter = new CustomGradleSettingsWriter();
    }

    @Override
    public void contribute(Path projectRoot) throws IOException {
        Path settingsGradle = Files.createFile(projectRoot.resolve("settings.gradle"));
        try (IndentingWriter writer = this.indentingWriterFactory.createIndentingWriter(
                "gradle", Files.newBufferedWriter(settingsGradle))) {
            this.settingsWriter.writeTo(writer, this.build);
        }
    }

}
