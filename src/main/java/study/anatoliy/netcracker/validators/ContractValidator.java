package study.anatoliy.netcracker.validators;

import study.anatoliy.netcracker.domain.contract.Contract;
import study.anatoliy.netcracker.domain.validation.ValidationMessage;
import study.anatoliy.netcracker.util.validator.Validator;
import study.anatoliy.netcracker.util.validator.ValidatorSupport;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static study.anatoliy.netcracker.validators.Validators.*;

/**
 * Class that stores contract validators (singleton)
 *
 * @see Validators
 *
 * @author Udarczev Anatoliy
 */
public class ContractValidator {

    private final static ContractValidator INSTANCE = new ContractValidator();

    public static ContractValidator getInstance() {
        return INSTANCE;
    }

    private final List<Validator<Contract>> validators;

    private ContractValidator() {
        validators = new ArrayList<>();
    }

    public void addValidator(Validator<Contract> validator) {
        validators.add(validator);
    }

    public Collection<ValidationMessage> doValidation(Contract contract) {
        return ValidatorSupport.doValidation(contract, validators);
    }

    static {
        ContractValidator validator = getInstance();

        validator.addValidator(validatorIdContract());
        validator.addValidator(validatorDatesContract());
        validator.addValidator(validatorInternetContract());
        validator.addValidator(validatorMobileContract());
    }

}
