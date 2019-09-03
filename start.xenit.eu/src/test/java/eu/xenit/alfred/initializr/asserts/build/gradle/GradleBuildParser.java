package eu.xenit.alfred.initializr.asserts.build.gradle;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class GradleBuildParser {


    public static String extractSection(String name, String content) {
        if (content == null) {
            return "";
        }

        int openPluginsIndex = content.indexOf(name + " {");
        if (openPluginsIndex < 0) {
            return "";
        }

        content = content.substring(openPluginsIndex + name.length() + " {".length()).trim();

        // now 'content' contains the start of the section
        // the assumption here is that the closing brace is on a separate line
        List<String> section = new ArrayList<>();
        for(String line : content.split(System.lineSeparator())) {
            if ("}".equalsIgnoreCase(line.trim()))
            {
                // found end of section on a new line
                return String.join(System.lineSeparator(), section);
            }

            section.add(line);
        }

        // if we end up here, something went wrong
        // the matching closing brace was not found ?!
        throw new IllegalStateException("matching closing brace not found for section "+name);

    }


    public static class GradleBuildParserTests {

        public static final String GRADLE = String.join("\n",
                "plugins {",
                "    id 'eu.xenit.docker-alfresco' version '4.0.3'",
                "}",
                "",
                "group = 'com.example'",
                "version = '0.0.1-SNAPSHOT'",
                "",
                "dependencies {",
                "   alfrescoProvided \"org.alfresco:alfresco-enterprise:${alfrescoVersion}\"",
//                "}", // intentional whitespace
                "\t}", // intentional additional whitespace
                "");


        @Test
        public void testMissingPluginSection() {
            String empty = String.join("\n",
                    "group = 'com.example'",
                    "version = '0.0.1-SNAPSHOT'");

            String result = extractSection("plugins", empty);
            assertThat(result).isEmpty();
        }

        @Test
        public void testPluginSectionExtraction() {
            String result = extractSection("plugins", GRADLE);
            assertThat(result)
                    .isNotBlank()
                    .isEqualTo("id 'eu.xenit.docker-alfresco' version '4.0.3'");

        }

        @Test
        public void testDependenciesWithExtraCurlyBraces() {
            String result = extractSection("dependencies", GRADLE);
            assertThat(result)
                    .isNotBlank()
                    .isEqualTo("alfrescoProvided \"org.alfresco:alfresco-enterprise:${alfrescoVersion}\"");

        }

        @Test(expected = IllegalStateException.class)
        public void testUnbalancedBraces() {
            String missingClosingBrace = String.join("\n",
                    "plugins {",
                    "    id 'eu.xenit.docker-alfresco' version '4.0.3'");

            String result = extractSection("plugins", missingClosingBrace);

        }
    }
}
