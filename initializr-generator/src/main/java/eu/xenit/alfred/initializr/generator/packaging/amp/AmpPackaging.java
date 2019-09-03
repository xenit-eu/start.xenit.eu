package eu.xenit.alfred.initializr.generator.packaging.amp;

import io.spring.initializr.generator.packaging.Packaging;

public class AmpPackaging implements Packaging {

    public static final String ID = "amp";

    @Override
    public String id() {
        return ID;
    }

    @Override
    public String toString() {
        return id();
    }
}
