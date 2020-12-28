package study.anatoliy.netcracker.validators;

import org.junit.jupiter.api.Test;
import study.anatoliy.netcracker.domain.client.Client;
import study.anatoliy.netcracker.domain.client.ClientBuilder;
import study.anatoliy.netcracker.domain.client.Gender;
import study.anatoliy.netcracker.domain.contract.*;
import study.anatoliy.netcracker.domain.validation.ValidationMessage;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static study.anatoliy.netcracker.domain.validation.ValidationStatus.*;
import static study.anatoliy.netcracker.validators.Validators.*;

class ValidatorsTest {

    ClientBuilder clientBuilder = new ClientBuilder()
            .setBirthDate(LocalDate.of(2000, 5, 2))
            .setFullName("Ударцев Анатолий Александрович")
            .setPassport("1234 567890")
            .setGender(Gender.MALE)
            .setId(0);

    InternetContractBuilder internetBuilder = new InternetContractBuilder()
            .setMegabits(500)
            .setClient(clientBuilder.build())
            .setStartDate(LocalDate.of(2019, 1, 1))
            .setExpirationDate(LocalDate.of(2020, 1, 1))
            .setId(0);

    MobileContractBuilder mobileBuilder = new MobileContractBuilder()
            .setMegabytes(2048)
            .setMinutes(1000)
            .setSms(100)
            .setClient(clientBuilder.build())
            .setStartDate(LocalDate.of(2016, 1, 1))
            .setExpirationDate(LocalDate.of(2019, 11, 26))
            .setId(1);

    DigitalTVContractBuilder digitalTVBuilder = new DigitalTVContractBuilder()
            .setChannelPackage(ChannelPackage.STANDARD)
            .setClient(clientBuilder.build())
            .setStartDate(LocalDate.of(2019, 9, 28))
            .setExpirationDate(LocalDate.of(2022, 9, 28))
            .setId(2);

    @Test
    public void validatorIdContract_idIsNegative_error() {
        Contract contract = internetBuilder
                .setId(-1)
                .build();

        List<ValidationMessage> messages = validatorIdContract().valid(contract);

        assertEquals(1, messages.size());
        assertEquals(ERROR, messages.get(0).getStatus());
    }

    @Test
    public void validatorIdContract_idIsPositive_noMessages() {
        Contract contract = internetBuilder.
                setId(1)
                .build();

        List<ValidationMessage> messages = validatorIdContract().valid(contract);

        assertEquals(0, messages.size());
    }

    @Test
    public void validatorDatesContract_periodContractIsNegative_error() {
        Contract contract = internetBuilder
                .setStartDate(LocalDate.now())
                .setExpirationDate(LocalDate.now().minusYears(1))
                .build();

        List<ValidationMessage> messages = validatorDatesContract().valid(contract);

        assertEquals(1, messages.size());
        assertEquals(ERROR, messages.get(0).getStatus());
    }

    @Test
    public void validatorDatesContract_birthDateClientEarliestStartDateContract_error() {
        Contract contract = internetBuilder
                .setStartDate(LocalDate.now().minusYears(1000))
                .build();

        List<ValidationMessage> messages = validatorDatesContract().valid(contract);

        assertEquals(1, messages.size());
        assertEquals(ERROR, messages.get(0).getStatus());
    }

    @Test
    public void validatorDatesContract_correctDates_noMessages() {
        Contract contract = internetBuilder.build();

        List<ValidationMessage> messages = validatorDatesContract().valid(contract);

        assertEquals(0, messages.size());
    }

    @Test
    public void validatorInternetContract_speedIsNegative_error() {
        Contract contract = internetBuilder
                .setMegabits(-1)
                .build();

        List<ValidationMessage> messages = validatorInternetContract().valid(contract);

        assertEquals(1, messages.size());
        assertEquals(ERROR, messages.get(0).getStatus());
    }

    @Test
    public void validatorInternetContract_speedIsPositive_noMessage() {
        Contract contract = internetBuilder
                .setMegabits(1)
                .build();

        List<ValidationMessage> messages = validatorInternetContract().valid(contract);

        assertEquals(0, messages.size());
    }

    @Test
    public void validatorMobileContract_specifiedParamsIsNegative_errors() {
        Contract contract = mobileBuilder
                .setMinutes(-1)
                .setSms(-1)
                .setMegabytes(-1)
                .build();

        List<ValidationMessage> messages = validatorMobileContract().valid(contract);

        assertEquals(3, messages.size());
        for (ValidationMessage message :
                messages) {
            assertEquals(ERROR, message.getStatus());
        }
    }

    @Test
    public void validatorMobileContract_specifiedParamsIsCorrect_noMessage() {
        Contract contract = mobileBuilder.build();

        List<ValidationMessage> messages = validatorInternetContract().valid(contract);

        assertEquals(0, messages.size());
    }

    @Test
    public void validatorIdClient_idIsNegative_error() {
        Client client = clientBuilder
                .setId(-1)
                .build();

        List<ValidationMessage> messages = validatorIdClient().valid(client);

        assertEquals(1, messages.size());
        assertEquals(ERROR, messages.get(0).getStatus());
    }

    @Test
    public void validatorIdClient_idIsPositive_noMessages() {
        Client client = clientBuilder.
                setId(1)
                .build();

        List<ValidationMessage> messages = validatorIdClient().valid(client);

        assertEquals(0, messages.size());
    }

    @Test
    public void validatorBirthDateClient_birthDateLatestNow_error() {
        Client client = clientBuilder
                .setBirthDate(LocalDate.now().plusDays(1))
                .build();

        List<ValidationMessage> messages = validatorBirthDateClient().valid(client);

        assertEquals(1, messages.size());
        assertEquals(ERROR, messages.get(0).getStatus());
    }

    @Test
    public void validatorBirthDateClient_oldBirthDate_warn() {
        Client client = clientBuilder
                .setBirthDate(LocalDate.now().minusYears(1000))
                .build();

        List<ValidationMessage> messages = validatorBirthDateClient().valid(client);

        assertEquals(1, messages.size());
        assertEquals(WARN, messages.get(0).getStatus());
    }

    @Test
    public void validatorBirthDateClient_birthDateEarliestNow_noMessage() {
        Client client = clientBuilder
                .setBirthDate(LocalDate.now().minusDays(1))
                .build();

        List<ValidationMessage> messages = validatorBirthDateClient().valid(client);

        assertEquals(0, messages.size());
    }
}