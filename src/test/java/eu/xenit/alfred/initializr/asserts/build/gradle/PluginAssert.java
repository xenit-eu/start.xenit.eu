package eu.xenit.alfred.initializr.asserts.build.gradle;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.AbstractStringAssert;
import org.junit.Test;

public class PluginAssert extends AbstractStringAssert<PluginAssert> {

    public PluginAssert(String gradleBuildContent) {
        super(extractPluginSection(gradleBuildContent), PluginAssert.class);
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

    private static String extractPluginSection(String content) {
        return GradleBuildParser.extractSection("plugins", content);
    }


    public static class PluginAssertTests {
        @Test
        public void testPluginAsserts() {
            new PluginAssert(GradleBuildParser.GradleBuildParserTests.GRADLE)
                    .hasPlugin("eu.xenit.docker-alfresco", "4.0.3")
                    .hasPlugin("eu.xenit.docker-alfresco")
                    .doesNotHavePlugin("eu.xenit.alfresco");
        }

        @Test(expected = AssertionError.class)
        public void hasPlugin_expectFailure() {
            new PluginAssert(GradleBuildParser.GradleBuildParserTests.GRADLE)
                    .hasPlugin("eu.xenit.alfresco");
        }

        @Test(expected = AssertionError.class)
        public void doesNotHavePlugin_expectFailure() {
            new PluginAssert(GradleBuildParser.GradleBuildParserTests.GRADLE)
                    .doesNotHavePlugin("eu.xenit.docker-alfresco");
        }
    }

}
