package study.anatoliy.netcracker.util.inject.bean;

import study.anatoliy.netcracker.validators.Validators;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation specifying a class or method as a bean factory
 *
 * @author Udarczev Anatoliy
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface Bean {

    /**
     * @see BeanFactoryType
     *
     * @return bean factory type
     */
    BeanFactoryType value() default BeanFactoryType.SINGLETON;
}
