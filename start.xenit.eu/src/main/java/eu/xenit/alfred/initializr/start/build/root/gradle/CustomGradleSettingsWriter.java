package eu.xenit.alfred.initializr.start.build.root.gradle;

import eu.xenit.alfred.initializr.generator.buildsystem.gradle.MultiProjectGradleBuild;
import io.spring.initializr.generator.buildsystem.gradle.GroovyDslGradleSettingsWriter;
import io.spring.initializr.generator.io.IndentingWriter;
import java.io.IOException;

public class CustomGradleSettingsWriter extends GroovyDslGradleSettingsWriter {

    public void writeTo(IndentingWriter writer, MultiProjectGradleBuild build) throws IOException {
        // override -> no plugin management
        writer.println("rootProject.name = '" + build.getSettings().getArtifact() + "'");

        this.writeSubProjects(writer, build);

    }

    private void writeSubProjects(IndentingWriter writer, MultiProjectGradleBuild build) {
        if (!build.hasModules()) return;

        writer.println();

        build.getModules().keySet().forEach(subproject -> {
            writer.print("include '");
            writer.print(subproject);
            writer.println("'");
        });

    }

}
