package eu.xenit.alfred.initializr.generator.build;

import io.spring.initializr.generator.buildsystem.Build;
import org.springframework.core.Ordered;

@FunctionalInterface
public interface BuildCustomizer<B extends Build> extends Ordered {
    void customize(B var1);

    default int getOrder() {
        return 0;
    }
}
