//package eu.xenit.alfred.initializr.metadata;
//
//import io.spring.initializr.test.metadata.InitializrMetadataTestBuilder;
//
//public class AlfredSdkInitializrMetadataTestBuilder extends InitializrMetadataTestBuilder {
//
//    public InitializrMetadataTestBuilder addDefaultTypes() {
//        return this
//                .addType("maven-build", false, "/pom.xml", "maven", "build")
//                .addType("maven-module", false, "/starter.zip", "maven", "module")
//                .addType("gradle-build", false, "/build.gradle", "gradle", "build")
//                .addType("gradle-module", true, "/starter.zip", "gradle", "module");
//    }
//
//    public static InitializrMetadataTestBuilder withDefaults() {
//        return (new AlfredSdkInitializrMetadataTestBuilder()).addAllDefaults();
//    }
//
//    public InitializrMetadataTestBuilder addDefaultPackagings() {
//        return this.addPackaging("amp", true)
//                   .addPackaging("war", false)
//                   .addPackaging("jar", false);
//    }
//}
