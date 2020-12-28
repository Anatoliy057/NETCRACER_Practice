package study.anatoliy.netcracker.util.inject.bean;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BeanFactoriesTest {

    @Test
    public void get_giveEachType_returnEachBeanFactory() {
        BeanFactory bf = BeanFactories.get(BeanFactoryType.EACH, () -> null);

        assertEquals(bf.getClass(), EachBeanFactory.class);
        assertNull(bf.factory());
    }

    @Test
    public void get_giveSingletonType_returnEachBeanFactory() {
        BeanFactory bf = BeanFactories.get(BeanFactoryType.SINGLETON, () -> null);

        assertEquals(bf.getClass(), SingletonBeanFactory.class);
        assertNull(bf.factory());
    }

}