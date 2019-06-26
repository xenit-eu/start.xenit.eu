package eu.xenit.alfred.initializr.generator.xml.beans;

//import static org.xmlunit.assertj.XmlAssert.assertThat;

import static org.assertj.core.api.Assertions.assertThat;

import io.spring.initializr.generator.io.IndentingWriter;
import io.spring.initializr.generator.io.IndentingWriterFactory;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.Properties;
import org.junit.Test;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanReference;
import org.springframework.beans.factory.config.TypedStringValue;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.io.ByteArrayResource;

public class SpringXmlConfigWriterTest {

    private SpringXmlConfigWriter writer = new SpringXmlConfigWriter();
    private IndentingWriterFactory indentingWriterFactory = IndentingWriterFactory.withDefaultSettings();

    @Test
    public void testEmpty() throws IOException {
        String xml = writeSpringBeansXml(new SpringXmlConfig());

        SpringXmlConfigAssert configAssert = new SpringXmlConfigAssert(xml);
        configAssert.hasXmlDeclaration();
        configAssert.hasImportCount(0);
        configAssert.hasBeanDefinitionCount(0);
    }

    @Test
    public void testImports() throws IOException {
        SpringXmlConfig model = new SpringXmlConfig();
        model.addImports("classpath:alfresco/module/${project.artifactId}/context/bootstrap-context.xml",
                "classpath:alfresco/module/${project.artifactId}/context/service-context.xml",
                "classpath:alfresco/module/${project.artifactId}/context/webscript-context.xml");

        String xml = writeSpringBeansXml(model);

        SpringXmlConfigAssert configAssert = new SpringXmlConfigAssert(xml);
        configAssert.hasXmlDeclaration();
        configAssert.hasImportCount(3);
        configAssert.hasBeanDefinitionCount(0);
    }

    @Test
    public void testBeans() throws IOException {
        SpringBeanDefinition bean = new SpringBeanDefinition();
        bean.setId("webscript.alfresco.tutorials.helloworld.get");
        bean.setBeanClassName("eu.xenit.platformsample.HelloWorldWebScript");
        bean.setParentName("webscript");

        SpringXmlConfig model = new SpringXmlConfig();
        model.addBeanDefinition(bean);

        String xml = writeSpringBeansXml(model);

        SpringXmlConfigAssert configAssert = new SpringXmlConfigAssert(xml);
        configAssert.hasXmlDeclaration();
        configAssert.hasImportCount(0);
        configAssert.hasBeanDefinitionCount(1);
    }

    @Test
    public void testComplexBean() throws IOException {
        SpringBeanDefinition bean = new SpringBeanDefinition();
        bean.setId("beanId");
        bean.setBeanClassName("beanClassName");
        bean.setParentName("parentBean");
        bean.setInitMethod("init");

        bean.addProperties(
                SpringBeanProperty.withValue("valuePropertyName", "value"),
                SpringBeanProperty.withReference("referencePropertyName", "reference")
        );

//        <bean id="eu.xenit.DemoComponent" class="eu.xenit.platformsample.DemoComponent" parent="module.baseComponent" >
//            <property name="moduleId" value="test-maven-sdk-platform" />  <!-- See module.properties -->
//            <property name="name" value="DemoComponent" />
//            <property name="description" value="A demonstration component" />
//            <property name="sinceVersion" value="1.0" />
//            <property name="appliesFromVersion" value="0.99" /> <!-- 1.0 would not work here when using SNAPSHOT version in project  -->
//            <property name="nodeService" ref="NodeService" />
//            <property name="nodeLocatorService" ref="nodeLocatorService" />
//        </bean>

        SpringXmlConfig model = new SpringXmlConfig();
        model.addBeanDefinition(bean);

        // write out the spring bean xml
        String xml = writeSpringBeansXml(model);

        // load the xml in a real spring application context for validating
        GenericApplicationContext context = new GenericApplicationContext();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(context);
        int count = reader.loadBeanDefinitions(new ByteArrayResource(xml.getBytes()));
        assertThat(count).isEqualTo(1);

        BeanDefinition beanDefinition = context.getBeanDefinition("beanId");
        assertThat(beanDefinition).isNotNull();

        assertThat(beanDefinition.getBeanClassName()).isEqualTo("beanClassName");
        assertThat(beanDefinition.getParentName()).isEqualTo("parentBean");
        assertThat(beanDefinition.getInitMethodName()).isEqualTo("init");

        MutablePropertyValues properties = beanDefinition.getPropertyValues();
        assertThat(properties).hasSize(2);
        assertThat(properties.get("valuePropertyName"))
                .isInstanceOfSatisfying(TypedStringValue.class, (val) -> assertThat(val.getValue()).isEqualTo("value"));
        assertThat(properties.get("referencePropertyName")).isInstanceOfSatisfying(BeanReference.class, (val) -> {
            assertThat(val.getBeanName()).isEqualTo("reference");
        });
//        beanDefinition.getPropertyValues()
    }

    @Test
    public void testWriteValueProperty() throws IOException {
        String xml = this.writeProperty(SpringBeanProperty.withValue("name", "demo"));

        assertThat(xml).isEqualTo("<property name=\"name\" value=\"demo\" />" + System.lineSeparator());
    }

    @Test
    public void testWriteRefProperty() throws IOException {
        String xml = this.writeProperty(SpringBeanProperty.withReference("name", "someBean"));

        assertThat(xml).isEqualTo("<property name=\"name\" ref=\"someBean\" />" + System.lineSeparator());
    }

    @Test
    public void testWriteListProperty() throws IOException {
        Element list = Element.fromList(
            Element.fromValue("content-model.xml"),
            Element.fromRef("beanRefTest"),
            Element.fromList(Collections.singletonList(Element.fromValue("innerListElement")))
        );

        SpringBeanProperty property = SpringBeanProperty.withElement("name", list);
        String xml = this.writeProperty(property);

        assertThat(xml).isEqualTo(String.join(System.lineSeparator(),
                "<property name=\"name\">",
                "    <list>",
                "        <value>content-model.xml</value>",
                "        <ref bean=\"beanRefTest\" />",
                "        <list>",
                "            <value>innerListElement</value>",
                "        </list>",
                "    </list>",
                "</property>",
                ""
        ));
    }

    @Test
    public void testWritePropsProperty() throws IOException {
        Properties properties = new Properties() {{
            put("key", "value");
        }};
        Element props = Element.fromProperty(properties);

        SpringBeanProperty property = SpringBeanProperty.withElement("name", props);
        String xml = this.writeProperty(property);

        assertThat(xml).isEqualTo(String.join(System.lineSeparator(),
                "<property name=\"name\">",
                "    <props>",
                "        <prop key=\"key\">value</prop>",
                "    </props>",
                "</property>",
                ""
        ));
    }

//    @Test
//    public void testWriteListWithEmbeddedProps() throws IOException {
//        Element list = Element.fromList(
//
//        );
//        SpringBeanProperty property = SpringBeanProperty.withElement("name", list);
//        String xml = this.writeProperty(property);
//
//        assertThat(xml).isEqualTo(String.join(System.lineSeparator(),
//                "<property name=\"name\">",
//                "    <list>",
//                "        <value>content-model.xml</value>",
//                "        <ref bean=\"beanRefTest\" />",
//                "        <list>",
//                "            <value>innerListElement</value>",
//                "        </list>",
//                "    </list>",
//                "</property>",
//                ""
//        ));
////        <property name="workflowDefinitions">
////            <list>
////                <props>
////                    <prop key="engineId">activiti</prop>
////                    <prop key="location">alfresco/module/test-maven-sdk-platform/workflow/sample-process.bpmn20.xml</prop>
////                    <prop key="mimetype">text/xml</prop>
////                </props>
////            </list>
////        </property>
//    }

    private String writeSpringBeansXml(SpringXmlConfig model) throws IOException {
        StringWriter out = new StringWriter();
        try (IndentingWriter writer = createWriter(out)) {
            this.writer.writeTo(writer, model);
        }
        return out.toString();
    }

    private String writeProperty(SpringBeanProperty property) throws IOException {
        StringWriter out = new StringWriter();
        try (IndentingWriter writer = createWriter(out)) {
            this.writer.writeProperty(writer, property);
        }
        return out.toString();
    }


    private IndentingWriter createWriter(StringWriter out) {
        return this.indentingWriterFactory.createIndentingWriter("xml", out);
    }


}