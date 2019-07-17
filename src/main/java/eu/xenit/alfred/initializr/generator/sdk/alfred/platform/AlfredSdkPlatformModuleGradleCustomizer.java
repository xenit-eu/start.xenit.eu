package eu.xenit.alfred.initializr.generator.sdk.alfred.platform;

import static org.springframework.util.StringUtils.quote;

import eu.xenit.alfred.initializr.generator.build.BuildCustomizer;
import eu.xenit.alfred.initializr.generator.alfresco.platform.AlfrescoPlatformModule;
import eu.xenit.alfred.initializr.generator.build.gradle.platform.PlatformGradleBuild;
import eu.xenit.alfred.initializr.generator.sdk.alfred.AlfredSdk.Dependencies;

public class AlfredSdkPlatformModuleGradleCustomizer implements BuildCustomizer<PlatformGradleBuild> {



    private final AlfrescoPlatformModule platformModule;

    public AlfredSdkPlatformModuleGradleCustomizer(AlfrescoPlatformModule platformModule) {
        this.platformModule = platformModule;
    }

    @Override
    public void customize(PlatformGradleBuild build) {

        build.addPlugin("eu.xenit.alfresco", "0.2.0");
        build.addPlugin("eu.xenit.amp", "0.2.0");

        build.dependencies().add("alfresco-repository", Dependencies.ALFRESCO_REPOSITORY);
    }

}
