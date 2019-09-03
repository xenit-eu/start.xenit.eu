package eu.xenit.alfred.initializr.generator.extensions.alfred.telemetry.grafana;

import static org.assertj.core.api.Assertions.assertThat;

import eu.xenit.alfred.initializr.start.extensions.alfred.telemetry.grafana.provisioning.GrafanaDashboardProvider;
import eu.xenit.alfred.initializr.start.extensions.alfred.telemetry.grafana.provisioning.GrafanaDashboardProvider.GrafanaDashboardOptions;
import eu.xenit.alfred.initializr.start.extensions.alfred.telemetry.grafana.provisioning.GrafanaDataSource;
import eu.xenit.alfred.initializr.start.extensions.alfred.telemetry.grafana.provisioning.GrafanaProvisioning;
import eu.xenit.alfred.initializr.start.extensions.alfred.telemetry.grafana.provisioning.GrafanaProvisioningWriterDelegate;
import io.spring.initializr.generator.io.IndentingWriter;
import io.spring.initializr.generator.io.IndentingWriterFactory;
import io.spring.initializr.generator.io.SimpleIndentStrategy;
import java.io.IOException;
import java.io.StringWriter;
import java.util.function.Consumer;
import org.junit.Test;

public class GrafanaDataSourcesProvisioningWriterDelegateTest {


    private final SimpleIndentStrategy TWO_SPACES = new SimpleIndentStrategy("  ");
    private IndentingWriterFactory indentingWriterFactory = IndentingWriterFactory.create(TWO_SPACES);

    @Test
    public void testGrafanaProvisioningDataSources() throws IOException {
        GrafanaProvisioning provisioning = new GrafanaProvisioning();
        provisioning.getDataSources().add(
                GrafanaDataSource.builder()
                        .name("Graphite")
                        .type("graphite")
                        .access("proxy")
                        .orgId(2)
                        .url("http://graphite-api:8000")
                        .user("user")
                        .password("password")
                        .isDefault(true)
                        .version(3)
                        .editable(true)
                        .build()
        );

        GrafanaProvisioningWriterDelegate writerDelegate = new GrafanaProvisioningWriterDelegate();
        String yml = withYmlWriter(writer -> writerDelegate.writeDataSources(writer, provisioning));

        assertThat(yml)
                .isNotBlank()
                .isEqualTo(String.join("\n",
                        "apiVersion: 1",
                        "",
                        "datasources:",
                        "  - name: Graphite",
                        "    type: graphite",
                        "    access: proxy",
                        "    orgId: 2",
                        "    url: http://graphite-api:8000",
                        "    user: user",
                        "    password: password",
                        "    isDefault: true",
                        "    version: 3",
                        "    editable: true",
                        "" // EOF on new line
                ));
    }

    @Test
    public void testGrafanaProvisioningDashboards() throws IOException {
        GrafanaProvisioning provisioning = new GrafanaProvisioning();
        provisioning.getDashboards().add(
                GrafanaDashboardProvider.builder()
                        .name("Graphite dashboard")
                        .type("file")
                        .folder("")
                        .editable(true)
                        .options(GrafanaDashboardOptions
                                .builder()
                                .path("/etc/grafana/provisioning/dashboards/graphite")
                                .build())
                        .build()
        );

        GrafanaProvisioningWriterDelegate writerDelegate = new GrafanaProvisioningWriterDelegate();
        String yml = withYmlWriter(writer -> writerDelegate.writeDashboards(writer, provisioning));

        assertThat(yml)
                .isNotBlank()
                .isEqualTo(String.join("\n",
                        "apiVersion: 1",
                        "",
                        "providers:",
                        "  - name: 'Graphite dashboard'",
                        "    folder: ''",
                        "    type: file",
                        "    editable: true",
                        "    options:",
                        "      path: /etc/grafana/provisioning/dashboards/graphite",
                        ""

                ));
    }


    private String withYmlWriter(Consumer<IndentingWriter> consumer)
            throws IOException {

        StringWriter out = new StringWriter();
        try (IndentingWriter writer = this.indentingWriterFactory.createIndentingWriter("yml", out)) {
            consumer.accept(writer);
        }

        return out.toString();
    }


}
