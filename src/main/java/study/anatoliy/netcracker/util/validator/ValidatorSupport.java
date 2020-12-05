package study.anatoliy.netcracker.util.validator;

import study.anatoliy.netcracker.domain.validation.ValidationMessage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

/**
 * ValidatorSupport makes it easier to create a validator and validate an object across multiple validators
 *
 * @see Validator
 *
 * @author Udarczev Anatoliy
 */
public class ValidatorSupport {

    /**
     * Wraps the specified class and validation function in a validator
     *
     * @param clazz class of objects that are suitable for validation
     * @param f validation function
     * @param <T> type of the object being validated
     * @return validator
     */
    public static <T> Validator<T> of(Class<? extends T> clazz, Function<T, List<ValidationMessage>> f) {
        return new Validator<T>() {
            @Override
            public List<ValidationMessage> doValidate(T o) {
                return f.apply(o);
            }

            @Override
            public boolean isSuitableClass(Class<?> c) {
                return clazz.isAssignableFrom(c);
            }

            @Override
            public Class<? extends T> getClassValidObject() {
                return clazz;
            }
        };
    }

    /**
     * Validates an object against a collection of validators
     *
     * @param o validated object
     * @param validators validators collection
     * @param <T> type validated object
     * @return collection of validation messages
     */
    public static <T> List<ValidationMessage> doValidation(T o, Collection<Validator<T>> validators) {
        List<ValidationMessage> messages = new ArrayList<>();
        for (Validator<T> validator:
                validators) {
            messages.addAll(validator.valid(o));
        }

        return messages;
    }
}
