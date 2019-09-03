package eu.xenit.alfred.initializr.generator.extensions.alfred.telemetry.grafana.provisioning;


import static org.assertj.core.api.Assertions.assertThat;

import eu.xenit.alfred.initializr.generator.project.LocationStrategy;
import eu.xenit.alfred.initializr.start.extensions.alfred.telemetry.grafana.provisioning.GrafanaDashboardsResourcesContributor;
import java.nio.file.Paths;
import org.junit.Test;

public class GrafanaDashboardsResourcesContributorTest {

    @Test
    public void testLocation() {
        LocationStrategy location = () -> Paths.get("compose");
        GrafanaDashboardsResourcesContributor contributor = new GrafanaDashboardsResourcesContributor("foobar",
                location);

        assertThat(contributor.getDashboardResourceLocation(Paths.get(".")))
                .isEqualTo(Paths.get("./compose/grafana/provisioning/dashboards/foobar/"));

    }
}