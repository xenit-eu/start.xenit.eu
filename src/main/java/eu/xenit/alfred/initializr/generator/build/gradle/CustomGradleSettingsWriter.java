package eu.xenit.alfred.initializr.generator.build.gradle;

import io.spring.initializr.generator.buildsystem.gradle.GradleSettingsWriter;
import io.spring.initializr.generator.io.IndentingWriter;
import java.io.IOException;

public class CustomGradleSettingsWriter extends GradleSettingsWriter {

    public void writeTo(IndentingWriter writer, MultiProjectGradleBuild build) throws IOException {
        // override -> no plugin management
        writer.println("rootProject.name = '" + build.getArtifact() + "'");

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
