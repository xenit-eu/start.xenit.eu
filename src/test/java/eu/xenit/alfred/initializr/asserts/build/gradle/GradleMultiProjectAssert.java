package eu.xenit.alfred.initializr.asserts.build.gradle;

import eu.xenit.alfred.initializr.web.project.BuildGenerationResult;
import io.micrometer.core.instrument.util.StringUtils;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GradleMultiProjectAssert {

    private final BuildGenerationResult buildGenerationResult;

    public GradleMultiProjectAssert(BuildGenerationResult buildGenerationResult) {
        this.buildGenerationResult = buildGenerationResult;
    }

    public GradleBuildAssert rootGradleBuild() {
        return new GradleBuildAssert(buildGenerationResult.getBuildContent("build.gradle"));
    }

    public GradleBuildAssert projectGradleBuild(String project) {
        Path path = Paths.get(project, "build.gradle");
        String gradleBuild = buildGenerationResult.getBuildContent(path);
        if (StringUtils.isBlank(gradleBuild)) {
            throw new IllegalArgumentException(
                    String.format("argument project %s invalid: %s not found", project, path.toString()));
        }

        return new GradleBuildAssert(gradleBuild);
    }

    public GradleBuildAssert platformGradleBuild() {
        return this.projectGradleBuild(this.buildGenerationResult.getProjectDescription().getName() + "-platform");
    }

}
