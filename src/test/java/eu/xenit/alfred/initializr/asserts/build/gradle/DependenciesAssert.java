package eu.xenit.alfred.initializr.asserts.build.gradle;

import org.assertj.core.api.AbstractStringAssert;

public class DependenciesAssert extends AbstractStringAssert<DependenciesAssert> {

    public DependenciesAssert(String gradleBuildContent) {
        super(extractDependenciesSection(gradleBuildContent), DependenciesAssert.class);
    }

    public DependenciesAssert hasDependency(String configuration, String dependency) {
        return this.contains(configuration + " " + dependency);
    }

    private static String extractDependenciesSection(String gradleBuildContent) {
        return GradleBuildParser.extractSection("dependencies", gradleBuildContent);
    }
}
