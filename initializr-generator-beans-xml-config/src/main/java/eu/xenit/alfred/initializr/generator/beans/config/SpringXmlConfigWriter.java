package eu.xenit.alfred.initializr.generator.beans.config;

import eu.xenit.alfred.initializr.generator.beans.config.SpringXmlConfig.ResourceImport;
import eu.xenit.alfred.initializr.generator.beans.config.model.Element;
import eu.xenit.alfred.initializr.generator.beans.config.model.Element.ListElement;
import eu.xenit.alfred.initializr.generator.beans.config.model.Element.PropsElement;
import eu.xenit.alfred.initializr.generator.beans.config.model.SpringBeanDefinition;
import eu.xenit.alfred.initializr.generator.beans.config.model.SpringBeanProperty;
import io.spring.initializr.generator.io.IndentingWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;

public class SpringXmlConfigWriter {

    public void writeTo(IndentingWriter writer, SpringXmlConfig model) throws IOException {
        this.writeXmlConfig(writer, () -> {
            this.writeImports(writer, model.getImports());
            this.writeSpringBeans(writer, model.getBeanDefinitions());
        });

    }

    private void writeImports(IndentingWriter writer, List<ResourceImport> imports) {
        if (!imports.isEmpty()) {
            writer.println();
            writeCollection(writer, imports, this::writeImport);
        }

    }

    private void writeImport(IndentingWriter writer, ResourceImport resourceImport) {
        this.writeXmlElement(writer, "import", () -> {
            writeAttribute(writer, "resource", resourceImport.getResource());
        }, null);
    }


    private void writeSpringBeans(IndentingWriter writer, List<SpringBeanDefinition> beans) {
        writeCollection(writer, beans, this::writeBean);
    }

    private void writeBean(IndentingWriter writer, SpringBeanDefinition bean) {
        writer.println();
        this.writeXmlElement(writer, "bean", () -> {
                    this.writeAttribute(writer, "id", bean.getId());
                    this.writeAttribute(writer, "class", bean.getBeanClassName());
                    this.writeAttribute(writer, "parent", bean.getParentName());
                    this.writeAttribute(writer, "init-method", bean.getInitMethod());
                },
                () -> {
                    // TODO constructor args
                    this.writeCollection(writer, bean.getProperties(), this::writeProperty);
                });
    }

    void writeProperty(IndentingWriter writer, SpringBeanProperty property) {
        writer.print("<property");
        this.writeAttribute(writer, "name", property.getName());

        switch (property.getType()) {
            case REF:
            case VALUE:
                this.writeAttribute(writer, property.getType().name().toLowerCase(), property.getValueAsString());
                writer.println(" />");
                break;
            case LIST:
                writer.println(">");
                writer.indented(() -> this.writeSpringElement(writer, property.getValue()));
                writer.println("</property>");
                break;
            case PROPS:
                writer.println(">");
                writer.indented(() -> this.writeSpringElement(writer, property.getValue()));
                writer.println("</property>");
                break;
            default:
                throw new UnsupportedOperationException("Property type not supported: "+property.getType());
        }
    }

    void writeSpringElement(IndentingWriter writer, Element element) {
        switch (element.getType()) {
            case REF:
                this.writeXmlElement(writer, "ref", () -> {
                    this.writeAttribute(writer, "bean", element.toString());
                }, null);
                break;
            case VALUE:
                this.writeXmlElement(writer, "value", null, () -> writer.print(element.toString()));
                break;
            case LIST:
                writer.print("<list>");
                writer.indented(() -> {
                    ListElement list = (ListElement) element;
                    this.writeCollection(writer, list.getElements(), this::writeSpringElement);
                });
                writer.println("</list>");
                break;
            case PROPS:
                writer.println("<props>");
                writer.indented(() -> {
                    PropsElement props = (PropsElement) element;
                    props.getProperties().forEach((key, value) -> {
                        this.writeXmlElement(writer, "prop", () -> {
                            this.writeAttribute(writer, "key", key.toString());
                        }, () -> writer.print(value.toString()));
                    });
                });
                writer.println("</props>");
                break;
            default:
                throw new UnsupportedOperationException("Element type not supported: "+element.getType());
        }
    }


    private void writeXmlConfig(IndentingWriter writer, Runnable whenWritten) {
        writer.println("<?xml version='1.0' encoding='UTF-8'?>");
        writer.println("<beans xmlns=\"http://www.springframework.org/schema/beans\"");
        writer.println("       xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"");
        writer.println(
                "       xsi:schemaLocation=\"http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-3.0.xsd\">");

        writer.indented(whenWritten);

        writer.println();
        writer.println("</beans>");
    }

    private void writeXmlElement(IndentingWriter writer, String name, Runnable withAttributes, Runnable withContent) {
        writer.print(String.format("<%s", name));

        if (withAttributes != null) {
            withAttributes.run();
        }

        if (withContent == null) {
            writer.println(" />");
        } else {
            writer.print(">");
            writer.indented(withContent);
            writer.println(String.format("</%s>", name));
        }

    }

    private <T> void writeCollection(IndentingWriter writer, Collection<T> collection,
            BiConsumer<IndentingWriter, T> itemWriter) {

        if (!collection.isEmpty()) writer.println();
        collection.forEach((item) -> itemWriter.accept(writer, item));
    }

    private void writeAttribute(IndentingWriter writer, String name, String value) {
        if (value != null) {
            writer.print(String.format(" %s=\"%s\"", name, value));
        }
    }

}
