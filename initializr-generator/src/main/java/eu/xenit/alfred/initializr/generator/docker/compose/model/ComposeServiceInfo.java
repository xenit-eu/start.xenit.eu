package eu.xenit.alfred.initializr.generator.docker.compose.model;

import eu.xenit.alfred.initializr.generator.docker.compose.model.ComposeEnvironment.EnvironmentEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@RequiredArgsConstructor
@Accessors(fluent = true)
public class ComposeServiceInfo {

    @Getter
    private final String name;

    @Getter
    @Setter
    private String image;


    private final List<String> volumes = new ArrayList<>();

    public List<String> getVolumes() {
        return this.volumes;
    }

    public ComposeServiceInfo volumes(String... volumes) {
        this.volumes.addAll(Arrays.asList(volumes));
        return this;
    }

    private final List<String> ports = new ArrayList<>();
    public List<String> getPorts() {
        return this.ports;
    }

    public ComposeServiceInfo ports(String... ports) {
        this.ports.addAll(Arrays.asList(ports));
        return this;
    }

    private final ComposeEnvironment environment = new ComposeEnvironment();
    public ComposeEnvironment getEnvironment() {
        return this.environment;
    }

    public ComposeServiceInfo environment(EnvironmentEntry... envs) {
        this.environment.putAll(
                Arrays.stream(envs).collect(
                        Collectors.toMap(EnvironmentEntry::getKey, EnvironmentEntry::getValue)));

        return this;
    }
}
