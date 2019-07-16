package eu.xenit.alfred.initializr.generator.condition;

import io.spring.initializr.generator.project.ResolvedProjectDescription;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Conditional;

/**
 * Condition that matches when a {@link ResolvedProjectDescription} has a requested
 * dependency with a specific facet.
 *
 * A generated project may ultimately reference a different set of dependencies, but
 * those are not considered when evaluating this condition. Only the explicitly
 * requested dependencies are considered in the evaluation.
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD })
@Documented
@Conditional(OnRequestedFacetCondition.class)
public @interface ConditionalOnRequestedFacet {

    /**
     * The facet value of one of the dependencies.
     * @return the facet value
     */
    String value();

}