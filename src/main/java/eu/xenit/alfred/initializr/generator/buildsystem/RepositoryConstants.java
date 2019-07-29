package eu.xenit.alfred.initializr.generator.buildsystem;

import io.spring.initializr.generator.buildsystem.MavenRepository;

public class RepositoryConstants {

    public static class MavenRepositories {
        public static final MavenRepository SONATYPE_SNAPSHOT = new MavenRepository(
                "sonatype-snaphost",
                "Sonatype Snapshot Repository",
                "https://oss.sonatype.org/content/repositories/snapshots/",
                true);
        public static final MavenRepository SONATYPE_RELEASE = new MavenRepository(
                "sonatype-release",
                "Sonatype Release Repository",
                "https://oss.sonatype.org/service/local/staging/deploy/maven2/");
        public static final MavenRepository JCENTER_RELEASE = new MavenRepository(
                "jcenter-release",
                "JCenter Release Repository",
                "https://jcenter.bintray.com/");

    }

}
