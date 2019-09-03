package eu.xenit.alfred.initializr.start.alfresco;

import io.spring.initializr.generator.buildsystem.MavenRepository;

public class AlfrescoConstants {

    public static class MavenRepositories {
        public static final MavenRepository ALFRESCO_PUBLIC = new MavenRepository(
                "alfresco-public",
                "Alfresco Public",
                "https://artifacts.alfresco.com/nexus/content/groups/public/");
    }

    public static class ArtifactId {
        public static final String ALFRESCO_ENTERPRISE = "alfresco-enterprise";
        public static final String ALFRESCO_REPOSITORY = "alfresco-repository";

    }

    public static class GroupId {
        public static final String ORG_ALFRESCO = "org.alfresco";
    }


}
