package eu.xenit.alfred.initializr.start.extensions.alfred.telemetry.grafana;

import eu.xenit.alfred.initializr.start.sdk.alfred.compose.config.DockerComposeGradlePluginConfiguration;
import eu.xenit.alfred.initializr.start.sdk.alfred.compose.config.DockerComposeGradlePluginConfigurationCustomizer;

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
