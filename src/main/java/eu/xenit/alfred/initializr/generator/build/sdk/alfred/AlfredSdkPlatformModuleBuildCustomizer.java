package eu.xenit.alfred.initializr.generator.build.sdk.alfred;

import static org.springframework.util.StringUtils.quote;

import eu.xenit.alfred.initializr.generator.build.BuildCustomizer;
import eu.xenit.alfred.initializr.generator.build.alfresco.platform.AlfrescoPlatformModule;
import eu.xenit.alfred.initializr.generator.build.gradle.MultiProjectGradleBuild;
import io.spring.initializr.generator.buildsystem.MavenRepository;

public class AlfredSdkPlatformModuleBuildCustomizer implements BuildCustomizer<MultiProjectGradleBuild> {

    public static final String ALFRESCO_PUBLIC_URL = "https://artifacts.alfresco.com/nexus/content/groups/public/";
    public static final MavenRepository ALFRESCO_PUBLIC_REPO = new MavenRepository("alfrescoPublic", "Alfresco Public",
            ALFRESCO_PUBLIC_URL);

    private final AlfrescoPlatformModule platformModule;

    public AlfredSdkPlatformModuleBuildCustomizer(AlfrescoPlatformModule platformModule) {
        this.platformModule = platformModule;
    }

    @Override
    public void customize(MultiProjectGradleBuild build) {
        MultiProjectGradleBuild subproject = build.module(this.platformModule.getId());
        subproject.addPlugin("eu.xenit.alfresco", "0.2.0");
        subproject.addPlugin("eu.xenit.amp", "0.2.0");

        subproject.setDescription(this.platformModule.getDescription());

        subproject.ext("alfrescoVersion", quote(this.platformModule.getAlfrescoVersion().toString()));

        subproject.repositories().add(MavenRepository.MAVEN_CENTRAL);
        subproject.repositories().add(ALFRESCO_PUBLIC_REPO);
//        subproject.ext("alfrescoVersion", this.platformModule.getAlfrescoVersion().toString());
    }

}
