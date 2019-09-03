package eu.xenit.alfred.initializr.metadata;

import io.spring.initializr.metadata.*;
import org.springframework.util.StringUtils;

import java.util.Arrays;

public class AlfredInitializrMetadataBuilder {
    private final InitializrMetadataBuilder builder = InitializrMetadataBuilder.create();



    public AlfredInitializrMetadataBuilder() {
    }

    public static AlfredInitializrMetadataBuilder withDefaults() {
        return (new AlfredInitializrMetadataBuilder()).addDefaults();
    }

    public InitializrMetadata build() {
        return this.builder.build();
    }

    private AlfredInitializrMetadataBuilder addDefaults() {
        return this.addDefaultTypes()
                .addDefaultPackagings()
                .addDefaultJavaVersions()
                .addDefaultLanguages()
                .addDefaultAlfrescoVersions()
                .addDefaultDependencies();
    }

    private AlfredInitializrMetadataBuilder addDefaultDependencies() {
        return this
                .addDependencyGroup("Alfresco",
                        Dependencies.Webscripts
                )
                .addDependencyGroup("Dynamic Extensions",
                        Dependencies.DynamicExtensions
                );



        //return this.addDependency()
//        - name: Dynamic Extensions
//        content:
//        - name: Dynamic Extensions
//        id: alfresco-dynamic-extensions-repo
//        groupId: eu.xenit
//        artifactId: alfresco-dynamic-extensions-repo
//        version: 1.7.6.RELEASE
//        description: Rapid development of Alfresco repository extensions in Java.
    }

    private AlfredInitializrMetadataBuilder addDefaultTypes() {
        return this
                .addType("maven-module", "Alfresco Maven SDK", false, "/starter.zip", "maven", "module")
                .addType("gradle-module", "Alfred Gradle SDK", true, "/starter.zip", "gradle", "module");
    }

    private AlfredInitializrMetadataBuilder addDefaultPackagings() {
        return this
                .addPackaging("amp", true)
                .addPackaging("jar", false);
    }

    private AlfredInitializrMetadataBuilder addDefaultJavaVersions() {
        return this
//                .addJavaVersion("1.6", false)
//                .addJavaVersion("1.7", false)
                .addJavaVersion("1.8", true);
    }

    private AlfredInitializrMetadataBuilder addDefaultLanguages() {
        return this.addLanguage("java", "Java", true);
    }

    private AlfredInitializrMetadataBuilder addDefaultAlfrescoVersions() {
        return this
                .addAlfrescoVersion("5.2.4.RELEASE", true);
    }

    private AlfredInitializrMetadataBuilder addAlfrescoVersion(String id, boolean defaultValue) {
        this.builder.withCustomizer((it) -> {
            DefaultMetadataElement element = new DefaultMetadataElement();
            element.setId(id);
            element.setName(id);
            element.setDefault(defaultValue);
            it.getBootVersions().getContent().add(element);
        });
        return this;
    }


    private AlfredInitializrMetadataBuilder addLanguage(String id, String name, boolean defaultValue) {
        this.builder.withCustomizer((it) -> {
            DefaultMetadataElement element = new DefaultMetadataElement();
            element.setId(id);
            element.setName(name);
            element.setDefault(defaultValue);
            it.getLanguages().getContent().add(element);
        });
        return this;
    }

    private AlfredInitializrMetadataBuilder addJavaVersion(String version, boolean defaultValue) {
        this.builder.withCustomizer((it) -> {
            DefaultMetadataElement element = new DefaultMetadataElement();
            element.setId(version);
            element.setName(version);
            element.setDefault(defaultValue);
            it.getJavaVersions().getContent().add(element);
        });
        return this;
    }

    private AlfredInitializrMetadataBuilder addPackaging(String id, boolean defaultValue) {
        this.builder.withCustomizer((it) -> {
            DefaultMetadataElement packaging = new DefaultMetadataElement();
            packaging.setId(id);
            packaging.setName(id);
            packaging.setDefault(defaultValue);
            it.getPackagings().getContent().add(packaging);
        });
        return this;
    }

    private AlfredInitializrMetadataBuilder addType(String id, String name, boolean defaultValue, String action, String build, String format) {
        Type type = new Type();
        type.setId(id);
        type.setName(name);
        type.setDefault(defaultValue);
        type.setAction(action);
        if (StringUtils.hasText(build)) {
            type.getTags().put("build", build);
        }

        if (StringUtils.hasText(format)) {
            type.getTags().put("format", format);
        }

        return this.addType(type);
    }

    private AlfredInitializrMetadataBuilder addType(Type type) {
        this.builder.withCustomizer((it) -> it.getTypes().getContent().add(type));

        return this;
    }

    public AlfredInitializrMetadataBuilder addDependencyGroup(String name, Dependency... dependencies) {
        this.builder.withCustomizer((it) -> {
            DependencyGroup group = new DependencyGroup();
            group.setName(name);
            group.getContent().addAll(Arrays.asList(dependencies));
            it.getDependencies().getContent().add(group);
        });
        return this;
    }
}
