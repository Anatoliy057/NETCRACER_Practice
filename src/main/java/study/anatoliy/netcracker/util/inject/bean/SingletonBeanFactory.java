package study.anatoliy.netcracker.util.inject.bean;

import java.util.function.Supplier;

/**
 * A bean factory that creates a bean once time
 *
 * @see BeanFactory
 *
 * @author Udarczev Anatoliy
 */
public class SingletonBeanFactory implements BeanFactory {

    private Object instance;
    private final Supplier<?> factory;

    public SingletonBeanFactory(Supplier<?> factory) {
        this.factory = factory;
        instance = null;
    }

    @Override
    public Object factory() {
        if (instance == null) {
            instance = factory.get();
        }
        return instance;
    }
}
