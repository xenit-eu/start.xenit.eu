package eu.xenit.alfred.initializr.asserts.grafana;

import static org.assertj.core.api.Assertions.assertThat;

import eu.xenit.alfred.initializr.web.project.GrafanaProvisioningResult;
import java.util.function.Consumer;
import org.assertj.core.api.AbstractAssert;

public class GrafanaProvisioningAssert extends AbstractAssert<GrafanaProvisioningAssert, GrafanaProvisioningResult> {

    private final GrafanaProvisioningResult result;

    public GrafanaProvisioningAssert(GrafanaProvisioningResult result) {
        super(result, GrafanaProvisioningAssert.class);
        this.result = result;
    }

    public GrafanaProvisioningAssert assertDataSource(String name, Consumer<GrafanaDataSourceAssert> asserts) {
        this.hasDatasource(name);

        String content = this.result.getDataSources().get(name);
        assertThat(content).as("DataSource <%s>", name).isNotBlank();

        asserts.accept(new GrafanaDataSourceAssert(name, content));

        return this;
    }

    public GrafanaProvisioningAssert hasDatasource(String name)
    {
        assertThat(this.result.getDataSources()).containsKeys(name);
        return this;
    }

    public GrafanaProvisioningAssert doesNotHaveDataSource(String name)
    {
        assertThat(this.result.getDataSources()).doesNotContainKeys(name);
        return this;
    }

    public GrafanaProvisioningAssert assertDashboard(String name, Consumer<GrafanaDashboardAssert> asserts) {
        this.hasDashboard(name);

        String content = this.result.getDashboards().get(name);
        assertThat(content).as("Dashboard <%s>", name).isNotBlank();

        asserts.accept(new GrafanaDashboardAssert(name, content));

        return this;
    }

    public GrafanaProvisioningAssert hasDashboard(String name)
    {
        assertThat(this.result.getDashboards()).containsKeys(name);
        return this;
    }

    public GrafanaProvisioningAssert doesNotHaveDashboard(String name) {
        assertThat(this.result.getDashboards()).doesNotContainKeys(name);
        return this;
    }
}
