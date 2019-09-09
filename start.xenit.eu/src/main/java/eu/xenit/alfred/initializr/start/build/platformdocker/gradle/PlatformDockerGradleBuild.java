package eu.xenit.alfred.initializr.start.build.platformdocker.gradle;

import eu.xenit.alfred.initializr.generator.buildsystem.gradle.DockerGradleBuild;
import eu.xenit.alfred.initializr.generator.buildsystem.gradle.MultiProjectGradleBuild;
import eu.xenit.alfred.initializr.start.build.platformdocker.PlatformDockerBuild;
import eu.xenit.alfred.initializr.start.build.root.gradle.RootGradleBuild;
import io.spring.initializr.generator.buildsystem.BuildItemResolver;

public class PlatformDockerGradleBuild extends MultiProjectGradleBuild
        implements PlatformDockerBuild, DockerGradleBuild {

    PlatformDockerGradleBuild(String name, BuildItemResolver buildItemResolver, RootGradleBuild parent) {
        super(name, buildItemResolver, parent);

    }
}
