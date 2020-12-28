package study.anatoliy.netcracker.util.inject.bean;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class BeanFactories {

    private final static Map<BeanFactoryType, Function<Supplier<?>, BeanFactory>> factories;

    static {
        factories = new HashMap<>();

        factories.put(BeanFactoryType.SINGLETON, SingletonBeanFactory::new);
        factories.put(BeanFactoryType.EACH, EachBeanFactory::new);
    }

    public static BeanFactory get(BeanFactoryType t, Supplier<?> s) {
        return factories.get(t).apply(s);
    }
}
