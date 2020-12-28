package study.anatoliy.netcracker.util.inject;

import org.junit.jupiter.api.Test;
import study.anatoliy.netcracker.util.inject.bean.Bean;
import study.anatoliy.netcracker.util.inject.bean.BeanFactoryType;
import study.anatoliy.netcracker.util.inject.bean.SyntaxBeanException;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class InjectorTest {

    @Test
    public void scanClass_emptyClass_nothingHappened() throws SyntaxBeanException {
        Injector injector = new Injector();
        class Test {}

        injector.scanClass(Test.class);
    }

    static abstract class TestBean {}
    @Bean
    static class TestBeanSingleton extends TestBean {
        public TestBeanSingleton() {}
    }
    @Bean(BeanFactoryType.EACH)
    static class TestBeanEach extends TestBean {
        public TestBeanEach() {}
    }
    static class TestInjectSingleton {@Inject TestBeanSingleton bean;}
    static class TestInjectEach {@Inject TestBeanEach bean;}
    static class TestInjectCollection {@InjectCollection Collection<TestBean> beans;}

    @Test
    public void inject_beanTypeSingleton_classInstanceInjection() throws SyntaxBeanException, InjectException, InjectSyntaxException {
        Injector injector = new Injector();
        TestInjectSingleton test = new TestInjectSingleton();

        injector.scanClass(TestBeanSingleton.class);
        injector.inject(test);

        assertNotNull(test.bean);
    }

    @Test
    public void inject_beanTypeEach_notSameBeans() throws SyntaxBeanException, InjectException, InjectSyntaxException {
        Injector injector = new Injector();
        TestInjectEach test1 = new TestInjectEach();
        TestInjectEach test2 = new TestInjectEach();

        injector.scanClass(TestBeanEach.class);
        injector.inject(test1);
        injector.inject(test2);

        assertNotNull(test1.bean);
        assertNotNull(test2.bean);
        assertNotEquals(test1.bean, test2.bean);
    }

    @Test
    public void inject_beans_allWillBeInjected() throws SyntaxBeanException, InjectException, InjectSyntaxException {
        Injector injector = new Injector();
        TestInjectCollection test = new TestInjectCollection();

        injector.scanClass(TestBeanSingleton.class);
        injector.scanClass(TestBeanEach.class);
        injector.inject(test);

        assertNotNull(test.beans);
        assertEquals(2, test.beans.size());
    }
}