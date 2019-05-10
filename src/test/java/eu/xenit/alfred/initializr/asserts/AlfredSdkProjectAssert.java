//package eu.xenit.alfred.initializr.asserts;
//
//import io.spring.initializr.test.generator.GradleBuildAssert;
//import io.spring.initializr.test.generator.GradleSettingsAssert;
//import io.spring.initializr.test.generator.ProjectAssert;
//import org.assertj.core.api.Assertions;
//import org.springframework.core.io.FileSystemResource;
//import org.springframework.core.io.support.PropertiesLoaderUtils;
//import org.springframework.util.StreamUtils;
//import org.springframework.util.StringUtils;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.nio.charset.Charset;
//import java.util.Properties;
//
//public class AlfredSdkProjectAssert extends ProjectAssert {
//
//    public AlfredSdkProjectAssert(File dir) {
//        super(dir);
//    }
//
//    public AlfrescoModulePackageAssert ampAssert() {
//        return new AlfrescoModulePackageAssert(this);
//    }
//
//    @Override
//    public AlfredSdkGradleBuildAssert gradleBuildAssert() {
//        try {
//            return new AlfredSdkGradleBuildAssert(StreamUtils.copyToString(new FileInputStream(this.file("build.gradle")), Charset.forName("UTF-8")));
//        } catch (IOException ioException) {
//            throw new IllegalArgumentException("Cannot resolve build.gradle", ioException);
//        }
//    }
//
//    @Override
//    public AlfredSdkGradleSettingsAssert gradleSettingsAssert() {
//        try {
//            return new AlfredSdkGradleSettingsAssert(StreamUtils.copyToString(new FileInputStream(this.file("settings.gradle")), Charset.forName("UTF-8")));
//        } catch (IOException var2) {
//            throw new IllegalArgumentException("Cannot resolve settings.gradle", var2);
//        }
//    }
//
//    @Override
//    public AlfredSdkProjectAssert isGradleProject() {
//        return this.isGradleProject(true);
//    }
//
//    public AlfredSdkProjectAssert isGradleProject(boolean expectGradleWrapper)
//    {
//        return this.isGradleProject(null, expectGradleWrapper);
//    }
//
//
//    // Split check for build.gradle and gradle wrapper, because we want to
//    // re-use this to verify subprojects too!
//    public AlfredSdkProjectAssert isGradleProject(String version, boolean expectGradleWrapper) {
//        this.hasFile("build.gradle").hasNoFile("pom.xml");
//
//        if (expectGradleWrapper) {
//            this.hasFile("gradlew", "gradlew.bat", "gradle/wrapper/gradle-wrapper.properties", "gradle/wrapper/gradle-wrapper.jar");
//            if (StringUtils.hasText(version)) {
//                Properties properties = this.properties("gradle/wrapper/gradle-wrapper.properties");
//                String distributionUrl = properties.getProperty("distributionUrl");
//                Assertions.assertThat(distributionUrl).contains(new CharSequence[]{version});
//            }
//        } else {
//            this.hasNoFile("gradlew", "gradlew.bat");
//        }
//
//        return this;
//    }
//
//    public AlfredSdkProjectAssert subproject(String name)
//    {
//        return new AlfredSdkProjectAssert(this.file(name));
//    }
//
//    private File file(String localPath) {
//        return new File(this.getDir(), localPath);
//    }
//
//    private Properties properties(String localPath) {
//        File f = this.file(localPath);
//
//        try {
//            return PropertiesLoaderUtils.loadProperties(new FileSystemResource(f));
//        } catch (Exception var4) {
//            throw new IllegalStateException("Cannot load Properties", var4);
//        }
//    }
//
////    public AlfredSdkProjectAssert(ProjectAssert module) {
////
////        this.module = module;
////    }
//}
