package eu.xenit.alfred.initializr.generator.buildsystem;

import eu.xenit.alfred.initializr.generator.buildsystem.gradle.CustomDependencyScope;
import eu.xenit.alfred.initializr.generator.project.Project;
import io.spring.initializr.generator.buildsystem.Dependency;
import lombok.Getter;


public class ProjectDependency extends Dependency implements CustomDependencyScope {

    @Getter
    private final Project project;

    @Getter
    private final String customScope;

    @Getter
    private final String configuration;

    public ProjectDependency(Project project, String groupId) {
        this(project, groupId, null, null);
    }

    public ProjectDependency(Project project, String groupId, String customScope) {
        this(project, groupId, customScope, null);
    }

    public ProjectDependency(Project project, String groupId, String customScope, String configuration) {
        super(groupId, project.getId());

        this.project = project;
        this.configuration = configuration;
        this.customScope = customScope;
    }

}
