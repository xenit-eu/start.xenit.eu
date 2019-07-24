package eu.xenit.alfred.initializr.asserts.grafana;

import org.assertj.core.api.AbstractStringAssert;

public class GrafanaDataSourceAssert extends AbstractStringAssert<GrafanaDataSourceAssert> {

    private final String name;

    public GrafanaDataSourceAssert(String name, String actual) {
        super(actual, GrafanaDataSourceAssert.class);
        this.name = name;
    }
}
