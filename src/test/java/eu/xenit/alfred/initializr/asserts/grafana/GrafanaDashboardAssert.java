package eu.xenit.alfred.initializr.asserts.grafana;

import org.assertj.core.api.AbstractStringAssert;

public class GrafanaDashboardAssert extends AbstractStringAssert<GrafanaDashboardAssert> {

    private final String name;

    public GrafanaDashboardAssert(String name, String actual) {
        super(actual, GrafanaDashboardAssert.class);
        this.name = name;
    }
}
