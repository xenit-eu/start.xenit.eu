package eu.xenit.alfred.initializr.start.extensions.alfred.telemetry.grafana.provisioning;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GrafanaDataSource {

    private final String name;
    private final String type;

    @Builder.Default
    private String access = "proxy";

    private Integer orgId;
    private String url;
    private String user;
    private String password;
    private Boolean isDefault;

    @Builder.Default
    private Integer version = 1;

    @Builder.Default
    private Boolean editable = true;

}
