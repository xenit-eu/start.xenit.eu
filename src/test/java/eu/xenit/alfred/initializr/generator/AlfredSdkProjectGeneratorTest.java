package eu.xenit.alfred.initializr.generator;

import eu.xenit.alfred.initializr.asserts.AlfredSdkProjectAssert;
import eu.xenit.alfred.initializr.metadata.AlfredSdkInitializrMetadataTestBuilder;
import io.spring.initializr.generator.AbstractProjectGeneratorTests;
import io.spring.initializr.generator.ProjectRequest;
import io.spring.initializr.test.metadata.InitializrMetadataTestBuilder;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.io.File;

public class AlfredSdkProjectGeneratorTest extends AbstractProjectGeneratorTests {

    public AlfredSdkProjectGeneratorTest() {
        super(new AlfredSdkProjectGenerator());
    }

    @Test
    public void defaultProectRequest(){
        ProjectRequest request = createProjectRequest();

        Assertions.assertThat(request.getType()).isEqualToIgnoringCase("gradle-project");
        Assertions.assertThat(request.getPackaging()).isEqualToIgnoringCase("amp");
    }

    @Test
    public void defaultProjectWithGradle() {
        ProjectRequest request = createProjectRequest();
//        request.setType("gradle-build");

        AlfredSdkProjectAssert project = generateProject(request).isGradleProject();

        project.gradleBuildAssert()
                .hasAlfrescoVersion("5.2.4");

        project.gradleSettingsAssert()
                .hasProjectName(request.getName())
                .includesSubProject(String.format("%s-repo", request.getName()));

        verifyProjectSuccessfulEventFor(request);
    }

    @Test
    public void defaultRepoSubProject() {
        ProjectRequest request = createProjectRequest();
//        request.setType("gradle-build");

        AlfredSdkProjectAssert repoProject = generateProject(request)
                .subproject(String.format("%s-repo", request.getName()))
                .isGradleProject(false);

        repoProject.gradleBuildAssert()
                .contains("id \"eu.xenit.alfresco\" version \"0.1.3\"")
                .contains("id \"eu.xenit.amp\" version \"0.1.3\"")
                .contains("alfrescoProvided \"org.alfresco:alfresco-repository:${alfrescoVersion}\"");
    }

    @Override
    protected AlfredSdkProjectAssert generateProject(ProjectRequest request) {
        File dir = this.projectGenerator.generateProjectStructure(request);
        return new AlfredSdkProjectAssert(dir);
    }

    @Override
    protected InitializrMetadataTestBuilder initializeTestMetadataBuilder() {
        return AlfredSdkInitializrMetadataTestBuilder.withDefaults();
    }
}