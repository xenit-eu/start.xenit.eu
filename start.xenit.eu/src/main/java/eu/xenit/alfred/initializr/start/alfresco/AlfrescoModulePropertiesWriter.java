package eu.xenit.alfred.initializr.start.alfresco;

import io.spring.initializr.generator.io.IndentingWriter;
import java.io.IOException;

public class AlfrescoModulePropertiesWriter {

    public void writeTo(IndentingWriter writer, AlfrescoModuleProperties properties) throws IOException {

        this.writeModuleProperty(writer, "id", properties.getId());
        this.writeModuleProperty(writer, "description", properties.getDescription());
        this.writeModuleProperty(writer, "version", properties.getVersion());
        this.writeModuleProperty(writer, "title", properties.getTitle());

    }

    private void writeModuleProperty(IndentingWriter writer, String key, String value) {
        writer.println("module." + key +"="+value);
    }

}
