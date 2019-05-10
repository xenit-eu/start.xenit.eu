package eu.xenit.alfred.initializr.asserts;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import eu.xenit.alfred.initializr.generator.alfresco.AlfrescoModuleProperties;
import java.io.IOException;
import java.io.StringReader;
import java.util.Properties;
import org.hamcrest.Matcher;

public class AlfrescoModulePropertiesAssert {

    private final Properties properties;

    public AlfrescoModulePropertiesAssert(String content) throws IOException {
        this(asProperties(content));
    }

    public AlfrescoModulePropertiesAssert(Properties properties) {
        this.properties = properties;
    }

    private static Properties asProperties(String content) throws IOException {
        Properties props = new Properties();
        props.load(new StringReader(content));
        return props;
    }

    public AlfrescoModulePropertiesAssert assertHasProperty(String key, String value) {
        return assertHasProperty(key, is(value));
    }

    public AlfrescoModulePropertiesAssert assertHasProperty(String key, Matcher<String> valueMatcher) {
        assertThat(this.properties.getProperty(key), valueMatcher);
        return this;
    }


}
