/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package eu.xenit.alfred.initializr.integration;

//import io.spring.initializr.generator.spring.test.build.GradleBuildAssert;
//import io.spring.initializr.generator.spring.test.build.PomAssert;
import eu.xenit.alfred.initializr.app.StartApplication;
import eu.xenit.alfred.initializr.asserts.build.gradle.GradleMultiProjectAssert;
import eu.xenit.alfred.initializr.asserts.docker.DockerComposeGenerationAssert;
import eu.xenit.alfred.initializr.web.project.BuildGenerationResult;
import eu.xenit.alfred.initializr.web.project.CustomProjectGenerationInvoker;
import eu.xenit.alfred.initializr.web.project.DockerComposeGenerationResultSet;
import io.spring.initializr.generator.test.project.ProjectStructure;
import io.spring.initializr.generator.version.Version;
import io.spring.initializr.metadata.BillOfMaterials;
import io.spring.initializr.metadata.Dependency;
import io.spring.initializr.metadata.InitializrMetadataProvider;
import io.spring.initializr.web.project.ProjectGenerationResult;
import io.spring.initializr.web.project.ProjectRequest;
import io.spring.initializr.web.project.WebProjectRequest;
import java.util.Arrays;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Base test class for extensions.
 *
 * @author Stephane Nicoll
 */
@SpringBootTest(classes = StartApplication.class)
@RunWith(SpringRunner.class)
public abstract class BaseGeneratorTests {

    @Autowired
    private CustomProjectGenerationInvoker invoker;

    @Autowired
    private InitializrMetadataProvider metadataProvider;

    protected Dependency getDependency(String id) {
        return this.metadataProvider.get().getDependencies().get(id);
    }

    protected BillOfMaterials getBom(String id, String version) {
        BillOfMaterials bom = this.metadataProvider.get().getConfiguration().getEnv()
                .getBoms().get(id);
        return bom.resolve(Version.parse(version));
    }


    protected ProjectStructure generateProject(ProjectRequest request) {
        ProjectGenerationResult result = this.invoker
                .invokeProjectStructureGeneration(request);
        return new ProjectStructure(result.getRootDirectory());
    }

    protected GradleMultiProjectAssert generateGradleBuild(ProjectRequest request) {
        request.setType("gradle-build");
        BuildGenerationResult result = this.invoker.invokeProjectBuildGeneration(request);
        return new GradleMultiProjectAssert(result);
    }

//	protected PomAssert generateMavenPom(ProjectRequest request) {
//		request.setType("maven-build");
//		String content = new String(this.invoker.invokeBuildGeneration(request));
//		return new PomAssert(content);
//	}

    protected DockerComposeGenerationAssert generateCompose(ProjectRequest request) {
        DockerComposeGenerationResultSet result = this.invoker.invokeProjectComposeGeneration(request);
        return new DockerComposeGenerationAssert(result);
    }

    protected ProjectRequest createProjectRequest(String... styles) {
        WebProjectRequest request = new WebProjectRequest();
        request.initialize(this.metadataProvider.get());
        request.getStyle().addAll(Arrays.asList(styles));
        return request;
    }

}
