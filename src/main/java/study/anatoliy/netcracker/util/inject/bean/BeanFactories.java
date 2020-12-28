package study.anatoliy.netcracker.util.inject.bean;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Helper class for creating a bean factory
 *
 * @see BeanFactoryType
 * @see BeanFactory
 * @see SingletonBeanFactory
 * @see EachBeanFactory
 *
 * @author Udarczev Anatoliy
 */
public class BeanFactories {

    /**
     * Map of all constructors of bean factories according to their type
     */
    private final static Map<BeanFactoryType, Function<Supplier<?>, BeanFactory>> factories;

    static {
        factories = new HashMap<>();

        factories.put(BeanFactoryType.SINGLETON, SingletonBeanFactory::new);
        factories.put(BeanFactoryType.EACH, EachBeanFactory::new);
    }

    /**
     * Creates a bean factory based on its type and bean creation function
     *
     * @param t type of bean factory
     * @param s bean creation function
     *
     * @return bean factory
     */
    public static BeanFactory get(BeanFactoryType t, Supplier<?> s) {
        return factories.get(t).apply(s);
    }
}
