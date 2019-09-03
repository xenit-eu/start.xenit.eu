package eu.xenit.alfred.initializr.generator.extensions.alfred.telemetry.grafana.provisioning;


import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

public class GrafanaProvisioning {

    @Getter
    private final List<GrafanaDataSource> dataSources = new ArrayList<>();

    @Getter
    private final List<GrafanaDashboardProvider> dashboards = new ArrayList<>();

}
