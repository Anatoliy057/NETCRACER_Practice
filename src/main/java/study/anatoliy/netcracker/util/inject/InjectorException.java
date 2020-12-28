package study.anatoliy.netcracker.util.inject;

/**
 * Parent of all injector exceptions
 *
 * @author Udarczev Anatoliy
 */
public class InjectorException extends Exception {

    public InjectorException() {
    }

    public InjectorException(String message) {
        super(message);
    }
}
