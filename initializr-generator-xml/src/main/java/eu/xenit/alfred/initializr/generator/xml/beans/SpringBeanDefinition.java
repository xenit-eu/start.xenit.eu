package eu.xenit.alfred.initializr.generator.xml.beans;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.Data;
import lombok.NonNull;

@Data
public class SpringBeanDefinition {

    private String id;
    private String beanClassName;
    private String parentName;
    private String initMethod;

    private List<SpringBeanProperty> properties = new ArrayList<>();

    public void addProperty(@NonNull SpringBeanProperty property) {
        this.properties.add(property);
    }

    public void addProperties(SpringBeanProperty... properties) {
        this.properties.addAll(Arrays.asList(properties));
    }

}
