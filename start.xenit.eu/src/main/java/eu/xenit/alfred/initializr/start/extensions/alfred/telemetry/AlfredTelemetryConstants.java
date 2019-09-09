package eu.xenit.alfred.initializr.start.extensions.alfred.telemetry;

import io.spring.initializr.generator.buildsystem.Dependency;
import io.spring.initializr.generator.buildsystem.DependencyScope;
import io.spring.initializr.generator.version.VersionReference;

public class AlfredTelemetryConstants {

    public static class AlfredTelemetry {

        public static final String ID = "alfred-telemetry";

        // The grpup/artifact/version should be single sourced from the application.yml
        @Deprecated
        public static final String VERSION = "0.1.0";
    }

    public static class Micrometer {
        public static final String GROUP_ID = "io.micrometer";
        public static final String MODULE = "micrometer-core";

        // The grpup/artifact/version should be single sourced from the application.yml
        @Deprecated
        public static final String VERSION = "1.0.6";

        static final Dependency DEPENDENCY = Dependency
                .withCoordinates(GROUP_ID, MODULE)
                .scope(DependencyScope.PROVIDED_RUNTIME)
                .version(VersionReference.ofProperty("micrometer-version"))
                .build();

        public static class Graphite {

            // The grpup/artifact/version should be single sourced from the application.yml
            public static final String ID = "micrometer-graphite";
        }

    }

}
