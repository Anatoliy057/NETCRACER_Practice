package study.anatoliy.netcracker.util.validator;

import study.anatoliy.netcracker.domain.validation.ValidationMessage;

import java.util.Collections;
import java.util.List;

public interface Validator<T> {

    List<ValidationMessage> doValidate(T o);

    default List<ValidationMessage> valid(T o) {
        return isSuitableClass(o.getClass()) ? doValidate(o) : Collections.emptyList();
    }

    boolean isSuitableClass(Class<?> c);

    Class<? extends T> getClassValidObject();
}
