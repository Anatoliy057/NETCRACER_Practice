package study.anatoliy.netcracker.domain.contractions;

import study.anatoliy.netcracker.domain.client.Client;
import study.anatoliy.netcracker.domain.exception.PeriodException;

import java.time.LocalDate;

public class DigitalTVContractBuilder {

    private ChannelPackage channelPackage;
    protected long id;
    protected LocalDate startDate;
    protected LocalDate expirationDate;
    protected Client client;

    public DigitalTVContractBuilder setId(long id) {
        this.id = id;
        return this;
    }

    public DigitalTVContractBuilder setStartDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public DigitalTVContractBuilder setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
        return this;
    }

    public DigitalTVContractBuilder setClient(Client client) {
        this.client = client;
        return this;
    }

    public DigitalTVContractBuilder setChannelPackage(ChannelPackage channelPackage) {
        this.channelPackage = channelPackage;
        return this;
    }

    public DigitalTVContract build() throws PeriodException {
        return new DigitalTVContract(id, startDate, expirationDate, client, channelPackage);
    }
}
