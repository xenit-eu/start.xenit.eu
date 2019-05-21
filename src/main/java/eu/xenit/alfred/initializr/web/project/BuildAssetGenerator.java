package eu.xenit.alfred.initializr.web.project;

import eu.xenit.alfred.initializr.generator.buildsystem.BuildAssetWriter;
import io.spring.initializr.generator.buildsystem.BuildWriter;
import io.spring.initializr.generator.project.ProjectAssetGenerator;
import io.spring.initializr.generator.project.ProjectGenerationContext;
import io.spring.initializr.generator.project.ResolvedProjectDescription;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.ObjectProvider;

public class BuildAssetGenerator implements ProjectAssetGenerator<Map<Path, String>> {

    @Override
    public Map<Path, String> generate(ProjectGenerationContext context) throws IOException {
        ObjectProvider<BuildAssetWriter> buildAssetWriters = context.getBeanProvider(BuildAssetWriter.class);

        return buildAssetWriters.stream().collect(Collectors.toMap(
                BuildAssetWriter::relativePath,
                this::writeBuildAsset));

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
