package eu.xenit.alfred.initializr.generator.build.gradle;

import eu.xenit.alfred.initializr.generator.build.MultiModuleBuild;
import io.spring.initializr.generator.buildsystem.BuildItemResolver;
import io.spring.initializr.generator.buildsystem.gradle.GradleBuild;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

public class MultiProjectGradleBuild extends GradleBuild implements MultiModuleBuild<MultiProjectGradleBuild> {

    private final Map<String, MultiProjectGradleBuild> subprojects = new LinkedHashMap<>();
    private final BuildItemResolver buildItemResolver;
    private final MultiProjectGradleBuild parent;

    public MultiProjectGradleBuild(BuildItemResolver buildItemResolver) {
        this(buildItemResolver, null);
    }

    public MultiProjectGradleBuild() {
        this(null);
    }

    private MultiProjectGradleBuild(BuildItemResolver buildItemResolver, MultiProjectGradleBuild parent)
    {
        super(buildItemResolver);

        this.buildItemResolver = buildItemResolver;
        this.parent = parent;
    }

    public MultiProjectGradleBuild module(String name) {
        return this.subprojects.computeIfAbsent(name, k -> new MultiProjectGradleBuild(this.buildItemResolver, this));
    }

    public MultiProjectGradleBuild getParent() {
        return this.parent;
    }

    public Map<String, MultiProjectGradleBuild> getModules() {
        return Collections.unmodifiableMap(this.subprojects);
    }

    @Getter @Setter
    private String description;
}
