package eu.xenit.alfred.initializr.generator.build.gradle;

import io.spring.initializr.generator.buildsystem.gradle.GradleBuild;
import io.spring.initializr.generator.buildsystem.gradle.GradleBuildWriter;
import io.spring.initializr.generator.io.IndentingWriterFactory;
import io.spring.initializr.generator.project.contributor.ProjectContributor;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map.Entry;

/**
 * Supports multi-module gradle builds by recursively going through the Gradle sub-projects
 * and delegating the actual contribution to {@link GradleBuildContributor}.
 *
 */
public class MultiProjectGradleBuildContributor implements ProjectContributor {

    private final CustomGradleBuildWriter buildWriter;
    private final MultiProjectGradleBuild build;
    private final IndentingWriterFactory indentingWriterFactory;

    public MultiProjectGradleBuildContributor(CustomGradleBuildWriter buildWriter, MultiProjectGradleBuild build,
            IndentingWriterFactory indentingWriterFactory) {
        this.buildWriter = buildWriter;
        this.build = build;
        this.indentingWriterFactory = indentingWriterFactory;
    }

    @Override
    public void contribute(Path projectRoot) throws IOException {
        ProjectContributor contributor = this.getGradleBuildContributor(this.build);
        contributor.contribute(projectRoot);

        // recursively handle subprojects
        for (Entry<String, MultiProjectGradleBuild> entry : this.build.getModules().entrySet()) {
            String name = entry.getKey();
            MultiProjectGradleBuild subproject = entry.getValue();

            new MultiProjectGradleBuildContributor(buildWriter, subproject, indentingWriterFactory)
                    .contribute(projectRoot.resolve(name));
        }
    }

    public GradleBuildContributor getGradleBuildContributor(MultiProjectGradleBuild project) {
        return new GradleBuildContributor(this.buildWriter, project, this.indentingWriterFactory);
    }
}
