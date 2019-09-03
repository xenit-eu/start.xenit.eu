package eu.xenit.alfred.initializr.web.project;

import eu.xenit.alfred.initializr.start.extensions.alfred.telemetry.grafana.provisioning.GrafanaProvisioningWriter;
import io.spring.initializr.generator.project.ProjectAssetGenerator;
import io.spring.initializr.generator.project.ProjectGenerationContext;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.ObjectProvider;

public class GrafanaProvisioningAssetGenerator implements ProjectAssetGenerator<GrafanaProvisioningResult> {

    @Override
    public GrafanaProvisioningResult generate(ProjectGenerationContext context) {
        ObjectProvider<GrafanaProvisioningWriter> writers = context.getBeanProvider(GrafanaProvisioningWriter.class);

        return new GrafanaProvisioningResult(
                this.renderDataSources(writers),
                this.renderDashboards(writers)
        );
    }

    private Map<String, String> renderDataSources(ObjectProvider<GrafanaProvisioningWriter> writers) {
        return writers.orderedStream().collect(
                Collectors.toMap(
                        GrafanaProvisioningWriter::getDataSourceFilename,
                        grafana -> this.writeContent(grafana::writeDataSources)
                ));
    }

    private Map<String, String> renderDashboards(ObjectProvider<GrafanaProvisioningWriter> writers) {
        return writers.orderedStream().collect(
                Collectors.toMap(
                        GrafanaProvisioningWriter::getDashboardsFilename,
                        grafana -> this.writeContent(grafana::writeDashboards)
                ));
    }

    private String writeContent(ThrowingConsumer<Writer, IOException> writer) {
        try {
            StringWriter out = new StringWriter();
            writer.accept(out);
            return out.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FunctionalInterface
    interface ThrowingConsumer<T, E extends Throwable> {
        void accept(T var1) throws E;
    }
}
