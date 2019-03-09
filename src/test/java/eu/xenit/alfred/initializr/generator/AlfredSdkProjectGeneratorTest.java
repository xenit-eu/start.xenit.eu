package eu.xenit.alfred.initializr.generator;

import eu.xenit.alfred.initializr.asserts.AlfredSdkProjectAssert;
import eu.xenit.alfred.initializr.metadata.AlfredInitializrMetadataBuilder;
import io.spring.initializr.generator.ProjectRequest;
import io.spring.initializr.metadata.InitializrMetadata;
import io.spring.initializr.metadata.InitializrMetadataProvider;
import io.spring.initializr.metadata.SimpleInitializrMetadataProvider;
import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Test;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertTrue;

//public class AlfredSdkProjectGeneratorTest extends AbstractProjectGeneratorTests {
public class AlfredSdkProjectGeneratorTest extends AbstractProjectGeneratorTest {

    @Test
    public void defaultProjectRequest(){
        ProjectRequest request = createProjectRequest();

        Assertions.assertThat(request.getType()).isEqualToIgnoringCase("gradle-project");
        Assertions.assertThat(request.getPackaging()).isEqualToIgnoringCase("amp");
    }

    @Test
    public void defaultProjectWithGradle() {
        ProjectRequest request = createProjectRequest();

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

        repoProject.ampAssert()
//                .hasFile("src/main/amp/module.properties")
                .hasModulePropertiesFile()
                .hasModuleContextFile();


    }



//    @Override
//    protected static InitializrMetadataTestBuilder initializeTestMetadataBuilder() {
//        return;
//    }


}