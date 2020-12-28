package study.anatoliy.netcracker.validators;

import study.anatoliy.netcracker.domain.client.Client;
import study.anatoliy.netcracker.domain.contract.Contract;
import study.anatoliy.netcracker.domain.contract.InternetContract;
import study.anatoliy.netcracker.domain.contract.MobileContract;
import study.anatoliy.netcracker.domain.validation.ValidationMessage;
import study.anatoliy.netcracker.util.Utils;
import study.anatoliy.netcracker.util.inject.bean.Bean;
import study.anatoliy.netcracker.util.validator.Validator;
import study.anatoliy.netcracker.util.validator.ValidatorSupport;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * List of static methods, that creates validators for client or contract
 *
 * @see Validator
 * @see ValidatorSupport
 *
 * @author Udarczev Anatoliy
 */
public class Validators {

    /**
     * Returns a validator for valid the client's birthday
     *
     * @return client validator
     */
    @Bean
    public static Validator validatorBirthDateClient() {
        return ValidatorSupport.of(Client.class,
                client -> {
                    List<ValidationMessage> messages = new ArrayList<>();

                    if (Utils.periodMoreZero(client.getBirthDate(), LocalDate.now())) {
                        messages.add(ValidationMessage.error("Client cannot be born later than current date"));
                    }

                    if (Utils.periodMoreZero(LocalDate.now().minusYears(130), client.getBirthDate())) {
                        messages.add(ValidationMessage.warn("Client's age exceeds 130 years"));
                    }

                    return messages;
                });
    }

    /**
     * Returns a validator for valid the client id
     *
     * @return client validator
     */
    @Bean
    public static Validator validatorIdClient() {
        return ValidatorSupport.of(Client.class,
                client -> {
                    if (client.getId() < 0) {
                        return Collections.singletonList(ValidationMessage.error("ID can not be negative: " + client.getId()));
                    } else {
                        return Collections.emptyList();
                    }
                });
    }

    /**
     * Returns a validator for valid the contract id
     *
     * @return contract validator
     */
    @Bean
    public static Validator validatorIdContract() {
        return ValidatorSupport.of(Contract.class,
                contract -> {
                    if (contract.getId() < 0) {
                        return Collections.singletonList(ValidationMessage.error("ID can not be negative: " + contract.getId()));
                    } else {
                        return Collections.emptyList();
                    }
                });
    }

    /**
     * Returns a validator for valid the contract start date and end date
     *
     * @return contract validator
     */
    @Bean
    public static Validator validatorDatesContract() {
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

    /**
     * Returns a validator for valid the internet contract
     *
     * @return contract validator
     */
    @Bean
    public static Validator validatorInternetContract() {
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

    /**
     * Returns a validator for valid the mobile contract
     *
     * @return contract validator
     */
    @Bean
    public static Validator validatorMobileContract() {
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

                    if (mobileContract.getMegabytes() < 0) {
                        messages.add(ValidationMessage.error("The number of megabytes cannot be negative"));
                    }

                    return messages;
                });
    }

}
