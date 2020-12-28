package study.anatoliy.netcracker.util.inject;

/**
 * The exception thrown when it is impossible to determine what to inject
 *
 * @author Udarczev Anatoliy
 */
public class InjectException extends InjectorException {
    public InjectException() {
    }

    public InjectException(String message) {
        super(message);
    }
}
