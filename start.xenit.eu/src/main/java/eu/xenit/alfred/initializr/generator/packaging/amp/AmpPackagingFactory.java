package eu.xenit.alfred.initializr.generator.packaging.amp;

import io.spring.initializr.generator.packaging.Packaging;
import io.spring.initializr.generator.packaging.PackagingFactory;
import io.spring.initializr.generator.packaging.jar.JarPackaging;

public class AmpPackagingFactory implements PackagingFactory {

    @Override
    public Packaging createPackaging(String id) {
        if (AmpPackaging.ID.equals(id)) {
            return new AmpPackaging();
        }
        return null;
    }
}
