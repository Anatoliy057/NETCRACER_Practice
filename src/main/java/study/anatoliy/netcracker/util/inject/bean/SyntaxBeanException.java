package study.anatoliy.netcracker.util.inject.bean;

import study.anatoliy.netcracker.util.inject.InjectorException;

/**
 * The exception thrown when using the Bean annotation incorrectly
 *
 * @see Bean
 *
 * @author Udarczev Anatoliy
 */
public class SyntaxBeanException extends InjectorException {

    public SyntaxBeanException(String message) {
        super(message);
    }
}
