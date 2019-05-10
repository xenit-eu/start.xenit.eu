package eu.xenit.alfred.initializr.generator.buildsystem;

import io.spring.initializr.generator.buildsystem.Build;
import io.spring.initializr.generator.buildsystem.Dependency;


public class ProjectDependency extends Dependency {

    private final Build project;
    private final String configuration;

    public ProjectDependency(Build project, String configuration) {
        super(project.getGroup(), project.getArtifact());

        this.project = project;
        this.configuration = configuration;
    }

    public String getConfiguration() {
        return configuration;
    }
}
