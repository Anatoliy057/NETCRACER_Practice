package study.anatoliy.netcracker.domain.contractions;

import study.anatoliy.netcracker.domain.client.Client;

import java.time.LocalDate;

public abstract class Contract {

    protected final long ID;

    protected final LocalDate startDate;
    protected LocalDate expirationDate;

    protected final Client client;

    public Contract(long ID, LocalDate startDate, LocalDate expirationDate, Client client) {
        this.ID = ID;
        this.startDate = startDate;
        this.expirationDate = expirationDate;
        this.client = client;
    }

    public abstract TypeContract getType();

    public long getID() {
        return ID;
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
