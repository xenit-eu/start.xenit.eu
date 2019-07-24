package eu.xenit.alfred.initializr.generator.extensions.alfred.telemetry.grafana.provisioning;

import eu.xenit.alfred.initializr.generator.io.YmlWriter;
import io.spring.initializr.generator.io.IndentingWriter;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import org.springframework.util.StringUtils;

public class GrafanaProvisioningWriterDelegate {

    public void writeDataSources(IndentingWriter writer, GrafanaProvisioning provisioning) {
        writeApiVersion(writer);
        writeDataSources(writer, provisioning.getDataSources());
    }

    public void writeDashboards(IndentingWriter writer, GrafanaProvisioning provisioning) {
        writeApiVersion(writer);
        writeDashboards(writer, provisioning.getDashboards());
    }

    private void writeDataSources(IndentingWriter writer, List<GrafanaDataSource> dataSources) {
        writeSequence(writer, dataSources, "datasources", this::writeDataSource);
    }

    private void writeDashboards(IndentingWriter writer, List<GrafanaDashboardProvider> dashboards) {
        writeSequence(writer, dashboards, "providers", this::writeDashboardProvider);
    }

    private void writeDataSource(YmlWriter yml, GrafanaDataSource dataSource) {
        writeAttribute(yml, "name", dataSource.getName(), false);
        writeAttribute(yml, "type", dataSource.getType(), false);

        writeAttribute(yml, "access", dataSource.getAccess(), true);
        writeAttribute(yml, "orgId", dataSource.getOrgId(), true);

        writeAttribute(yml, "url", dataSource.getUrl(), true);
        writeAttribute(yml, "user", dataSource.getUser(), true);
        writeAttribute(yml, "password", dataSource.getPassword(), true);
        writeAttribute(yml, "isDefault", dataSource.getIsDefault(), true);
        writeAttribute(yml, "version", dataSource.getVersion(), true);
        writeAttribute(yml, "editable", dataSource.getEditable(), true);
    }

    private void writeDashboardProvider(YmlWriter yml, GrafanaDashboardProvider dashboard) {
        writeAttribute(yml, "name", StringUtils.quoteIfString(dashboard.getName()), true);
        writeAttribute(yml, "orgId", dashboard.getOrgId(), true);
        writeAttribute(yml, "folder", StringUtils.quoteIfString(dashboard.getFolder()), false);
        writeAttribute(yml, "folderUid", StringUtils.quoteIfString(dashboard.getFolderUid()), true);
        writeAttribute(yml, "type", dashboard.getType(), false);
        writeAttribute(yml, "disableDeletion", dashboard.getDisableDeletion(), true);
        writeAttribute(yml, "editable", dashboard.getEditable(), true);
        writeAttribute(yml, "updateIntervalSeconds", dashboard.getUpdateIntervalSeconds(), true);

        yml.println("options:");
        yml.indented(() -> {
            writeAttribute(yml, "path", dashboard.getOptions().getPath(), false);
        });
    }

    private <T> void writeSequence(IndentingWriter writer, List<T> collection, String name,
            BiConsumer<YmlWriter, T> callback) {
        writer.println(name + ":");
        writer.indented(() -> {
            YmlWriter yml = new YmlWriter(writer);
            collection.forEach(element -> {
                yml.sequenceElement(() -> callback.accept(yml, element));
            });
        });
    }

    private void writeAttribute(YmlWriter writer, String propertyName, Object value, boolean skipIfEmpty)
    {
        if (StringUtils.isEmpty(value)) {
            if (skipIfEmpty) {
                return;
            } else {
                value = "";
            }
        }

        writer.println(propertyName+": "+ Objects.toString(value));
    }


    private void writeApiVersion(IndentingWriter writer) {
        writer.println("apiVersion: 1");
        writer.println();
    }

}
