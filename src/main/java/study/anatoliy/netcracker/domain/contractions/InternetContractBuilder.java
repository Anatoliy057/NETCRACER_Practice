package study.anatoliy.netcracker.domain.contractions;

import study.anatoliy.netcracker.domain.client.Client;
import study.anatoliy.netcracker.domain.exception.PeriodException;

import java.time.LocalDate;

/**
 * Wired Internet Contract builder
 *
 * @see InternetContract
 * @author Udarczev Anatoliy
 */
public class InternetContractBuilder {

    private int megabits;
    protected long id;
    protected LocalDate startDate;
    protected LocalDate expirationDate;
    protected Client client;

    public InternetContractBuilder setId(long id) {
        this.id = id;
        return this;
    }

    public InternetContractBuilder setStartDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public InternetContractBuilder setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
        return this;
    }

    public InternetContractBuilder setClient(Client client) {
        this.client = client;
        return this;
    }

    public InternetContractBuilder setMegabits(int megabits) {
        this.megabits = megabits;
        return this;
    }

    public InternetContract build() throws PeriodException {
        return new InternetContract(id, startDate, expirationDate, client, megabits);
    }
}
