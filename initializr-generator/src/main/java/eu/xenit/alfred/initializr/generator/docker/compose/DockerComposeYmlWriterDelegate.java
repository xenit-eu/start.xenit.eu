package eu.xenit.alfred.initializr.generator.docker.compose;

import eu.xenit.alfred.initializr.generator.docker.compose.model.ComposeServiceInfo;
import eu.xenit.alfred.initializr.generator.docker.compose.model.ComposeServices;
import eu.xenit.alfred.initializr.generator.docker.compose.model.ComposeVolumeInfo;
import eu.xenit.alfred.initializr.generator.docker.compose.model.ComposeVolumes;
import eu.xenit.alfred.initializr.generator.docker.compose.model.DockerComposeModel;
import io.spring.initializr.generator.io.IndentingWriter;
import java.util.Collection;
import java.util.Map;
import java.util.function.Function;

public class DockerComposeYmlWriterDelegate {

    public void writeTo(IndentingWriter writer, DockerComposeModel file) {
        writeVersion(writer, file);
        writeServices(writer, file.getServices());
        writeVolumes(writer, file.getVolumes());
    }

    private void writeVersion(IndentingWriter writer, DockerComposeModel file) {
        writer.println("version: '" + file.getVersion() + "'");

    }

    private void writeServices(IndentingWriter writer, ComposeServices services) {
        if (services.isEmpty()) {
            return;
        }

        writer.println();
        writer.println("services:");
        writer.indented(() -> services.stream().forEach(service -> writeService(writer, service)));
    }

    private void writeVolumes(IndentingWriter writer, ComposeVolumes volumes) {
        if (volumes.isEmpty()) {
            return;
        }

        writer.println();
        writer.println("volumes:");
        writer.indented(() -> volumes.stream().forEach(vol -> writeVolume(writer, vol)));
    }

    private void writeVolume(IndentingWriter writer, ComposeVolumeInfo vol) {
        writer.println(vol.getName()+":");
    }

    private void writeService(IndentingWriter writer, ComposeServiceInfo service) {
        writer.println(service.name() + ":");
        writer.indented(() -> {
            // print build ?
            writeProperty(writer, "image", service.image());
            writeCollection(writer, "ports", service.getPorts(), Function.identity());
            writeCollection(writer, "volumes", service.getVolumes(), Function.identity());
            writeCollection(writer, "environment", service.getEnvironment().entrySet(),
                    (Map.Entry<String, String> entry) -> entry.getKey() + "=" + entry.getValue());

        });
    }


    private <T> void writeCollection(IndentingWriter writer, String name, Collection<T> collection,
            Function<T, String> converter) {
        if (collection.isEmpty()) {
            return;
        }

        writer.println(name + ":");
        //writer.indented(() ->
        collection.stream().map(converter).map(str -> "- " + str).forEach(writer::println);
//        );
    }

    private void writeProperty(IndentingWriter writer, String key, String value) {
        if (value == null) {
            return;
        }
        writer.println(key + ": " + value);
    }


}
