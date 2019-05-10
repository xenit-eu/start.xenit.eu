package eu.xenit.alfred.initializr.generator.buildsystem;

import io.spring.initializr.generator.buildsystem.BomContainer;
import io.spring.initializr.generator.buildsystem.DependencyContainer;
import io.spring.initializr.generator.buildsystem.MavenRepositoryContainer;
import io.spring.initializr.generator.version.VersionProperty;
import java.util.Map;

public interface IBuild {

    String getGroup();

    void setGroup(String group);

    String getArtifact();

    void setArtifact(String artifact);

    String getVersion();

    void setVersion(String version);

    void addVersionProperty(VersionProperty versionProperty, String version);

    void addExternalVersionProperty(String propertyName, String version);

    void addInternalVersionProperty(String propertyName, String version);

    Map<VersionProperty, String> getVersionProperties();

    DependencyContainer dependencies();

    BomContainer boms();

    MavenRepositoryContainer repositories();

    MavenRepositoryContainer pluginRepositories();
}
