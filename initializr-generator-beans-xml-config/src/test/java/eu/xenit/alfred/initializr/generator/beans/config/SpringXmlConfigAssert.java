package eu.xenit.alfred.initializr.generator.beans.config;

import static org.assertj.core.api.Assertions.assertThat;

import io.spring.initializr.generator.test.assertj.NodeAssert;

public class SpringXmlConfigAssert {

    private final String content;

    private final NodeAssert xml;

    public SpringXmlConfigAssert(String content) {
        this.content = content;
        this.xml = new NodeAssert(content);
    }

    public SpringXmlConfigAssert hasXmlDeclaration() {
        assertThat(this.content).startsWith("<?xml version='1.0' encoding='UTF-8'?>");
        return this;
    }

    public SpringXmlConfigAssert hasImportCount(int count) {
        assertThat(this.xml).nodesAtPath("/beans/import").hasSize(count);
        return this;
    }

    public SpringXmlConfigAssert hasBeanDefinitionCount(int count) {
        assertThat(this.xml).nodesAtPath("/beans/bean").hasSize(count);
        return this;
    }
}
