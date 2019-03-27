//package eu.xenit.alfred.initializr.generator;
//
//import eu.xenit.alfred.initializr.asserts.AlfredSdkProjectAssert;
//import eu.xenit.alfred.initializr.metadata.AlfredInitializrMetadataBuilder;
//import io.spring.initializr.generator.*;
//import io.spring.initializr.metadata.InitializrMetadata;
//import io.spring.initializr.metadata.InitializrMetadataProvider;
//import io.spring.initializr.metadata.SimpleInitializrMetadataProvider;
//import org.mockito.ArgumentMatcher;
//import org.mockito.ArgumentMatchers;
//import org.mockito.Mockito;
//import org.springframework.context.ApplicationEventPublisher;
//
//import java.io.File;
//import java.util.ArrayList;
//import java.util.Arrays;
//
//public abstract class AbstractProjectGeneratorTest {
//
//    private final ProjectGenerator projectGenerator;
//    private final ApplicationEventPublisher eventPublisher;
//
//    protected AbstractProjectGeneratorTest() {
//        this(new AlfredSdkProjectGenerator(defaultMetadataProvider()));
//    }
//
//    protected AbstractProjectGeneratorTest(ProjectGenerator projectGenerator) {
//        this.projectGenerator = projectGenerator;
//
//        this.eventPublisher = Mockito.mock(ApplicationEventPublisher.class);
//        this.projectGenerator.setEventPublisher(this.eventPublisher);
//        this.projectGenerator.setRequestResolver(new ProjectRequestResolver(new ArrayList<>()));
//    }
//
//    protected ProjectRequest createProjectRequest(String... styles) {
//        ProjectRequest request = new ProjectRequest();
//        request.initialize(this.projectGenerator.getMetadataProvider().get());
//        request.getStyle().addAll(Arrays.asList(styles));
//        return request;
//    }
//
//    protected AlfredSdkProjectAssert generateProject(ProjectRequest request) {
//        File dir = this.projectGenerator.generateProjectStructure(request);
//        return new AlfredSdkProjectAssert(dir);
//    }
//
//    protected void verifyProjectSuccessfulEventFor(ProjectRequest request) {
//        Mockito.verify(this.eventPublisher, Mockito.times(1)).publishEvent(ArgumentMatchers.argThat(new AbstractProjectGeneratorTest.ProjectGeneratedEventMatcher(request)));
//    }
//
//    protected static class ProjectGeneratedEventMatcher implements ArgumentMatcher<ProjectGeneratedEvent> {
//        private final ProjectRequest request;
//
//        ProjectGeneratedEventMatcher(ProjectRequest request) {
//            this.request = request;
//        }
//
//        public boolean matches(ProjectGeneratedEvent event) {
//            return this.request.equals(event.getProjectRequest());
//        }
//    }
//
//    private static InitializrMetadataProvider defaultMetadataProvider() {
//        InitializrMetadata metadata = AlfredInitializrMetadataBuilder.withDefaults().build();
//        return new SimpleInitializrMetadataProvider(metadata);
//    }
//}
