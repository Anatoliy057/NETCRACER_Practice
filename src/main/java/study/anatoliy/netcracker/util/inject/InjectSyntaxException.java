package study.anatoliy.netcracker.util.inject;

/**
 * The exception thrown when using the Inject or InjectCollection annotation incorrectly
 *
 * @see Inject
 * @see InjectCollection
 *
 * @author Udarczev Anatoliy
 */
public class InjectSyntaxException extends InjectorException {

    public InjectSyntaxException() {
    }

    public InjectSyntaxException(String message) {
        super(message);
    }
}
