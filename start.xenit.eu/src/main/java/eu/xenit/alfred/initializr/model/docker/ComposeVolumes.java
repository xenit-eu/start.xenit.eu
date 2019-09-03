package eu.xenit.alfred.initializr.model.docker;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ComposeVolumes {

    public Map<String, ComposeVolumeInfo> items = new LinkedHashMap<>();


    public boolean isEmpty() {
        return this.items.isEmpty();
    }

    public Stream<ComposeVolumeInfo> stream() {
        return this.items.values().stream();
    }

    public void addAll(Collection<ComposeVolumeInfo> volumes) {
        this.items.putAll(volumes.stream().collect(Collectors.toMap(ComposeVolumeInfo::getName, Function.identity())));
    }

    public static ComposeVolumeInfo volume(String name)
    {
        return new ComposeVolumeInfo(name);
    }
}
