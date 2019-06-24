package eu.xenit.alfred.initializr.generator.xml.beans;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.GenericBeanDefinition;

public class SpringXmlConfig {

    private List<ResourceImport> imports = new ArrayList<>();
    private List<SpringBeanDefinition> beans = new ArrayList<>();

    public void addImport(ResourceImport resource)
    {
        this.imports.add(resource);
    }

    public void addImport(String resource) {
        imports.add(new ResourceImport(resource));
    }

    public void addImports(String... resources) {
        this.imports.addAll(Arrays.stream(resources).map(ResourceImport::new).collect(Collectors.toList()));
    }

    public List<ResourceImport> getImports() {
        return Collections.unmodifiableList(this.imports);
    }

    public void addBeanDefinition(SpringBeanDefinition beanDefinition) {
        this.beans.add(beanDefinition);
    }

    public void addBeanDefinitions(Collection<? extends SpringBeanDefinition> beanDefinitions) {
        this.beans.addAll(beanDefinitions);
    }

    public List<SpringBeanDefinition> getBeanDefinitions() {
        return this.beans;
    }


    public class ResourceImport {

        private final String resource;

        ResourceImport(String resource) {
            this.resource = resource;
        }

        public String getResource() {
            return this.resource;
        }
    }

}
