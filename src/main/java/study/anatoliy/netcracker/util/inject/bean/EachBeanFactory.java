package study.anatoliy.netcracker.util.inject.bean;

import java.util.function.Supplier;

public class EachBeanFactory implements BeanFactory {

    private final Supplier<?> factory;

    public EachBeanFactory(Supplier<?> factory) {
        this.factory = factory;
    }

    @Override
    public Object factory() {
        return factory;
    }
}
