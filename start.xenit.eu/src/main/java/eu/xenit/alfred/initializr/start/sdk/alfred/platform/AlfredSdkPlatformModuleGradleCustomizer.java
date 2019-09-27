package eu.xenit.alfred.initializr.start.sdk.alfred.platform;

import eu.xenit.alfred.initializr.start.build.BuildCustomizer;
import eu.xenit.alfred.initializr.start.build.platform.gradle.PlatformGradleBuild;
import eu.xenit.alfred.initializr.start.project.alfresco.artifacts.AlfrescoVersionArtifactSelector;
import eu.xenit.alfred.initializr.start.project.alfresco.platform.AlfrescoPlatformModule;
import eu.xenit.alfred.initializr.start.sdk.alfred.AlfredSdk.Dependencies;

public class AlfredSdkPlatformModuleGradleCustomizer implements BuildCustomizer<PlatformGradleBuild> {

    private final AlfrescoPlatformModule platformModule;
    private AlfrescoVersionArtifactSelector artifactSelector;

    public AlfredSdkPlatformModuleGradleCustomizer(AlfrescoPlatformModule platformModule,
            AlfrescoVersionArtifactSelector artifactSelector) {
        this.platformModule = platformModule;
        this.artifactSelector = artifactSelector;
    }

    @Override
    public void customize(PlatformGradleBuild build) {

        build.plugins().add("eu.xenit.alfresco", plugin -> plugin.setVersion("0.2.0"));
        build.plugins().add("eu.xenit.amp", plugin -> plugin.setVersion("0.2.0"));

        build.dependencies().add("alfresco-repository",
                Dependencies.getAlfrescoRepositoryDependency(artifactSelector.getAlfrescoArtifactId()));
    }

}
