package eu.xenit.alfred.initializr.start.project.alfresco.platform.context;

import eu.xenit.alfred.initializr.generator.beans.config.SpringXmlConfig;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;
import lombok.Data;
import lombok.Getter;

public class SpringContextModel {

    @Getter
    final SpringXmlConfig moduleContext = new SpringXmlConfig();

    private final Map<String, SpringXmlConfig> contexts = new HashMap<>();

    public SpringContextModel() {
        this.contexts.put("module-context.xml", this.getModuleContext());
    }

    public SpringXmlConfig getOrCreateContext(String name) {
        return this.contexts.computeIfAbsent(name, (n) -> new SpringXmlConfig());
    }

    public Stream<SpringXmlConfigFile> stream() {
        return this.contexts.entrySet().stream()
                .map(entry -> new SpringXmlConfigFile(entry.getKey(), entry.getValue()));
    }

    @Data
    public class SpringXmlConfigFile {

        private final String fileName;
        private final SpringXmlConfig config;
    }
}
