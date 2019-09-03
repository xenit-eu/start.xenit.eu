package eu.xenit.alfred.initializr.generator.docker.compose.model;

import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
public class DockerComposeModel {

    public static final String DEFAULT_VERSION = "2.2";

    public DockerComposeModel()
    {
        this("");
    }

    @Getter
    private final String name;

    @Getter @Setter
    private String version = DEFAULT_VERSION;

    @Getter
    private final ComposeServices services = new ComposeServices();

    public DockerComposeModel services(ComposeServiceInfo... services)
    {
        this.services.putAll(Arrays.stream(services));
        return this;
    }

    @Getter
    private final ComposeVolumes volumes = new ComposeVolumes();

    public DockerComposeModel volumes(ComposeVolumeInfo... vols) {
        this.volumes.addAll(Arrays.asList(vols));
        return this;
    }


}
