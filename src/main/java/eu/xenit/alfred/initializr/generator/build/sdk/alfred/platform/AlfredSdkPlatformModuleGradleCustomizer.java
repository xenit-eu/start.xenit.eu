package eu.xenit.alfred.initializr.generator.build.sdk.alfred.platform;

import static org.springframework.util.StringUtils.quote;

import eu.xenit.alfred.initializr.generator.build.BuildCustomizer;
import eu.xenit.alfred.initializr.generator.build.alfresco.platform.AlfrescoPlatformModule;
import eu.xenit.alfred.initializr.generator.build.gradle.MultiProjectGradleBuild;
import eu.xenit.alfred.initializr.generator.buildsystem.ProjectDependency;
import io.spring.initializr.generator.buildsystem.MavenRepository;
import io.spring.initializr.generator.buildsystem.gradle.GradleBuild;
import io.spring.initializr.generator.project.ResolvedProjectDescription;
import org.springframework.context.annotation.Bean;

public class AlfredSdkPlatformModuleGradleCustomizer implements BuildCustomizer<MultiProjectGradleBuild> {

    public static final String ALFRESCO_PUBLIC_URL = "https://artifacts.alfresco.com/nexus/content/groups/public/";
    public static final MavenRepository ALFRESCO_PUBLIC_REPO = new MavenRepository("alfrescoPublic", "Alfresco Public",
            ALFRESCO_PUBLIC_URL);

    private final AlfrescoPlatformModule platformModule;
    private final ResolvedProjectDescription projectDescription;

    public AlfredSdkPlatformModuleGradleCustomizer(AlfrescoPlatformModule platformModule, ResolvedProjectDescription projectDescription) {
        this.platformModule = platformModule;
        this.projectDescription = projectDescription;
    }

    @Override
    public void customize(MultiProjectGradleBuild build) {

        MultiProjectGradleBuild subproject = build.module(this.platformModule.getId());
        subproject.setArtifact(this.platformModule.getId());
        subproject.setGroup(this.projectDescription.getGroupId());

        subproject.addPlugin("eu.xenit.alfresco", "0.2.0");
        subproject.addPlugin("eu.xenit.amp", "0.2.0");

        subproject.setDescription(this.platformModule.getDescription());

        subproject.ext("alfrescoVersion", quote(this.platformModule.getAlfrescoVersion().toString()));

        subproject.repositories().add(MavenRepository.MAVEN_CENTRAL);
        subproject.repositories().add(ALFRESCO_PUBLIC_REPO);

        build.dependencies()
                .add("alfrescoAmp", new ProjectDependency(subproject, "amp"));

    }

}
