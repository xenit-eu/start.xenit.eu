package eu.xenit.alfred.initializr.start.build.platform.gradle;

import eu.xenit.alfred.initializr.start.build.platform.PlatformBuild;
import eu.xenit.alfred.initializr.start.build.root.gradle.RootGradleBuild;
import eu.xenit.alfred.initializr.generator.buildsystem.gradle.MultiProjectGradleBuild;
import io.spring.initializr.generator.buildsystem.BuildItemResolver;

public class PlatformGradleBuild extends MultiProjectGradleBuild implements PlatformBuild {


    public PlatformGradleBuild(String name, BuildItemResolver buildItemResolver, RootGradleBuild parent) {
        super(name, buildItemResolver, parent);

    }

}
