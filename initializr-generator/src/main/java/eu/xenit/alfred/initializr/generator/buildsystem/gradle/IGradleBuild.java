package eu.xenit.alfred.initializr.generator.buildsystem.gradle;

import eu.xenit.alfred.initializr.generator.buildsystem.Build;
import io.spring.initializr.generator.buildsystem.gradle.GradlePluginContainer;
import io.spring.initializr.generator.buildsystem.gradle.GradleTaskContainer;

public interface IGradleBuild extends Build {

    GradlePluginContainer plugins();
    GradleTaskContainer tasks();

    //void customizeTask(String taskName, Consumer<TaskCustomization> customizer);
}
