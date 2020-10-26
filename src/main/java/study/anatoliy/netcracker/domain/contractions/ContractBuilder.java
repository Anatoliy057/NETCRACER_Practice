package study.anatoliy.netcracker.domain.contractions;

import study.anatoliy.netcracker.domain.client.Client;

import java.time.LocalDate;

public abstract class ContractBuilder {

    protected long id;
    protected LocalDate startDate;
    protected LocalDate expirationDate;
    protected Client client;

    public ContractBuilder setId(long id) {
        this.id = id;
        return this;
    }

    public ContractBuilder setStartDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public ContractBuilder setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
        return this;
    }

    public ContractBuilder setClient(Client client) {
        this.client = client;
        return this;
    }
}
