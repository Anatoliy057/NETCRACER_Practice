package study.anatoliy.netcracker.validators;

import study.anatoliy.netcracker.domain.client.Client;
import study.anatoliy.netcracker.domain.validation.ValidationMessage;
import study.anatoliy.netcracker.util.validator.Validator;
import study.anatoliy.netcracker.util.validator.ValidatorSupport;

import java.util.ArrayList;
import java.util.List;

import static study.anatoliy.netcracker.validators.Validators.*;

public class ClientValidator {

    private final static ClientValidator INSTANCE = new ClientValidator();

    public static ClientValidator getInstance() {
        return INSTANCE;
    }

    private List<Validator<Client>> validators;

    private ClientValidator() {
        validators = new ArrayList<>();
    }

    public void addValidator(Validator<Client> validator) {
        validators.add(validator);
    }

    public List<ValidationMessage> doValidation(Client client) {
        return ValidatorSupport.doValidation(client, validators);
    }

    static {
        ClientValidator validator = getInstance();

        validator.addValidator(validatorIdClient());
        validator.addValidator(validatorBirthDateClient());
    }
}
