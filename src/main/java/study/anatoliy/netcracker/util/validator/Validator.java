package study.anatoliy.netcracker.util.validator;

import study.anatoliy.netcracker.domain.validation.ValidationMessage;

import java.util.Collections;
import java.util.List;

public interface Validator<T> {

    List<ValidationMessage> doValidate(T o);

    default List<ValidationMessage> valid(T o) {
        return isSuitableClass(o) ? doValidate(o) : Collections.emptyList();
    }

    boolean isSuitableClass(Object o);

    Class<? extends T> getClassValidObject();
}
