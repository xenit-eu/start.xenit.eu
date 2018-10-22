package eu.xenit.alfred.initializr.metadata;

import io.spring.initializr.test.metadata.InitializrMetadataTestBuilder;

public class AlfredSdkInitializrMetadataTestBuilder extends InitializrMetadataTestBuilder {

    public InitializrMetadataTestBuilder addDefaultTypes() {
        return this
                .addType("maven-build", false, "/pom.xml", "maven", "build")
                .addType("maven-project", false, "/starter.zip", "maven", "project")
                .addType("gradle-build", false, "/build.gradle", "gradle", "build")
                .addType("gradle-project", true, "/starter.zip", "gradle", "project");
    }

    public static InitializrMetadataTestBuilder withDefaults() {
        return (new AlfredSdkInitializrMetadataTestBuilder()).addAllDefaults();
    }

    public InitializrMetadataTestBuilder addDefaultPackagings() {
        return this.addPackaging("amp", true)
                   .addPackaging("war", false)
                   .addPackaging("jar", false);
    }
}
