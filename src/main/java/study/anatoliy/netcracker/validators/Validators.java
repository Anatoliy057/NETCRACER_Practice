package study.anatoliy.netcracker.validators;

import study.anatoliy.netcracker.domain.client.Client;
import study.anatoliy.netcracker.domain.contract.Contract;
import study.anatoliy.netcracker.domain.contract.InternetContract;
import study.anatoliy.netcracker.domain.contract.MobileContract;
import study.anatoliy.netcracker.domain.validation.ValidationMessage;
import study.anatoliy.netcracker.util.Utils;
import study.anatoliy.netcracker.util.validator.Validator;
import study.anatoliy.netcracker.util.validator.ValidatorSupport;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Validators {

    public static Validator<Client> validatorBirthDateClient() {
        return ValidatorSupport.of(Client.class,
                client -> {
                    List<ValidationMessage> messages = new ArrayList<>();

                    if (Utils.periodMoreZero(client.getBirthDate(), LocalDate.now())) {
                        messages.add(ValidationMessage.error("Client cannot be born later than current date"));
                    }

                    if (Utils.periodMoreZero(LocalDate.now().minusYears(130), client.getBirthDate())) {
                        messages.add(ValidationMessage.error("Client's age exceeds 130 years"));
                    }

                    return messages;
                });
    }

    public static Validator<Client> validatorIdClient() {
        return ValidatorSupport.of(Client.class,
                client -> {
                    if (client.getId() < 0) {
                        return Collections.singletonList(ValidationMessage.error("ID can not be negative: " + client.getId()));
                    } else {
                        return Collections.emptyList();
                    }
                });
    }

    public static Validator<Contract> validatorIdContract() {
        return ValidatorSupport.of(Contract.class,
                contract -> {
                    if (contract.getId() < 0) {
                        return Collections.singletonList(ValidationMessage.error("ID can not be negative: " + contract.getId()));
                    } else {
                        return Collections.emptyList();
                    }
                });
    }

    public static Validator<Contract> validatorDatesContract() {
        return ValidatorSupport.of(Contract.class,
                contract -> {
                    List<ValidationMessage> messages = new ArrayList<>();

                    if (Utils.periodMoreZero(contract.getClient().getBirthDate(), contract.getStartDate())) {
                        messages.add(ValidationMessage.error("The client cannot conclude a contract because it does not exist yet"));
                    }

                    if (contract.getExpirationDate() != null && Utils.periodMoreZero(contract.getStartDate(), contract.getExpirationDate())) {
                        messages.add(ValidationMessage.error("The end date of contract is earlier than or equals its start"));
                    }

                    return messages;
                });
    }

    public static Validator<Contract> validatorInternetContract() {
        return ValidatorSupport.of(InternetContract.class,
                contract -> {
                    InternetContract internetContract = (InternetContract) contract;

                    if (internetContract.getMegabits() < 0) {
                        return Collections.singletonList(ValidationMessage.error("Internet speed cannot be negative"));
                    } else {
                        return Collections.emptyList();
                    }
                });
    }

    public static Validator<Contract> validatorMobileContract() {
        return ValidatorSupport.of(MobileContract.class,
                contract -> {
                    MobileContract mobileContract = (MobileContract) contract;
                    List<ValidationMessage> messages = new ArrayList<>();

                    if (mobileContract.getMinutes() < 0) {
                        messages.add(ValidationMessage.error("The number of minutes cannot be negative"));
                    }

                    if (mobileContract.getSms() < 0) {
                        messages.add(ValidationMessage.error("The number of sms cannot be negative"));
                    }

                    if (mobileContract.getGigabytes() < 0) {
                        messages.add(ValidationMessage.error("The number of megabytes cannot be negative"));
                    }

                    return messages;
                });
    }

}
