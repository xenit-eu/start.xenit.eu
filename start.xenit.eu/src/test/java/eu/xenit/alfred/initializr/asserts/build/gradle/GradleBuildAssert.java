package eu.xenit.alfred.initializr.asserts.build.gradle;

import eu.xenit.alfred.initializr.metadata.Dependencies;
import io.spring.initializr.generator.buildsystem.gradle.GradleTask;
import java.util.function.Consumer;
import lombok.Getter;
import org.assertj.core.api.AbstractStringAssert;

public class GradleBuildAssert extends AbstractStringAssert<GradleBuildAssert> {

    public GradleBuildAssert(String content) {
        super(content, GradleBuildAssert.class);
    }

    public GradleBuildAssert hasPlugin(String plugin) {
        this.getPluginAssert().hasPlugin(plugin);
        return myself;
    }

    public GradleBuildAssert hasPlugin(String plugin, String version) {
        this.getPluginAssert().hasPlugin(plugin, version);
        return myself;
    }

    public GradleBuildAssert doesNotHavePlugin(String plugin) {
        this.getPluginAssert().doesNotHavePlugin(plugin);
        return myself;
    }

    @Getter(lazy=true)
    private final PluginAssert pluginAssert = PluginAssert.from(this.actual);

    public GradleBuildAssert hasVersion(String version) {
        return this.contains("version = '" + version + "'");
    }

    public GradleBuildAssert hasJavaVersion(String javaVersion) {
        return this.contains("sourceCompatibility = '" + javaVersion + "'");
    }

    public GradleBuildAssert hasRepository(String url) {
        return this.contains("maven { url '" + url + "' }");
    }

    public GradleBuildAssert hasProperties(String... values) {
        StringBuilder builder = new StringBuilder(String.format("ext {%n"));
        if (values.length % 2 == 1) {
            throw new IllegalArgumentException("Size must be even, it is a set of property=value pairs");
        } else {
            for(int i = 0; i < values.length; i += 2) {
                builder.append(String.format("\tset('%s', '%s')%n", values[i], values[i + 1]));
            }

            builder.append("}");
            return this.contains(builder.toString());
        }
    }

    public GradleBuildAssert hasDependency(String configuration, String dependency) {
        this.getDependencies().hasDependency(configuration, dependency);
        return myself;
    }

    @Getter(lazy=true)
    private final DependenciesAssert dependencies = DependenciesAssert.from(this.actual);

    public GradleBuildAssert assertDependencies(Consumer<DependenciesAssert> callback) {
        callback.accept(this.getDependencies());
        return myself;
    }

    public GradleTaskAssert getTask(String task) {
        return GradleTaskAssert.from(task, this.actual);
    }

    public GradleBuildAssert assertTask(String task, Consumer<GradleTaskAssert> callback) {
        callback.accept(this.getTask(task));
        return myself;
    }

}
