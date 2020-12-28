package study.anatoliy.netcracker.util.inject.bean;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EachBeanFactoryTest {

    @Test
    public void factory_nothing_returnAlwaysNotSameObject() {
        BeanFactory bf = BeanFactories.get(BeanFactoryType.EACH, Object::new);

        assertNotEquals(bf.factory(), bf.factory());
    }
}