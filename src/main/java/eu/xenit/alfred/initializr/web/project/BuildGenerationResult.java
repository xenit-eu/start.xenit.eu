package eu.xenit.alfred.initializr.web.project;

import io.spring.initializr.generator.project.ProjectDescription;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import lombok.Getter;

public class BuildGenerationResult {

    @Getter
    private final ProjectDescription projectDescription;

    private final Map<Path, String> build;

    BuildGenerationResult(ProjectDescription projectDescription, Map<Path, String> build) {
        this.projectDescription = projectDescription;
        this.build = build;
    }

    public String getBuildContent(Path path) {
        return build.get(path);
    }

    public String getBuildContent(String path) {
        return this.getBuildContent(Paths.get(path));
    }



}
