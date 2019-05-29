//package eu.xenit.alfred.initializr.generator.buildsystem;
//
//import eu.xenit.alfred.initializr.generator.buildsystem.gradle.CustomDependencyScope;
//import eu.xenit.alfred.initializr.generator.project.ProjectModule;
//import io.spring.initializr.generator.buildsystem.Dependency;
//import io.spring.initializr.generator.buildsystem.DependencyScope;
//import lombok.Getter;
//
//
//public class ProjectDependency extends Dependency implements CustomDependencyScope {
//
//    @Getter
//    private final ProjectModule project;
//
//    @Getter
//    private final String customScope;
//
//    @Getter
//    private final String configuration;
//
//    public ProjectDependency(ProjectModule project, String groupId) {
//        this(project, groupId, null, null);
//    }
//
//    public ProjectDependency(ProjectModule project, String groupId, String customScope) {
//        this(project, groupId, customScope, null);
//    }
//
//    public ProjectDependency(ProjectModule project, String groupId, String customScope, String configuration) {
//        super(Dependency.withCoordinates(groupId, project.getId()));
//
//        this.project = project;
//        this.configuration = configuration;
//        this.customScope = customScope;
//    }
//
//}
