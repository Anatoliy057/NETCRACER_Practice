package study.anatoliy.netcracker.util;

/**
 * The interface provides the ability to validate objects
 *
 * @param <T> the type of the checked argument
 *
 * @author Udarczev Anatoliy
 */
public interface Validator<T> {


    /**
     * Check the validity of the passed argument
     *
     * @param a the argument being checked
     * @throws ValidException if validation is failed
     */
    void valid(T a) throws ValidException;
}
