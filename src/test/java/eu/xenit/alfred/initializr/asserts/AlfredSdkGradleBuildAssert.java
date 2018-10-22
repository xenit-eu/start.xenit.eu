package eu.xenit.alfred.initializr.asserts;

import io.spring.initializr.test.generator.GradleBuildAssert;

public class AlfredSdkGradleBuildAssert extends GradleBuildAssert {
    public AlfredSdkGradleBuildAssert(String content) {
        super(content);
    }

    public GradleBuildAssert hasAlfrescoVersion(String alfrescoVersion) {
        return this.contains(String.format("alfrescoVersion = '%s'",alfrescoVersion));
    }
}
