package eu.xenit.alfred.initializr.generator.buildsystem.gradle;

import eu.xenit.alfred.initializr.generator.project.ProjectModule;
import io.spring.initializr.generator.buildsystem.gradle.GradleDependency;
import lombok.Getter;

public class GradleProjectDependency extends GradleDependency {

    @Getter
    private final String path;

    @Getter
    private final String projectConfiguration;

    public GradleProjectDependency(ProjectBuilder builder) {
        super(builder);

        this.path = builder.path;
        this.projectConfiguration = builder.projectConfiguration;
    }

    public static ProjectBuilder from(ProjectModule projectModule){
        return new ProjectBuilder(projectModule.getId());
    }

    public static class ProjectBuilder extends Builder {

        private String path;
        private String projectConfiguration;

        private ProjectBuilder (String path) {
            super("", path);

            this.path = path;
        }

        public ProjectBuilder projectConfiguration(String projectConfiguration) {
            this.projectConfiguration = projectConfiguration;
            return this;
        }

        @Override
        public GradleProjectDependency build() {
            return new GradleProjectDependency(this);
        }
    }
}
