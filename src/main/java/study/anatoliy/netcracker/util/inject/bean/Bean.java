package study.anatoliy.netcracker.util.inject.bean;

import study.anatoliy.netcracker.util.inject.bean.BeanFactoryType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface Bean {
    BeanFactoryType value() default BeanFactoryType.SINGLETON;
}
