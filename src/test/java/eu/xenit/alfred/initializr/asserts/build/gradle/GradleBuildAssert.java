package eu.xenit.alfred.initializr.asserts.build.gradle;

import io.spring.initializr.generator.buildsystem.gradle.GradleBuildSystem;
import java.util.function.Consumer;
import lombok.Getter;
import org.assertj.core.api.AbstractStringAssert;

public class GradleBuildAssert extends AbstractStringAssert<GradleBuildAssert> {



    public GradleBuildAssert(String content) {
        super(content, GradleBuildAssert.class);
    }

    public GradleBuildAssert hasPlugin(String plugin) {
        this.getPluginAssert().hasPlugin(plugin);
        return this;
    }

    public GradleBuildAssert hasPlugin(String plugin, String version) {
        this.getPluginAssert().hasPlugin(plugin, version);
        return this;
    }

    public GradleBuildAssert doesNotHavePlugin(String plugin) {
        this.getPluginAssert().doesNotHavePlugin(plugin);
        return this;
    }

    @Getter(lazy=true)
    private final PluginAssert pluginAssert = new PluginAssert(this.actual);

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
        return this;
    }

    @Getter(lazy=true)
    private final DependenciesAssert dependencies = new DependenciesAssert(this.actual);

    public GradleBuildAssert assertTask(String task, Consumer<GradleTaskAssertion> callback)
    {
        String section = GradleBuildParser.extractSection(task, this.actual);
        callback.accept(new GradleTaskAssertion(section));
        return this;
    }
}
