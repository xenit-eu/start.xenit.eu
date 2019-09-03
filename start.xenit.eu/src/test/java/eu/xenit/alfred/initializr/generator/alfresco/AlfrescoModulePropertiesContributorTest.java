package eu.xenit.alfred.initializr.generator.alfresco;

import static org.mockito.Mockito.mock;

import eu.xenit.alfred.initializr.asserts.AlfrescoModulePropertiesAssert;
import eu.xenit.alfred.initializr.start.alfresco.AlfrescoModuleProjectLayout;
import eu.xenit.alfred.initializr.start.alfresco.AlfrescoModuleProperties;
import eu.xenit.alfred.initializr.start.alfresco.AlfrescoModulePropertiesContributor;
import eu.xenit.alfred.initializr.start.alfresco.AlfrescoModulePropertiesWriter;
import io.spring.initializr.generator.io.IndentingWriterFactory;
import java.io.IOException;
import java.io.StringWriter;
import org.junit.Test;

public class AlfrescoModulePropertiesContributorTest {

    @Test
    public void writeBuild() throws IOException {
        AlfrescoModuleProperties properties = new AlfrescoModuleProperties();
        properties.setId("id");
        properties.setDescription("desc");
        properties.setVersion("1.0");
        properties.setTitle("title");

        AlfrescoModulePropertiesContributor contributor = new AlfrescoModulePropertiesContributor(
                mock(AlfrescoModuleProjectLayout.class),
                properties,
                IndentingWriterFactory.create(indent -> "  "),
                new AlfrescoModulePropertiesWriter()
        );

        StringWriter writer = new StringWriter();
        contributor.writeBuild(writer);

        String output = writer.toString();

        new AlfrescoModulePropertiesAssert(output)
                .assertHasProperty("module.id", "id")
                .assertHasProperty("module.description", "desc")
                .assertHasProperty("module.version", "1.0")
                .assertHasProperty("module.title", "title");
    }
}