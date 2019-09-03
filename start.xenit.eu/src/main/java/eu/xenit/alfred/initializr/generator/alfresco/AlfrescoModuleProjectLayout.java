package eu.xenit.alfred.initializr.generator.alfresco;

import eu.xenit.alfred.initializr.generator.alfresco.platform.AlfrescoPlatformModule;
import java.nio.file.Path;
import java.nio.file.Paths;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AlfrescoModuleProjectLayout {

    private final AlfrescoPlatformModule module;

    private Path moduleRoot = null;
    public Path getModuleRoot() {
        if (moduleRoot != null)
        {
            return this.moduleRoot;
        }

        return Paths.get(this.module.getId());
    }
    public void setModuleRoot(Path path) {
        this.moduleRoot = path;
    }

    private Path modulePropertiesPath;
    public Path getModulePropertiesPath() {
        if (this.modulePropertiesPath != null) {
            return this.modulePropertiesPath;
        }

        return this.getSourceConfigDir().resolve("module.properties");
    }
    public void setModulePropertiesPath(Path path) {
        this.modulePropertiesPath = path;
    }

    @Getter
    private Path sourceConfigDir = Paths.get("src/main/config");
    public void setSourceConfigDir(Path path) {
        if (path == null) {
            sourceConfigDir = Paths.get("src/main/config");
        } else {
            sourceConfigDir = path;
        }
    }
    public void setSourceConfigDir(String path) {
        if (path == null) {
            this.sourceConfigDir = null;
        } else {
            this.setSourceConfigDir(Paths.get(path));
        }
    }

    private Path sourceConfigModuleDir = null;
    public Path getSourceConfigModuleDir() {
        if (this.sourceConfigModuleDir != null) {
            return this.sourceConfigModuleDir;
        }

        return this.getSourceConfigDir()
                .resolve("alfresco/module/")
                .resolve(this.module.getId());
    }

    public void setSourceConfigModuleDir(Path path) {
        this.sourceConfigModuleDir = path;
    }
    public void setSourceConfigModuleDir(String path) {
        if (path == null) {
            this.sourceConfigModuleDir = null;
        } else {
            this.setSourceConfigModuleDir(Paths.get(path));
        }
    }
}
