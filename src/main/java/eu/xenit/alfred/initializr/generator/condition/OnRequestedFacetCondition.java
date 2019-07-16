package eu.xenit.alfred.initializr.generator.condition;

import io.spring.initializr.generator.condition.ProjectGenerationCondition;
import io.spring.initializr.generator.project.ResolvedProjectDescription;
import io.spring.initializr.metadata.InitializrMetadata;
import java.util.Map;
import java.util.Objects;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.StringUtils;

/**
 * {@link ProjectGenerationCondition} implementation for {@link ConditionalOnRequestedFacet}.
 */
class OnRequestedFacetCondition extends ProjectGenerationCondition {

    private static String conditionalClassName = ConditionalOnRequestedFacet.class.getName();

    @Override
    protected boolean matches(ResolvedProjectDescription projectDescription, ConditionContext context,
            AnnotatedTypeMetadata metadata) {

        Map<String, Object> annotationAttributes = metadata.getAnnotationAttributes(conditionalClassName);
        Objects.requireNonNull(annotationAttributes, "annotation cannot be null");
        String facet = (String) annotationAttributes.get("value");

        if (StringUtils.isEmpty(facet)) {
            return false;
        }

        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
        Objects.requireNonNull(beanFactory, "beanFactory cannot be null");
        InitializrMetadata initializrMetadata = beanFactory.getBean(InitializrMetadata.class);

        return projectDescription.getRequestedDependencies()
                .keySet().stream()
                .map(dependencyId -> initializrMetadata.getDependencies().get(dependencyId))
                .filter(Objects::nonNull)
                .anyMatch(dependencyMetadata -> dependencyMetadata.getFacets().contains(facet));
    }
}