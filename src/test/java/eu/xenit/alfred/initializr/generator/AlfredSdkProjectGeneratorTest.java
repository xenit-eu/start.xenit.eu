//package eu.xenit.alfred.initializr.generator;
//
//import eu.xenit.alfred.initializr.asserts.AlfredSdkProjectAssert;
//import eu.xenit.alfred.initializr.metadata.AlfredInitializrMetadataBuilder;
//import io.spring.initializr.generator.ProjectRequest;
//import io.spring.initializr.metadata.InitializrMetadata;
//import io.spring.initializr.metadata.InitializrMetadataProvider;
//import io.spring.initializr.metadata.SimpleInitializrMetadataProvider;
//import org.assertj.core.api.Assertions;
////import org.junit.jupiter.api.Test;
//import org.junit.Test;
//
//import java.io.File;
//
//import static org.junit.Assert.assertTrue;
//
////public class AlfredSdkProjectGeneratorTest extends AbstractProjectGeneratorTests {
//public class AlfredSdkProjectGeneratorTest extends AbstractProjectGeneratorTest {
//
//    @Test
//    public void defaultProjectRequest(){
//        ProjectRequest request = createProjectRequest();
//
//        Assertions.assertThat(request.getType()).isEqualToIgnoringCase("gradle-module");
//        Assertions.assertThat(request.getPackaging()).isEqualToIgnoringCase("amp");
//    }
//
//    @Test
//    public void defaultProjectWithGradle() {
//        ProjectRequest request = createProjectRequest();
//
//        AlfredSdkProjectAssert module = generateProject(request).isGradleProject();
//
//        module.gradleBuildAssert()
//                .hasAlfrescoVersion("5.2.4");
//
//        module.gradleSettingsAssert()
//                .hasProjectName(request.getId())
//                .includesSubProject(String.format("%s-repo", request.getId()));
//
//        verifyProjectSuccessfulEventFor(request);
//    }
//
//    @Test
//    public void defaultRepoSubProject() {
//        ProjectRequest request = createProjectRequest();
//
////        request.setType("gradle-build");
//
//        AlfredSdkProjectAssert repoProject = generateProject(request)
//                .subproject(String.format("%s-repo", request.getId()))
//                .isGradleProject(false);
//
//        repoProject.gradleBuildAssert()
//                .contains("id \"eu.xenit.alfresco\" version \"0.1.5\"")
//                .contains("id \"eu.xenit.amp\" version \"0.1.5\"")
//                .contains("alfrescoProvided \"org.alfresco:alfresco-repository:${alfrescoVersion}\"");
//
//        repoProject.ampAssert()
////                .hasFile("src/main/amp/module.properties")
//                .hasModulePropertiesFile()
//                .hasModuleContextFile();
//
//
//    }
//
//
//
////    @Override
////    protected static InitializrMetadataTestBuilder initializeTestMetadataBuilder() {
////        return;
////    }
//
//
//}