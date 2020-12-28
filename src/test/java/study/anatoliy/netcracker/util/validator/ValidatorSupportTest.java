package study.anatoliy.netcracker.util.validator;

import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class ValidatorSupportTest {

    @Test
    public void of_createValidatorOfSuperType_validatorFits() {
        class A {}
        class B extends A {}

        Validator validator = ValidatorSupport.of(A.class, o -> Collections.emptyList());

        assertTrue(validator.isSuitableClass(B.class));
    }

}