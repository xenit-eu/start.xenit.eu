package eu.xenit.alfred.initializr.start.sdk.alfred.platform;

import eu.xenit.alfred.initializr.start.alfresco.platform.AlfrescoPlatformModule;
import eu.xenit.alfred.initializr.start.build.BuildCustomizer;
import eu.xenit.alfred.initializr.start.build.gradle.platform.PlatformGradleBuild;
import eu.xenit.alfred.initializr.start.sdk.alfred.AlfredSdk.Dependencies;

public class AlfredSdkPlatformModuleGradleCustomizer implements BuildCustomizer<PlatformGradleBuild> {

    private final AlfrescoPlatformModule platformModule;

    public AlfredSdkPlatformModuleGradleCustomizer(AlfrescoPlatformModule platformModule) {
        this.platformModule = platformModule;
    }

    @Override
    public void customize(PlatformGradleBuild build) {

        build.plugins().add("eu.xenit.alfresco", plugin -> plugin.setVersion("0.2.0"));
        build.plugins().add("eu.xenit.amp", plugin -> plugin.setVersion("0.2.0"));

        build.dependencies().add("alfresco-repository", Dependencies.ALFRESCO_REPOSITORY);
    }

}
