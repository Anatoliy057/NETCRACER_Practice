package study.anatoliy.netcracker.util.inject;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation that indicate for injector what to inject
 *
 * @author Udarczev Anatoliy
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Inject {
    /**
     * @return the class of the bean being injected, the default will be the field type
     */
    Class<?> value() default Null.class;
}
