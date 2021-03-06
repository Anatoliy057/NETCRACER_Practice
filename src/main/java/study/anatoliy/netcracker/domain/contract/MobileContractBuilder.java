package study.anatoliy.netcracker.domain.contract;

import study.anatoliy.netcracker.domain.client.Client;

import java.time.LocalDate;

/**
 * Mobile communication contract builder
 *
 * @see MobileContract
 * @author Udarczev Anatoliy
 */
public class MobileContractBuilder {

    private int minutes;
    private int sms;
    private int megabytes;
    protected long id;
    protected LocalDate startDate;
    protected LocalDate expirationDate;
    protected Client client;

    public MobileContractBuilder setId(long id) {
        this.id = id;
        return this;
    }

    public MobileContractBuilder setStartDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public MobileContractBuilder setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
        return this;
    }

    public MobileContractBuilder setClient(Client client) {
        this.client = client;
        return this;
    }

    public MobileContractBuilder setMinutes(int minutes) {
        this.minutes = minutes;
        return this;
    }

    public MobileContractBuilder setSms(int sms) {
        this.sms = sms;
        return this;
    }

    public MobileContractBuilder setMegabytes(int megabytes) {
        this.megabytes = megabytes;
        return this;
    }

    public MobileContract build() {
        return new MobileContract(id, startDate, expirationDate, client, minutes, sms, megabytes);
    }
}
