package eu.xenit.alfred.initializr.start.extensions.alfred.telemetry.grafana;

import eu.xenit.alfred.initializr.start.docker.compose.DockerComposeGradlePluginConfiguration;
import eu.xenit.alfred.initializr.start.docker.compose.DockerComposeGradlePluginConfigurationCustomizer;

class GrafanaDockerComposeGradlePluginConfigurationCustomizer implements
        DockerComposeGradlePluginConfigurationCustomizer {

    private final String composeFilename;

    GrafanaDockerComposeGradlePluginConfigurationCustomizer(String composeFilename)
    {
        this.composeFilename = composeFilename;
    }

    @Override
    public void customize(DockerComposeGradlePluginConfiguration configuration) {
        configuration.getUseComposeFiles().add(composeFilename);
    }

    @Override
    public int getOrder() {
        return 100;
    }
}
