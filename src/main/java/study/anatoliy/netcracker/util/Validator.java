package study.anatoliy.netcracker.util;

public interface Validator<T> {

    void valid(T a) throws ValidException;
}
