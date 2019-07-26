package eu.xenit.alfred.initializr.asserts.build.gradle;

import java.util.function.Consumer;
import org.assertj.core.api.AbstractStringAssert;

public class GradleTaskAssertion extends AbstractStringAssert<GradleTaskAssertion> {

    public GradleTaskAssertion(String actual) {
        super(actual, GradleTaskAssertion.class);
    }

    public GradleTaskAssertion nested(String task, Consumer<GradleTaskAssertion> callback)
    {
        String section = GradleBuildParser.extractSection(task, this.actual);
        callback.accept(new GradleTaskAssertion(section));
        return this;
    }
}
