//package eu.xenit.alfred.initializr.asserts;
//
//import io.spring.initializr.test.generator.GradleSettingsAssert;
//import org.assertj.core.api.Assertions;
//
//public class AlfredSdkGradleSettingsAssert extends GradleSettingsAssert {
//
//    public AlfredSdkGradleSettingsAssert(String content) {
//        super(content);
//    }
//
//    @Override
//    public AlfredSdkGradleSettingsAssert hasProjectName(String name) {
//        return this.contains(String.format("rootProject.name = '%s'", name));
//
//    }
//
//    public AlfredSdkGradleSettingsAssert includesSubProject(String name)
//    {
//        return this.contains(String.format("include '%s'", name));
//    }
//
//    @Override
//    public AlfredSdkGradleSettingsAssert contains(String expression) {
//        super.contains(expression);
//        return this;
//    }
//}
