package eu.xenit.alfred.initializr.generator.buildsystem;

import java.util.Map;

public interface MultiModuleBuild<TBuild extends BuildSystem> extends BuildSystem {

    String getName();

//    TBuild module(String name);
    TBuild getParent();
    Map<String, TBuild> getModules();

    default boolean hasModules() {
        return !this.getModules().isEmpty();
    }

}
