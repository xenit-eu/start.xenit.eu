package eu.xenit.alfred.initializr.generator.sdk.alfred;

import eu.xenit.alfred.initializr.generator.alfresco.AlfrescoConstants;
import eu.xenit.alfred.initializr.generator.buildsystem.CustomScopeDependency;
import eu.xenit.alfred.initializr.generator.packaging.amp.AmpPackaging;
import io.spring.initializr.generator.buildsystem.DependencyScope;
import io.spring.initializr.generator.packaging.Packaging;
import io.spring.initializr.generator.packaging.jar.JarPackaging;
import io.spring.initializr.generator.version.VersionReference;

public class AlfredSdk {

    public static class Configurations {

        public static final String ALFRESCO_PROVIDED = "alfrescoProvided";
        public static final String BASE_ALFRESCO_WAR = "baseAlfrescoWar";

        public static final String ALFRESCO_AMP = "alfrescoAmp";
        public static final String ALFRESCO_SM = "alfrescoSM";
        public static final String ALFRESCO_DE = "alfrescoDE";

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


        public static final CustomScopeDependency ALFRESCO_WAR = new CustomScopeDependency(
                Configurations.BASE_ALFRESCO_WAR,
                AlfrescoConstants.GROUP_ID,
                "alfresco-enterprise",
                VersionReference.ofProperty("alfresco-version"),
                DependencyScope.PROVIDED_RUNTIME,
                "war");

        public static final CustomScopeDependency ALFRESCO_REPOSITORY = new CustomScopeDependency(
                Configurations.ALFRESCO_PROVIDED,
                AlfrescoConstants.GROUP_ID,
                "alfresco-repository",
                VersionReference.ofProperty("alfresco-version"),
                DependencyScope.PROVIDED_RUNTIME);
    }
}
