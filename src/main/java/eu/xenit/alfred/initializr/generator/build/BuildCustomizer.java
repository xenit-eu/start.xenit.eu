package eu.xenit.alfred.initializr.generator.build;

import eu.xenit.alfred.initializr.generator.buildsystem.BuildSystem;
import io.spring.initializr.generator.buildsystem.Build;
import org.springframework.core.Ordered;

@FunctionalInterface
public interface BuildCustomizer<B extends BuildSystem> extends Ordered {
    void customize(B var1);

    default int getOrder() {
        return 0;
    }
}
