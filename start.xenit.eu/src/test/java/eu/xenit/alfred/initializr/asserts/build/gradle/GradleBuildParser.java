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
        // we start tracking the 'depth', to support nested sections
        //
        // ASSUMPTION: the matching closing brace is:
        // 1. at the end of a line (after trimming)
        // 2. alone on a new line (is that the same as case (1) ?
        // This is to avoid running into variables in strings like "${foo}"

        List<String> section = new ArrayList<>();
        int depth = 1;
        for (String line : content.split(System.lineSeparator())) {

            if (line.trim().matches("^\\w+\\s\\{.*$")) {
                depth++;
            }
//            else if (line.trim().startsWith("}")) {
//                depth--;
//            }

            if (line.trim().endsWith("}")) {
                depth--;

                if (depth == 0) {

                    if (!line.trim().equalsIgnoreCase("}"))
                    {
                        // there is useful content before the closing '}'
                        // Example: maven { url 'https://artifacts.alfresco.com/nexus/content/groups/public/' }

                        section.add(line.substring(0, line.indexOf("}")).trim());
                    }
                    return String.join(System.lineSeparator(), section);
                }
            }

            section.add(line);
        }

        // if we end up here, something went wrong
        // the matching closing brace was not found ?!
        throw new IllegalStateException("matching closing brace not found for section " + name);

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

        @Test
        public void testNested() {
            String gradle = String.join("\n",
                    "sourceSets {",
                    "   main {",
                    "       amp {",
                    "           dynamicExtension()",
                    "       }",
                    "   }",
                    "}");

            String sourceSets = extractSection("sourceSets", gradle);
            String main = extractSection("main", sourceSets);
            String amp = extractSection("amp", main);

            assertThat(amp).isEqualTo("dynamicExtension()");
        }

        @Test
        public void testOnSingleLine() {
            String gradle = String.join("\n",
                    "repositories {",
                    "    mavenCentral()",
                    "    maven { url 'https://artifacts.alfresco.com/nexus/content/groups/public/' }",
                    "}",
                    ""
            );

            String repositories = extractSection("repositories", gradle);
            String maven = extractSection("maven", repositories);

            assertThat(maven).isEqualTo("url 'https://artifacts.alfresco.com/nexus/content/groups/public/'");
        }
    }
}
