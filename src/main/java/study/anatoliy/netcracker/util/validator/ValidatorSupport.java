package study.anatoliy.netcracker.util.validator;

import study.anatoliy.netcracker.domain.contract.Contract;
import study.anatoliy.netcracker.domain.validation.ValidationMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ValidatorSupport {

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

    public static <T> List<ValidationMessage> doValidation(T o, List<Validator<T>> validators) {
        List<ValidationMessage> messages = new ArrayList<>();
        for (Validator<T> validator:
                validators) {
            messages.addAll(validator.valid(o));
        }

        return messages;
    }
}
