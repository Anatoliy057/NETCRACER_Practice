package study.anatoliy.netcracker.domain.client;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

/**
 * Client entity class
 *
 * @author Udarczev Anatoliy
 */
public class Client {

    /** ID of client */
    private final long id;
    /** Birthday */
    private final LocalDate birthDate;

    /** Contains first name, last name, [patronymic] */
    private String fullName;
    /** Passport series and number */
    private String passport;
    /** Client gender */
    private Gender gender;

    /**
     * @throws NullPointerException if one of the parameters is null
     */
    public Client(long id, LocalDate birthDate, String fullName, String passport, Gender gender) {
        this.id = id;
        this.birthDate = Objects.requireNonNull(birthDate);
        this.fullName = Objects.requireNonNull(fullName);
        this.passport = Objects.requireNonNull(passport);
        this.gender = Objects.requireNonNull(gender);
    }


    /**
     * @return the current age of the client
     */
    public int getAge() {
        return Period.between(birthDate, LocalDate.now()).getYears();
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
