package eu.xenit.alfred.initializr.start.extensions.alfred.telemetry.grafana.provisioning;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GrafanaDashboardProvider {

    /**
     * A unique dashboard provider name
     */
    private String name;

    /**
     * org id. will default to orgId 1 if not specified
     */
    private Integer orgId;

    /**
     * The name of the dashboard folder. Required.
     */
    @Builder.Default
    private final String folder = "";

    /**
     * The folder UID will be automatically generated if not specified
     */
    private String folderUid;

    /**
     * The provider type. Required
     */
    @Builder.Default
    private final String type = "file";

    /**
     * Disable dashboard deletion.
     */
    private Boolean disableDeletion;

    /**
     * Enable dashboard editing
     */
    private Boolean editable;

    /**
     * Interval that says how often Grafana will scan for changed dashboards
     */
    private Integer updateIntervalSeconds;

    @Builder.Default
    private GrafanaDashboardOptions options = new GrafanaDashboardOptions("");

    @Data
    @Builder
    public static class GrafanaDashboardOptions {

        /**
         * The path to dashboard files on disk. Required
         */

        private final String path;

    }
}
