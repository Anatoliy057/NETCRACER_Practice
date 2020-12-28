package study.anatoliy.netcracker.util.inject;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotations indicating to the injector what type of beans to inject into the collection
 *
 * @author Udarczev Anatoliy
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface InjectCollection {
}
