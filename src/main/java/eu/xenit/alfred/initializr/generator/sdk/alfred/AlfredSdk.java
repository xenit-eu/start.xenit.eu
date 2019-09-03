package eu.xenit.alfred.initializr.generator.sdk.alfred;

import eu.xenit.alfred.initializr.generator.alfresco.AlfrescoConstants;
import eu.xenit.alfred.initializr.generator.alfresco.AlfrescoConstants.ArtifactId;
import eu.xenit.alfred.initializr.generator.alfresco.AlfrescoConstants.GroupId;
import eu.xenit.alfred.initializr.generator.packaging.amp.AmpPackaging;
import io.spring.initializr.generator.buildsystem.Dependency;
import io.spring.initializr.generator.buildsystem.DependencyScope;
import io.spring.initializr.generator.buildsystem.gradle.GradleDependency;
import io.spring.initializr.generator.packaging.Packaging;
import io.spring.initializr.generator.packaging.jar.JarPackaging;
import io.spring.initializr.generator.version.VersionReference;

public class AlfredSdk {

    public static class Configurations {

        public static final String ALFRESCO_PROVIDED = "alfrescoProvided";
        public static final String BASE_ALFRESCO_WAR = "baseAlfrescoWar";

        public static final String SHARE_PROVIDED = "shareProvided";
        public static final String BASE_SHARE_WAR = "baseShareWar";

        public static final String ALFRESCO_AMP = "alfrescoAmp";
        public static final String ALFRESCO_SM = "alfrescoSM";
        public static final String ALFRESCO_DE = "alfrescoDE";

        public static final String SHARE_AMP = "shareAmp";
        public static final String SHARE_SM = "shareSM";
        public static final String SHARE_DE = "shareDE";

        public static String configurationForPackaging(Packaging packaging) {
            switch (packaging.id()) {
                case AmpPackaging.ID:
                    return ALFRESCO_AMP;
                case JarPackaging.ID:
                    return ALFRESCO_SM;
                default:
                    throw new IllegalStateException(
                            "Unrecognized packaging type '" + packaging.id() + "'");
            }
        }
    }


    public static class Dependencies {


        public static final Dependency ALFRESCO_WAR = GradleDependency.withCoordinates(
                GroupId.ORG_ALFRESCO,
                ArtifactId.ALFRESCO_ENTERPRISE)
                .version(VersionReference.ofProperty("alfresco-version"))
                .scope(DependencyScope.COMPILE)
                .configuration("baseAlfrescoWar")
                .type("war")
                .build();

        public static final Dependency ALFRESCO_REPOSITORY = GradleDependency.withCoordinates(
                GroupId.ORG_ALFRESCO,
                ArtifactId.ALFRESCO_REPOSITORY)
                .version(VersionReference.ofProperty("alfresco-version")) // this is not correct ?
                .scope(DependencyScope.PROVIDED_RUNTIME)
                .configuration("alfrescoProvided")
                .build();
    }
}
