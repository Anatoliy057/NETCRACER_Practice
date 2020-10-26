package study.anatoliy.netcracker.domain.client;

import java.time.LocalDate;

public class ClientBuilder {

    private long id;
    private LocalDate birthDate;
    private String fullName;
    private String passport;
    private Gender gender;

    public ClientBuilder setID(long ID) {
        this.id = id;
        return this;
    }

    public ClientBuilder setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public ClientBuilder setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public ClientBuilder setPassport(String passport) {
        this.passport = passport;
        return this;
    }

    public ClientBuilder setGender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public Client build() {
        return new Client(id, birthDate, fullName, passport, gender);
    }
}
