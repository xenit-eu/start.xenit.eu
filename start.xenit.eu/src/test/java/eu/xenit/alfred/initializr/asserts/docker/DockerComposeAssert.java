package eu.xenit.alfred.initializr.asserts.docker;

import org.assertj.core.api.AbstractStringAssert;

public class DockerComposeAssert extends AbstractStringAssert<DockerComposeAssert> {

    public DockerComposeAssert(String actual) {
        super(actual, DockerComposeAssert.class);
    }


    public DockerComposeAssert hasService(String name)
    {
        this.contains("    " + name + ":");
        return this;
    }

}
