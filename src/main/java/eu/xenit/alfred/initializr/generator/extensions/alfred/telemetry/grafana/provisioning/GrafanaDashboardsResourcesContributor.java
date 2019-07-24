/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package eu.xenit.alfred.initializr.generator.extensions.alfred.telemetry.grafana.provisioning;

import eu.xenit.alfred.initializr.generator.project.LocationStrategy;
import io.spring.initializr.generator.project.contributor.MultipleResourcesProjectContributor;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * A {@link MultipleResourcesProjectContributor} that contributes Grafana dashboards
 * for a given dataSource
 */
public class GrafanaDashboardsResourcesContributor extends MultipleResourcesProjectContributor {

    private static final String PROVISIONING_DASHBOARDS = "grafana/provisioning/dashboards/";

    private final LocationStrategy locationStrategy;
    private final String dataSource;

    public GrafanaDashboardsResourcesContributor(String dataSource, LocationStrategy locationStrategy) {
        super("classpath:" + PROVISIONING_DASHBOARDS + dataSource);


        this.dataSource = dataSource;
        this.locationStrategy = locationStrategy;
    }

    @Override
    public void contribute(Path projectRoot) throws IOException {
        Path dashboardResourceLocation = getDashboardResourceLocation(projectRoot);
        Files.createDirectories(dashboardResourceLocation);

        super.contribute(dashboardResourceLocation);
    }

    public Path getDashboardResourceLocation(Path projectRoot) {
        Path composeDirectory = projectRoot.resolve(locationStrategy.getLocation());
        return composeDirectory.resolve(PROVISIONING_DASHBOARDS).resolve(dataSource);
    }

}
