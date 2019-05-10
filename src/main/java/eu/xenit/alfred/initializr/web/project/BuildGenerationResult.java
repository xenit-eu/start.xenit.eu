package eu.xenit.alfred.initializr.web.project;

import io.spring.initializr.generator.project.ResolvedProjectDescription;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import lombok.Getter;

public class BuildGenerationResult {

    @Getter
    private final ResolvedProjectDescription projectDescription;

    private final Map<Path, String> build;

    BuildGenerationResult(ResolvedProjectDescription projectDescription, Map<Path, String> build) {
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
