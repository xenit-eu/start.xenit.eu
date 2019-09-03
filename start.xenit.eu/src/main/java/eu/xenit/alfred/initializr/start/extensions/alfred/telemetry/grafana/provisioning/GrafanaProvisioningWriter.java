package eu.xenit.alfred.initializr.start.extensions.alfred.telemetry.grafana.provisioning;

import java.io.IOException;
import java.io.Writer;

public interface GrafanaProvisioningWriter {

    void writeDataSources(Writer out) throws IOException;
    String getDataSourceFilename();

    void writeDashboards(Writer out) throws IOException;
    String getDashboardsFilename();
}
