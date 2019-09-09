package eu.xenit.alfred.initializr.asserts.build.gradle;

import org.assertj.core.api.AbstractStringAssert;
import org.junit.Test;

public class PluginAssert extends AbstractStringAssert<PluginAssert> {

    private PluginAssert(String plugins) {
        super(plugins, PluginAssert.class);
    }

    public static PluginAssert from(String buildGradleContent) {
        return new PluginAssert(GradleBuildParser.extractSection("plugins", buildGradleContent));
    }

    public PluginAssert hasPlugin(String plugin) {
        return this.contains("id '" + plugin + "'");
    }

    public PluginAssert hasPlugin(String plugin, String version) {
        return this.contains("id '" + plugin + "' version '" + version + "'");
    }

    public PluginAssert doesNotHavePlugin(String plugin) {
        return this.doesNotContain(plugin);
    }


    public static class PluginAssertTests {
        @Test
        public void testPluginAsserts() {
            PluginAssert.from(GradleBuildParser.GradleBuildParserTests.GRADLE)
                    .hasPlugin("eu.xenit.docker-alfresco", "4.0.3")
                    .hasPlugin("eu.xenit.docker-alfresco")
                    .doesNotHavePlugin("eu.xenit.alfresco");
        }

        @Test(expected = AssertionError.class)
        public void hasPlugin_expectFailure() {
            PluginAssert.from(GradleBuildParser.GradleBuildParserTests.GRADLE)
                    .hasPlugin("eu.xenit.alfresco");
        }

        @Test(expected = AssertionError.class)
        public void doesNotHavePlugin_expectFailure() {
            PluginAssert.from(GradleBuildParser.GradleBuildParserTests.GRADLE)
                    .doesNotHavePlugin("eu.xenit.docker-alfresco");
        }
    }

}
