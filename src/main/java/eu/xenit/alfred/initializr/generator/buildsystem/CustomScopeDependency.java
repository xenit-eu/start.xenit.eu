package eu.xenit.alfred.initializr.generator.buildsystem;

import eu.xenit.alfred.initializr.generator.buildsystem.gradle.CustomDependencyScope;
import io.spring.initializr.generator.buildsystem.Dependency;
import io.spring.initializr.generator.buildsystem.DependencyScope;
import io.spring.initializr.generator.version.VersionReference;

public class CustomScopeDependency extends Dependency implements CustomDependencyScope {

    private final String customScope;



    public CustomScopeDependency(String customScope, Dependency dependency) {
        this(customScope, dependency.getGroupId(), dependency.getArtifactId(), dependency.getVersion(),
                dependency.getScope(), dependency.getType());

    }

    public CustomScopeDependency(String customScope, String groupId, String artifactId, VersionReference version,
            DependencyScope scope) {
        this(customScope, groupId, artifactId, version, scope, null);

    }

    public CustomScopeDependency(String customScope, String groupId, String artifactId, VersionReference version,
            DependencyScope scope, String type) {
        super(groupId, artifactId, version, scope, type);

        this.customScope = customScope;
    }


    @Override
    public String getCustomScope() {
        return this.customScope;
    }
}
