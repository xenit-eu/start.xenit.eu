package eu.xenit.alfred.initializr.generator.build.gradle.platform;

import eu.xenit.alfred.initializr.generator.alfresco.platform.PlatformBuild;
import eu.xenit.alfred.initializr.generator.build.gradle.root.RootGradleBuild;
import eu.xenit.alfred.initializr.generator.buildsystem.gradle.MultiProjectGradleBuild;
import io.spring.initializr.generator.buildsystem.BuildItemResolver;

public class PlatformGradleBuild extends MultiProjectGradleBuild implements PlatformBuild {


    public PlatformGradleBuild(String name, BuildItemResolver buildItemResolver, RootGradleBuild parent) {
        super(name, buildItemResolver, parent);

    }

}
