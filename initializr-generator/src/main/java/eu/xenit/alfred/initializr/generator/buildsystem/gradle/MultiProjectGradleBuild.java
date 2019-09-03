package eu.xenit.alfred.initializr.generator.buildsystem.gradle;

import eu.xenit.alfred.initializr.generator.buildsystem.MultiModuleBuild;
import io.spring.initializr.generator.buildsystem.BuildItemResolver;
import io.spring.initializr.generator.buildsystem.gradle.GradleBuild;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.var;
import org.springframework.util.StringUtils;

public class MultiProjectGradleBuild extends GradleBuild implements MultiModuleBuild<MultiProjectGradleBuild> {

    private final Map<String, MultiProjectGradleBuild> subprojects = new LinkedHashMap<>();
    private final MultiProjectGradleBuild parent;

    protected MultiProjectGradleBuild(@NonNull String name) {
        this(name, null);
    }

    protected MultiProjectGradleBuild(@NonNull String name, BuildItemResolver buildItemResolver) {
        this(name, buildItemResolver, null);
    }

    protected MultiProjectGradleBuild(@NonNull String name, BuildItemResolver buildItemResolver, MultiProjectGradleBuild parent)
    {
        super(buildItemResolver);

        if (StringUtils.isEmpty(name))
        {
            throw new IllegalArgumentException("Parameter name cannot be empty");
        }

        this.name = name;
        this.parent = parent;

        if (parent != null) {
            var result = this.parent.subprojects.putIfAbsent(name, this);
            if (result != null) {
                String exMessage = String.format("Root project {} already contains a module with name {}", parent, name);
                throw new IllegalArgumentException(exMessage);
            }
        }
    }

    public MultiProjectGradleBuild getParent() {
        return this.parent;
    }

    public Map<String, MultiProjectGradleBuild> getModules() {
        return Collections.unmodifiableMap(this.subprojects);
    }

    @Getter @Setter
    private String description;

    @Getter @Setter @NonNull
    private String name;
}
