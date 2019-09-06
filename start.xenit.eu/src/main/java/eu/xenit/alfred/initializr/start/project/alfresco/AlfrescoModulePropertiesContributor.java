package eu.xenit.alfred.initializr.start.project.alfresco;

import io.spring.initializr.generator.io.IndentingWriter;
import io.spring.initializr.generator.io.IndentingWriterFactory;
import io.spring.initializr.generator.project.contributor.ProjectContributor;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

public class AlfrescoModulePropertiesContributor implements ProjectContributor {

    private final AlfrescoModuleProjectLayout layout;
    private final AlfrescoModuleProperties properties;
    private final IndentingWriterFactory writerFactory;
    private final AlfrescoModulePropertiesWriter propertiesWriter;

    public AlfrescoModulePropertiesContributor(
            AlfrescoModuleProjectLayout projectLayout,
            AlfrescoModuleProperties properties,
            IndentingWriterFactory writerFactory,
            AlfrescoModulePropertiesWriter propertiesWriter) {
        this.layout = projectLayout;
        this.properties = properties;
        this.writerFactory = writerFactory;
        this.propertiesWriter = propertiesWriter;
    }

    @Override
    public void contribute(Path projectRoot) throws IOException {

        Path moduleRoot = projectRoot.resolve(this.layout.getModuleRoot());
        Path moduleProperties = moduleRoot.resolve(this.layout.getModulePropertiesPath());

        writeBuild(Files.newBufferedWriter(moduleProperties));

    }

    public void writeBuild(Writer out) throws IOException {
        try (IndentingWriter writer = this.writerFactory.createIndentingWriter("properties", out)) {
            this.propertiesWriter.writeTo(writer, this.properties);
        }
    }
}
