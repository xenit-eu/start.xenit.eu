package eu.xenit.alfred.initializr.web.project;

import eu.xenit.alfred.initializr.generator.buildsystem.BuildAssetWriter;
import io.spring.initializr.generator.project.ProjectAssetGenerator;
import io.spring.initializr.generator.project.ProjectDescription;
import io.spring.initializr.generator.project.ProjectGenerationContext;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Path;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.ObjectProvider;

public class BuildAssetGenerator implements ProjectAssetGenerator<BuildGenerationResult> {

    @Override
    public BuildGenerationResult generate(ProjectGenerationContext context) {
        ObjectProvider<BuildAssetWriter> buildAssetWriters = context.getBeanProvider(BuildAssetWriter.class);

        Map<Path, String> buildFiles = buildAssetWriters.stream().collect(Collectors.toMap(
                BuildAssetWriter::getRelativePath,
                this::writeBuildAsset));

        return new BuildGenerationResult(context.getBean(ProjectDescription.class), buildFiles);

    }

    private String writeBuildAsset(BuildAssetWriter buildAssetWriter) {
        try {
            StringWriter out = new StringWriter();
            buildAssetWriter.writeBuild(out);
            return out.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
