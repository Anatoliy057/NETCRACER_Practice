package study.anatoliy.netcracker.util.inject.bean;

import java.util.function.Supplier;

/**
 * A bean factory that creates a new bean every time
 *
 * @see BeanFactory
 *
 * @author Udarczev Anatoliy
 */
public class EachBeanFactory implements BeanFactory {

    private final Supplier<?> factory;

    public EachBeanFactory(Supplier<?> factory) {
        this.factory = factory;
    }

    @Override
    public Object factory() {
        return factory.get();
    }
}
