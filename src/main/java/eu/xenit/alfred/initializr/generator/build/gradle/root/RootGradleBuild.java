package eu.xenit.alfred.initializr.generator.build.gradle.root;

import eu.xenit.alfred.initializr.generator.build.RootProjectBuild;
import eu.xenit.alfred.initializr.generator.buildsystem.gradle.MultiProjectGradleBuild;
import io.spring.initializr.generator.buildsystem.BuildItemResolver;
import lombok.NonNull;

public class RootGradleBuild extends MultiProjectGradleBuild implements RootProjectBuild {

    public RootGradleBuild(@NonNull String name, BuildItemResolver buildItemResolver) {
        super(name, buildItemResolver);
    }
}
