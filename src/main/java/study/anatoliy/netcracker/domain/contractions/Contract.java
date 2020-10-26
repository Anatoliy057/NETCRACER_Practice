package study.anatoliy.netcracker.domain.contractions;

import study.anatoliy.netcracker.domain.client.Client;

import java.time.LocalDate;

public abstract class Contract {

    protected final long id;

    protected final LocalDate startDate;
    protected LocalDate expirationDate;

    protected final Client client;

    public Contract(long id, LocalDate startDate, LocalDate expirationDate, Client client) {
        this.id = id;
        this.startDate = startDate;
        this.expirationDate = expirationDate;
        this.client = client;
    }

    public abstract TypeContract getType();

    public long getId() {
        return id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Client getClient() {
        return client;
    }
}
