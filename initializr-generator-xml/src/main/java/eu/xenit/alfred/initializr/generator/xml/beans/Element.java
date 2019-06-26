package eu.xenit.alfred.initializr.generator.xml.beans;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Represents an xml bean element - can be basically anything
 *
 * <xsd:element ref="bean"/>
 * <xsd:element ref="ref"/>
 * <xsd:element ref="idref"/>
 * <xsd:element ref="value"/>
 * <xsd:element ref="null"/>
 * <xsd:element ref="array"/>
 * <xsd:element ref="elements"/>
 * <xsd:element ref="set"/>
 * <xsd:element ref="map"/>
 * <xsd:element ref="props"/>
 */
public abstract class Element {

    private Element() {

    }

    public abstract ElementType getType();

    public static ValueElement fromValue(String value){
        return new ValueElement(value);
    }

    public static RefElement fromRef(String reference){
        return new RefElement(reference);
    }

    public static ListElement fromList(Collection<Element> collection)
    {
        return new ListElement(collection);
    }

    public static ListElement fromList(Element... elements) {
        return fromList(Arrays.asList(elements));
    }

    public static Element fromProperty(Properties properties) {
        return new PropsElement(properties);
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    public static class ValueElement extends Element {

        @Getter
        private final String value;

        @Override
        public ElementType getType() {
            return ElementType.VALUE;
        }

        @Override
        public String toString() {
            return this.getValue();
        }
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    static class RefElement extends Element {

        @Getter
        private final String reference;

        @Override
        public ElementType getType() {
            return ElementType.REF;
        }

        @Override
        public String toString() {
            return this.getReference();
        }
    }

    public static class ListElement extends Element {

        @Getter
        private final List<Element> elements;

        private ListElement(Collection<Element> collection)
        {
            this.elements = Collections.unmodifiableList(new ArrayList<>(collection));
        }

        @Override
        public ElementType getType() {
            return ElementType.LIST;
        }
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    public static class PropsElement extends Element {

        @Getter
        private final Properties properties;

        @Override
        public ElementType getType() {
            return ElementType.PROPS;
        }
    }
}
