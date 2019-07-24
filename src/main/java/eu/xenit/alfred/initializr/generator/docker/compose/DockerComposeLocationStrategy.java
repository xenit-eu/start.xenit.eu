package eu.xenit.alfred.initializr.generator.docker.compose;

import eu.xenit.alfred.initializr.generator.project.LocationStrategy;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.util.Assert;

public class DockerComposeLocationStrategy implements LocationStrategy {

    private final Path composeLocation;

    public DockerComposeLocationStrategy()
    {
        this(Paths.get(""));
    }

    public DockerComposeLocationStrategy(Path composeLocation)
    {
        Assert.isTrue(!composeLocation.isAbsolute(), "Expecting relative path");
        this.composeLocation = composeLocation;
    }

    @Override
    public Path getLocation() {
        return this.composeLocation;
    }
}
