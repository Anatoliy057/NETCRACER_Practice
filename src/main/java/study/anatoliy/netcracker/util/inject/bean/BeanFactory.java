package study.anatoliy.netcracker.util.inject.bean;

/**
 * Bean factory interface
 *
 * @see SingletonBeanFactory
 * @see EachBeanFactory
 *
 * @author Udarczev Anatoliy
 */
public interface BeanFactory {

    /**
     * @return bean object
     */
    Object factory();

}
