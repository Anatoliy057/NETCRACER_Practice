package study.anatoliy.netcracker.util.validator;

import study.anatoliy.netcracker.domain.validation.ValidationMessage;
import study.anatoliy.netcracker.validators.Validators;

import java.util.Collections;
import java.util.List;

/**
 * An interface that determines the ability of an object to validate
 *
 * @see Validators
 *
 * @author Udarczev Anatoliy
 */
public interface Validator {

    /**
     * Validation without checking the class of the object being viewed
     *
     * @param o validated object
     * @return collection of validation messages
     */
    List<ValidationMessage> doValidate(Object o);

    /**
     * Before validation, it checks the compliance of the object with the specified class,
     * if false then returns an empty list
     *
     * @param o validated object
     * @return collection of validation messages
     */
    default List<ValidationMessage> valid(Object o) {
        return isSuitableClass(o.getClass()) ? doValidate(o) : Collections.emptyList();
    }

    /**
     * Checks if an object of a given class can be validated
     *
     * @param c class validated object
     * @return can the given object validate
     */
    boolean isSuitableClass(Class<?> c);

    /**
     * Returns the specified validator class
     *
     * @return class type that can be validated
     */
    Class<?> getClassValidObject();
}
