package eu.xenit.alfred.initializr.generator.docker.compose.model;

import java.util.HashMap;
import lombok.Data;
import lombok.NonNull;

public class ComposeEnvironment extends HashMap<String, String> {
    public ComposeEnvironment with(String key, String value) {
        put(key, value);
        return this;
    }

    public static EnvironmentEntry env(String key, String value) {
        return new EnvironmentEntry(key, value);
    }

    @Data
    public static class EnvironmentEntry {

        @NonNull
        private final String key;

        private final String value;
    }
}


