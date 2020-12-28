package study.anatoliy.netcracker.util.inject.bean;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SingletonBeanFactoryTest {

    @Test
    public void factory_nothing_returnAlwaysSameObject() {
        BeanFactory bf = BeanFactories.get(BeanFactoryType.SINGLETON, Object::new);

        assertEquals(bf.factory(), bf.factory());
    }
}