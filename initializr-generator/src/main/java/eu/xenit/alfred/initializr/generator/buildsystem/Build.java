package eu.xenit.alfred.initializr.generator.buildsystem;

import io.spring.initializr.generator.buildsystem.BomContainer;
import io.spring.initializr.generator.buildsystem.BuildSettings;
import io.spring.initializr.generator.buildsystem.DependencyContainer;
import io.spring.initializr.generator.buildsystem.MavenRepositoryContainer;
import io.spring.initializr.generator.buildsystem.PropertyContainer;
import io.spring.initializr.generator.version.VersionProperty;
import java.util.Map;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

public interface Build {

    BuildSettings.Builder<?> settings();

    DependencyContainer dependencies();

    BomContainer boms();

    MavenRepositoryContainer repositories();

    MavenRepositoryContainer pluginRepositories();

    PropertyContainer properties();

    String getDescription();
    void setDescription(String description);

    String getName();
    void setName(@NonNull String name);
}
