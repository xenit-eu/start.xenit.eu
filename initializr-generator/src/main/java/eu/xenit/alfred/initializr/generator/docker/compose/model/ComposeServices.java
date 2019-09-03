package eu.xenit.alfred.initializr.generator.docker.compose.model;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ComposeServices {

    // should switch to LinkedHashMap for insertion order ?
    // or should we slap on an Ordered interface for sorting ?
    private final Map<String, ComposeServiceInfo> items = new LinkedHashMap<>();

    public ComposeServiceInfo service(String name) {
        ComposeServiceInfo absent = new ComposeServiceInfo(name);
        ComposeServiceInfo existing = this.items.putIfAbsent(name, absent);
        return existing != null ? existing : absent;
    }

    public ComposeServices putAll(Stream<ComposeServiceInfo> services) {
        this.items.putAll(services.collect(Collectors.toMap(ComposeServiceInfo::name, Function.identity())));
        return this;
    }

    public boolean isEmpty() {
        return this.items.isEmpty();
    }

    public Stream<ComposeServiceInfo> stream() {
        return this.items.values().stream();
    }
    // should we have an add/merge method ?

}
