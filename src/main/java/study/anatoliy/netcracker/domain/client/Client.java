package study.anatoliy.netcracker.domain.client;

import java.time.LocalDate;

public class Client {

    private final long id;
    private final LocalDate birthDate;

    private String fullName;
    private String passport;
    private Gender gender;

    public Client(long id, LocalDate birthDate, String fullName, String passport, Gender gender) {
        this.id = id;
        this.birthDate = birthDate;
        this.fullName = fullName;
        this.passport = passport;
        this.gender = gender;
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
}
