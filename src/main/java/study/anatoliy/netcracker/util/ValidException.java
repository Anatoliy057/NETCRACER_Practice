package study.anatoliy.netcracker.util;

/**
 * ValidException throw when object validation is failed
 *
 * @see Validator#valid(Object)
 * @author Udarczev Anatoliy
 */
public class ValidException extends Exception{

    public ValidException(String message) {
        super(message);
    }

    public ValidException(String message, Throwable cause) {
        super(message, cause);
    }
}
