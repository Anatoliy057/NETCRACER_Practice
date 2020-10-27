package study.anatoliy.netcracker.domain.client;

import study.anatoliy.netcracker.domain.exception.PeriodException;
import study.anatoliy.netcracker.util.Checks;

import java.time.LocalDate;
import java.util.Objects;

public class Client {

    private final long id;
    private final LocalDate birthDate;

    private String fullName;
    private String passport;
    private Gender gender;

    public Client(long id, LocalDate birthDate, String fullName, String passport, Gender gender) throws PeriodException {
        if (id < 0) {
            throw new IllegalArgumentException("ID can not be negative: " + id);
        }
        this.id = id;
        this.birthDate = Objects.requireNonNull(birthDate);
        this.fullName = Objects.requireNonNull(fullName);
        this.passport = Objects.requireNonNull(passport);
        this.gender = Objects.requireNonNull(gender);
        checkBirth();
    }

    public int getAge() {
        return LocalDate.now().minusYears(birthDate.getYear()).getYear();
    }

    public long getId() {
        return id;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    private void checkBirth() throws PeriodException {
        Checks.periodMoreZero(birthDate, LocalDate.now(), "Client cannot be born later than current date");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return id == client.id &&
                birthDate.equals(client.birthDate) &&
                fullName.equals(client.fullName) &&
                passport.equals(client.passport) &&
                gender == client.gender;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, birthDate, fullName, passport, gender);
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", birthDate=" + birthDate +
                ", fullName='" + fullName + '\'' +
                ", passport='" + passport + '\'' +
                ", gender=" + gender +
                '}';
    }
}
