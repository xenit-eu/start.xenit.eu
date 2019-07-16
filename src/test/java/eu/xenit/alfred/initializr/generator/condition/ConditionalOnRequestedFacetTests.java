package eu.xenit.alfred.initializr.generator.condition;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import io.spring.initializr.generator.project.ProjectDescription;
import io.spring.initializr.generator.test.project.ProjectAssetTester;
import io.spring.initializr.metadata.Dependency;
import io.spring.initializr.metadata.DependencyGroup;
import io.spring.initializr.metadata.InitializrMetadata;
import java.util.Collections;
import org.junit.Test;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Tests for {@link ConditionalOnRequestedFacetTests}.
 */
public class ConditionalOnRequestedFacetTests {

    private final ProjectAssetTester projectTester = new ProjectAssetTester()
            .withConfiguration(RequestedFacetTestConfiguration.class);

    @Configuration
    static class RequestedFacetTestConfiguration {

        @Bean
        public InitializrMetadata metadata() {
            // Add a dependency 'foo' with facet 'web'
            Dependency dependency = Dependency.withId("foo");
            dependency.setFacets(Collections.singletonList("web"));

            DependencyGroup dependencyGroup = DependencyGroup.create("test");
            dependencyGroup.getContent().add(dependency);

            InitializrMetadata initializrMetadata = new InitializrMetadata();
            initializrMetadata.getDependencies().getContent().add(dependencyGroup);

            initializrMetadata.validate();

            return initializrMetadata;
        }

        @Bean
        @ConditionalOnRequestedFacet("web")
        public String webFacetActive() {
            return "webFacetDependency";
        }
    }

    @Test
    public void outcomeWithMatchingFacet() {
        ProjectDescription projectDescription = new ProjectDescription();
        projectDescription.addDependency("foo", mock(io.spring.initializr.generator.buildsystem.Dependency.class));

        String bean = this.projectTester.generate(projectDescription, (projectGenerationContext) -> {
            assertThat(projectGenerationContext.getBeansOfType(String.class)).hasSize(1);
            return projectGenerationContext.getBean(String.class);
        });

        assertThat(bean).isEqualTo("webFacetDependency");
    }

    @Test
    public void outcomeWithNoMatch() {
        ProjectDescription projectDescription = new ProjectDescription();
        projectDescription.addDependency("another", mock(io.spring.initializr.generator.buildsystem.Dependency.class));

        this.projectTester.generate(projectDescription, (projectGenerationContext) -> {
            assertThat(projectGenerationContext.getBeansOfType(String.class)).isEmpty();
            return null;
        });
    }


}



