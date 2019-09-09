package eu.xenit.alfred.initializr.generator.buildsystem.gradle;

import eu.xenit.alfred.initializr.generator.buildsystem.gradle.GradleProjectDependency;
import io.spring.initializr.generator.buildsystem.Dependency;
import io.spring.initializr.generator.buildsystem.Dependency.Exclusion;
import io.spring.initializr.generator.buildsystem.gradle.GradleBuild;
import io.spring.initializr.generator.buildsystem.gradle.GroovyDslGradleBuildWriter;
import io.spring.initializr.generator.io.IndentingWriter;
import io.spring.initializr.generator.version.VersionProperty;
import io.spring.initializr.generator.version.VersionReference;
import java.util.Map;

/**
 * Adapted from {@link io.spring.initializr.generator.buildsystem.gradle.GradleBuildWriter}
 *
 * Original class hides utility methods as private, which makes it hard to simply override the {@link
 * #writeTo(IndentingWriter writer, GradleBuild build)} method
 */
public class CustomGradleBuildWriter extends GroovyDslGradleBuildWriter {

    @Override
    protected void writeExtraProperties(IndentingWriter writer,
            Map<String, String> allProperties) {
        writeNestedCollection(writer, "ext", allProperties.entrySet(),
                (e) -> getFormattedExtraProperty(e.getKey(), e.getValue()),
                writer::println);
    }

    private String getFormattedExtraProperty(String key, String value) {
        return String.format("%s = %s", key, value);
    }


    protected void writeDependency(IndentingWriter writer, Dependency dependency) {
        String quoteStyle = this.determineQuoteStyle(dependency.getVersion());
        String version = this.determineVersion(dependency.getVersion());
        String type = dependency.getType();
        boolean hasExclusions = !dependency.getExclusions().isEmpty();
        boolean isProjectDependency = dependency instanceof GradleProjectDependency;

        writer.print(this.configurationForDependency(dependency));
        writer.print(hasExclusions ? "(" : " ");

        if (isProjectDependency) {
            GradleProjectDependency projectDependency = (GradleProjectDependency) dependency;
            writer.print("project(path: ':");
            writer.print(projectDependency.getPath());
            writer.print("'");
            if (projectDependency.getProjectConfiguration() != null) {
                writer.print(", configuration: '");
                writer.print(projectDependency.getProjectConfiguration());
                writer.print("'");
            }
            writer.print(")");

        } else {
            writer.print(quoteStyle + dependency.getGroupId() + ":" + dependency.getArtifactId()
                    + (version != null ? ":" + version : "") + (type != null ? "@" + type : "") + quoteStyle);
        }

        if (hasExclusions) {
            writer.println(") {");
            writer.indented(() -> {
                this.writeCollection(writer, dependency.getExclusions(), this::dependencyExclusionAsString);
            });
            writer.println("}");
        } else {
            writer.println();
        }

    }

    private String determineQuoteStyle(VersionReference versionReference) {
        return (versionReference != null && versionReference.isProperty()) ? "\"" : "'";
    }

    private String determineVersion(VersionReference versionReference) {
        if (versionReference != null) {
            if (versionReference.isProperty()) {
                VersionProperty property = versionReference.getProperty();
                return "${"
                        + (property.isInternal() ? property.toCamelCaseFormat()
                        : "property('" + property.toStandardFormat() + "')")
                        + "}";
            }
            return versionReference.getValue();
        }
        return null;
    }

    private String dependencyExclusionAsString(Exclusion exclusion) {
        return "exclude group: '" + exclusion.getGroupId() + "', module: '" + exclusion.getArtifactId() + "'";
    }

}