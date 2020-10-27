package study.anatoliy.netcracker.domain.client;

import org.junit.jupiter.api.Test;
import study.anatoliy.netcracker.domain.exception.PeriodException;

import java.time.LocalDate;
import java.time.Period;

import static org.junit.jupiter.api.Assertions.*;

class ClientTest {

    @Test
    void getAge() throws PeriodException {
        LocalDate birthDate = LocalDate.of(2000, 5, 2);
        Client c = new ClientBuilder()
                .setFullName("Ударцев Анатолий Александрович")
                .setID(0)
                .setGender(Gender.MALE)
                .setPassport("1234 567890")
                .setBirthDate(null)
                .build();
        int expectAge = Period.between(birthDate, LocalDate.now()).getYears();

        int age = c.getAge();

        assertEquals(expectAge, age);
    }

    @Test
    void new_birthLaterCurrent_throwPeriodException() {
        LocalDate birthDate = LocalDate.now().plusDays(1);
        ClientBuilder clientBuilder = new ClientBuilder()
                .setFullName("Ударцев Анатолий Александрович")
                .setID(0)
                .setGender(Gender.MALE)
                .setPassport("1234 567890")
                .setBirthDate(birthDate);

        assertThrows(PeriodException.class, clientBuilder::build);
    }

    @Test
    void new_birthIsNull_throwNullPointerException() {
        ClientBuilder clientBuilder = new ClientBuilder()
                .setFullName("Ударцев Анатолий Александрович")
                .setID(0)
                .setGender(Gender.MALE)
                .setPassport("1234 567890")
                .setBirthDate(null);

        assertThrows(NullPointerException.class, clientBuilder::build);
    }
}