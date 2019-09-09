package eu.xenit.alfred.initializr.asserts.build.gradle;

import java.util.function.Consumer;
import org.assertj.core.api.AbstractStringAssert;

public class GradleTaskAssert extends AbstractStringAssert<GradleTaskAssert> {

    private final String name;

    private GradleTaskAssert(String name, String content) {
        super(content, GradleTaskAssert.class);
        this.name = name;
    }

    public static GradleTaskAssert from(String task, String buildGradle) {
        return new GradleTaskAssert(task, GradleBuildParser.extractSection(task, buildGradle));
    }

    /**
     * Assert that a task configuration contains a given value. If the value is not an expression
     * but a String, the caller is responsible to quote the value
     *
     * @param name the attribute name
     * @param value the value
     * @return {@code this} assertion object.
     */
    public GradleTaskAssert assertAttribute(String name, String value) {
        this.contains(name + " = " + value);
        return myself;
    }

    public GradleTaskAssert getNested(String name) {
        return GradleTaskAssert.from(name, this.actual);
    }

    public GradleTaskAssert assertNested(String name, Consumer<GradleTaskAssert> callback)
    {
        callback.accept(this.getNested(name));
        return myself;
    }

    // TODO assertInvocation ?
}
