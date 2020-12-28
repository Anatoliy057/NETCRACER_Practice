package study.anatoliy.netcracker.util.inject.bean;

import study.anatoliy.netcracker.util.inject.bean.BeanFactory;

import java.util.function.Supplier;

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
