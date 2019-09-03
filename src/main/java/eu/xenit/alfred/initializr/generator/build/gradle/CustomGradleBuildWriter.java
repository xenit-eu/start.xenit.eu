package eu.xenit.alfred.initializr.generator.build.gradle;

import eu.xenit.alfred.initializr.generator.buildsystem.gradle.GradleProjectDependency;
import io.spring.initializr.generator.buildsystem.Dependency;
import io.spring.initializr.generator.buildsystem.Dependency.Exclusion;
import io.spring.initializr.generator.buildsystem.gradle.GradleBuild;
import io.spring.initializr.generator.buildsystem.gradle.GradleBuild.TaskCustomization;
import io.spring.initializr.generator.buildsystem.gradle.GradleBuild.TaskCustomization.Invocation;
import io.spring.initializr.generator.buildsystem.gradle.GroovyDslGradleBuildWriter;
import io.spring.initializr.generator.io.IndentingWriter;
import io.spring.initializr.generator.version.VersionProperty;
import io.spring.initializr.generator.version.VersionReference;
import java.util.Collection;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Adapted from {@link io.spring.initializr.generator.buildsystem.gradle.GradleBuildWriter}
 *
 * Original class hides utility methods as private, which makes it hard to simply override the {@link
 * #writeTo(IndentingWriter writer, GradleBuild build)} method
 *
 * Customisations:
 * <ul>
 * <li>Support for dependencies on sub-projects: Spring Initializrs' does not support (and does not want to support)
 * multi-module projects at this time</li>
 * <li>Pretty-formatting task-customization invocations: customized tasks with invocations are printet on a single
 * line. Especially for Dynamic Extensions bnd tool configuration, this gets unwieldy.
 * </ul>
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


    @Override
    protected void writeTaskCustomizations(IndentingWriter writer, GradleBuild build) {
        Map<String, GradleBuild.TaskCustomization> taskCustomizations = build.getTaskCustomizations();

        taskCustomizations.forEach((name, customization) -> {
            writer.println();
            writer.println(name + " {");
            writer.indented(() -> writeAutoFormattedTaskCustomization(writer, customization));
            writer.println("}");
        });
    }

    protected void writeAutoFormattedTaskCustomization(IndentingWriter writer, TaskCustomization customization) {
        writeCollection(writer, customization.getInvocations(), writeTaskInvocation(writer));
        writeMap(writer, customization.getAssignments(), (key, value) -> key + " = " + value);
        customization.getNested().forEach((property, nestedCustomization) -> {
            writer.println(property + " {");
            writer.indented(() -> writeAutoFormattedTaskCustomization(writer, nestedCustomization));
            writer.println("}");
        });
    }

    private Consumer<Invocation> writeTaskInvocation(IndentingWriter writer) {
        return invocation -> {
            String arguments = String.join(", ", invocation.getArguments());

            int argumentsCount = invocation.getArguments().size();
            if (argumentsCount == 0) {
                arguments = "()";
            } else if (argumentsCount == 1) {
                arguments = " " + arguments;
            } else {
                arguments = "(" + arguments + ")";
            }

            writer.println(invocation.getTarget() + arguments);
        };
    }

    protected <T> void writeCollection(IndentingWriter writer, Collection<T> collection,
            Consumer<T> callback) {
        writeCollection(writer, collection, callback, null);
    }

    protected <T> void writeCollection(IndentingWriter writer, Collection<T> collection,
            Consumer<T> callback, Runnable beforeWriting) {
        if (!collection.isEmpty()) {
            if (beforeWriting != null) {
                beforeWriting.run();
            }
            collection.forEach(callback::accept);
        }
    }



}