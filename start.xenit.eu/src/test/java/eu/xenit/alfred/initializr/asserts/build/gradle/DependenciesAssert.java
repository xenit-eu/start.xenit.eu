package eu.xenit.alfred.initializr.asserts.build.gradle;

import org.assertj.core.api.AbstractStringAssert;

public class DependenciesAssert extends AbstractStringAssert<DependenciesAssert> {

    private DependenciesAssert(String content) {
        super(content, DependenciesAssert.class);
    }

    public DependenciesAssert hasDependency(String configuration, String dependency) {
        return this.contains(configuration + " " + dependency);
    }

    public static DependenciesAssert from(String gradleBuildContent) {
        String dependencies = GradleBuildParser.extractSection("dependencies", gradleBuildContent);
        return new DependenciesAssert(dependencies);
    }
}
