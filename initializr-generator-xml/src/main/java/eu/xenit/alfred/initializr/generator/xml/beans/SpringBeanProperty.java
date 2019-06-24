package eu.xenit.alfred.initializr.generator.xml.beans;

import lombok.Getter;

public class SpringBeanProperty {

    @Getter
    private final String name;

    @Getter
    private final Element value;

    private SpringBeanProperty(String name, Element value) {

        this.name = name;
        this.value = value;
    }

    public ElementType getType() {
        return this.getValue().getType();
    }

    public static SpringBeanProperty withElement(String name, Element element)
    {
        return new SpringBeanProperty(name, element);
    }

    public static SpringBeanProperty withValue(String name, String value) {
        return new SpringBeanProperty(name, Element.fromValue(value));
    }

    public static SpringBeanProperty withReference(String name, String reference) {
        return new SpringBeanProperty(name, Element.fromRef(reference));
    }




    public String getValueAsString() {
        return this.value.toString();
    }


}