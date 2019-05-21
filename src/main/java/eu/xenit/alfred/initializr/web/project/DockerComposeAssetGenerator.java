package eu.xenit.alfred.initializr.web.project;

import eu.xenit.alfred.initializr.generator.docker.compose.DockerComposeWriter;
import io.spring.initializr.generator.project.ProjectAssetGenerator;
import io.spring.initializr.generator.project.ProjectGenerationContext;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.ObjectProvider;

public class DockerComposeAssetGenerator implements ProjectAssetGenerator<List<DockerComposeYml>> {

    @Override
    public List<DockerComposeYml> generate(ProjectGenerationContext context) throws IOException {
        ObjectProvider<DockerComposeWriter> composeWriters = context.getBeanProvider(DockerComposeWriter.class);

        return composeWriters.orderedStream()
                .map(writer -> new DockerComposeYml(writer.composeFile(), this.writeDockerComposeContent(writer)))
                .collect(Collectors.toList());
    }

    private String writeDockerComposeContent(DockerComposeWriter composeWriter) {
        try {
            StringWriter out = new StringWriter();
            composeWriter.writeCompose(out);
            return out.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
