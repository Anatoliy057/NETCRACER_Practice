package study.anatoliy.netcracker.domain.client;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Period;

import static org.junit.jupiter.api.Assertions.*;

class ClientTest {

    @Test
    void getAge() {
        LocalDate birthDate = LocalDate.of(2000, 5, 2);
        Client c = new ClientBuilder()
                .setFullName("Ударцев Анатолий Александрович")
                .setID(0)
                .setGender(Gender.MALE)
                .setPassport("1234 567890")
                .setBirthDate(birthDate)
                .build();
        int expectAge = Period.between(birthDate, LocalDate.now()).getYears();

        int age = c.getAge();

        assertEquals(expectAge, age);
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