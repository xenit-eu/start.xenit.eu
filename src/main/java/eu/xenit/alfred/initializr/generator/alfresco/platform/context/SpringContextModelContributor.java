package eu.xenit.alfred.initializr.generator.alfresco.platform.context;

import eu.xenit.alfred.initializr.generator.alfresco.AlfrescoModuleProjectLayout;
import eu.xenit.alfred.initializr.generator.alfresco.platform.context.SpringContextModel.SpringXmlConfigFile;
import eu.xenit.alfred.initializr.generator.beans.config.SpringXmlConfig;
import eu.xenit.alfred.initializr.generator.beans.config.SpringXmlConfigWriter;
import io.spring.initializr.generator.io.IndentingWriter;
import io.spring.initializr.generator.io.IndentingWriterFactory;
import io.spring.initializr.generator.project.contributor.ProjectContributor;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class SpringContextModelContributor implements ProjectContributor {

    private final SpringContextModel contextModel;
    private final IndentingWriterFactory writerFactory;
    private final SpringXmlConfigWriter contextWriter;
    private final AlfrescoModuleProjectLayout layout;

    public SpringContextModelContributor(SpringContextModel contextModel, IndentingWriterFactory writerFactory,
            AlfrescoModuleProjectLayout layout) {

        this.contextModel = contextModel;
        this.writerFactory = writerFactory;
        this.layout = layout;
        this.contextWriter = new SpringXmlConfigWriter();
    }

    @Override
    public void contribute(Path projectRoot) throws IOException {
        Path moduleRoot = projectRoot.resolve(this.layout.getModuleRoot());
        Path springContextRoot = moduleRoot.resolve(this.layout.getSourceConfigModuleDir());

        Collection<SpringXmlConfigFile> configFiles = this.contextModel.stream().collect(Collectors.toList());
        for (SpringXmlConfigFile configFile : configFiles) {
            Path springContextPath = springContextRoot.resolve(configFile.getFileName());
            writeContext(Files.newBufferedWriter(springContextPath), configFile.getConfig());
        }
    }

    public void writeContext(Writer out, SpringXmlConfig config) throws IOException {
        try (IndentingWriter writer = this.writerFactory.createIndentingWriter("xml", out)) {
            this.contextWriter.writeTo(writer, config);
        }
    }
}
