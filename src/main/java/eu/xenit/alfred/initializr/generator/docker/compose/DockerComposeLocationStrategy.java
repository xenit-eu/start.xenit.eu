package eu.xenit.alfred.initializr.generator.docker.compose;

import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.util.Assert;

public class DockerComposeLocationStrategy {

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

    public Path getComposePath() {
        return this.composeLocation;
    }
}
