package study.anatoliy.netcracker.domain.contract;

import org.junit.jupiter.api.Test;
import study.anatoliy.netcracker.domain.client.Client;
import study.anatoliy.netcracker.domain.client.ClientBuilder;
import study.anatoliy.netcracker.domain.client.Gender;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ContractTest {

    Client client = new ClientBuilder()
            .setBirthDate(LocalDate.of(2000, 5, 2))
            .setFullName("Ударцев Анатолий Александрович")
            .setPassport("1234 567890")
            .setGender(Gender.MALE)
            .setID(0)
            .build();

    ContractTest() {
    }

    @Test
    public void new_clientIsNull_throwNullPointerException() {
        InternetContractBuilder builder = new InternetContractBuilder()
                .setMegabits(500)
                .setClient(null)
                .setStartDate(LocalDate.of(2019, 1, 1))
                .setExpirationDate(LocalDate.of(2020, 1, 1))
                .setId(0);

        assertThrows(NullPointerException.class, builder::build);
    }

}