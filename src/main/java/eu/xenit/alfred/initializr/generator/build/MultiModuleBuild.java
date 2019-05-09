package eu.xenit.alfred.initializr.generator.build;

import io.spring.initializr.generator.buildsystem.Build;
import java.util.Map;

public interface MultiModuleBuild<TBuild extends Build> {

    TBuild module(String name);
    TBuild getParent();
    Map<String, TBuild> getModules();

    default boolean hasModules() {
        return !this.getModules().isEmpty();
    }

}
