package eu.xenit.alfred.initializr.generator;

import eu.xenit.alfred.initializr.asserts.AlfredSdkProjectAssert;
import io.spring.initializr.generator.ProjectRequest;
import org.junit.Test;

import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertThat;

public class WebscriptsGeneratorTests extends AbstractProjectGeneratorTest {

    @Test
    public void vanillaWebscript() {
        ProjectRequest request = createProjectRequest("webscripts");

        assertThat(request.getStyle(), hasItem("webscripts"));

        AlfredSdkProjectAssert repoProject = generateProject(request)
                .subproject(String.format("%s-repo", request.getName()))
                .isGradleProject(false);

        repoProject.sourceCodeAssert("src/main/resources/alfresco/extension/templates/webscripts/com/example/demo/demo.get.desc.xml")
                .contains("<url>/demo</url>");

        repoProject.sourceCodeAssert("src/main/resources/alfresco/extension/templates/webscripts/com/example/demo/demo.get.json.ftl");

        repoProject.sourceCodeAssert("src/main/java/com/example/demo/DemoApplicationWebscript.java")
                .doesNotContain("SpringBootApplication")
                .hasImports(
                        "org.springframework.extensions.webscripts.DeclarativeWebScript",
                        "org.springframework.extensions.webscripts.WebScriptRequest")
                .contains("Map<String, Object> executeImpl(WebScriptRequest req, Status status, Cache cache)");


        repoProject.sourceCodeAssert("src/main/amp/config/alfresco/module/demo-repo/module-context.xml")
                .contains("bean id=\"webscript.com.example.demo.demo.get\"");
    }

    @Test
    public void noWebscripts() {
        ProjectRequest request = createProjectRequest();

        assertThat(request.getStyle(), not(hasItem("webscripts")));

        AlfredSdkProjectAssert repoProject = generateProject(request)
                .subproject(String.format("%s-repo", request.getName()));

        repoProject.hasNoFile("src/main/resources/alfresco/extension/templates/webscripts/com/example/demo/demo.get.desc.xml")
                .hasNoFile("src/main/resources/alfresco/extension/templates/webscripts/com/example/demo/demo.get.json.ftl")
                .hasNoFile("src/main/java/com/example/demo/DemoApplicationWebscript.java");

        repoProject.sourceCodeAssert("src/main/amp/config/alfresco/module/demo-repo/module-context.xml")
                .doesNotContain("bean id=\"webscript.com.example.demo.demo.get\"");
    }
}
