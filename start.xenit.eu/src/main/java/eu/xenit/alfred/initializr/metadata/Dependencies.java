package eu.xenit.alfred.initializr.metadata;

import io.spring.initializr.metadata.Dependency;

import java.util.Collections;
import java.util.List;

import static io.spring.initializr.metadata.Dependency.SCOPE_COMPILE;

public class Dependencies {

    public static Dependency DynamicExtensions = Dependencies.create(
            "Dynamic Extensions",
            "eu.xenit",
            "alfresco-dynamic-extensions-repo",
            "1.7.6.RELEASE",
            "Rapid development of Alfresco repository extensions",
            Collections.singletonList("de"));

    public static Dependency Webscripts = Dependencies.create(
        "Webscripts Example",
            "webscripts",
            "Build REST APIs with Alfresco Content Services",
            Collections.singletonList("webscripts"));


    private static Dependency create(String name, String groupId, String artifactId, String version, String description, List<String> facets) {
        Dependency dependency = new Dependency();
        dependency.setName(name);
        dependency.setGroupId(groupId);
        dependency.setArtifactId(artifactId);
        dependency.setVersion(version);
        dependency.setDescription(description);
        dependency.setScope(SCOPE_COMPILE);

        dependency.setFacets(facets);

        return dependency;
    }

    private static Dependency create(String name, String id, String description, List<String> facets) {
        Dependency dependency = new Dependency();
        dependency.setName(name);
        dependency.setId(id);
        dependency.setDescription(description);

        dependency.setFacets(facets);

        return dependency;
    }
}
