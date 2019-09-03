package eu.xenit.alfred.initializr.generator.extensions.alfred.telemetry.grafana.provisioning;

import eu.xenit.alfred.initializr.generator.project.LocationStrategy;
import io.spring.initializr.generator.io.IndentingWriter;
import io.spring.initializr.generator.io.IndentingWriterFactory;
import io.spring.initializr.generator.project.contributor.ProjectContributor;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

public class GrafanaProvisioningContributor implements ProjectContributor, GrafanaProvisioningWriter {

    private final String name;
    private final GrafanaProvisioning config;
    private final GrafanaProvisioningWriterDelegate writer;
    private final IndentingWriterFactory indentingWriterFactory;
    private final LocationStrategy locationStrategy;

    public GrafanaProvisioningContributor(
            String name,
            GrafanaProvisioning config,
            GrafanaProvisioningWriterDelegate writer,
            IndentingWriterFactory indentingWriterFactory,
            LocationStrategy locationStrategy) {
        this.name = name;
        this.config = config;
        this.writer = writer;
            this.indentingWriterFactory = indentingWriterFactory;
        this.locationStrategy = locationStrategy;
    }

    @Override
    public void contribute(Path projectRoot) throws IOException {

        // make sure directory exists
        Path provisioning = Files.createDirectories(
                projectRoot.resolve(this.locationStrategy.getLocation())
                        .resolve("grafana/provisioning"));

        this.contributeDataSources(provisioning);
        this.contributeDashboards(provisioning);
    }

    private void contributeDataSources(Path provisioning) throws IOException {
        Path datasources = Files.createDirectories(provisioning.resolve("datasources"));
        Path yml = Files.createFile(datasources.resolve(this.getDataSourceFilename()));
        this.writeDataSources(Files.newBufferedWriter(yml));
    }

    private void contributeDashboards(Path provisioning) throws IOException {
        Path dashboards = Files.createDirectories(provisioning.resolve("dashboards"));
        Path yml = Files.createFile(dashboards.resolve(this.getDashboardsFilename()));
        this.writeDashboards(Files.newBufferedWriter(yml));
    }

    public void writeDataSources(Writer out) throws IOException {
        try (IndentingWriter writer = this.indentingWriterFactory.createIndentingWriter("yml", out)) {
            this.writer.writeDataSources(writer, this.config);
        }
    }

    public String getDataSourceFilename() {
        return this.name + ".yml";
    }

    public void writeDashboards(Writer out) throws IOException {
        try (IndentingWriter writer = this.indentingWriterFactory.createIndentingWriter("yml", out)) {
            this.writer.writeDashboards(writer, this.config);
        }
    }

    public String getDashboardsFilename() {
        return this.name + ".yml";
    }
}
