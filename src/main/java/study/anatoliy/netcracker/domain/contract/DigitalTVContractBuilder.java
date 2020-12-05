package study.anatoliy.netcracker.domain.contract;

import study.anatoliy.netcracker.domain.client.Client;

import java.time.LocalDate;

/**
 * Digital television contract builder
 *
 * @see DigitalTVContract
 * @author Udarczev Anatoliy
 */
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

    public DigitalTVContract build() {
        return new DigitalTVContract(id, startDate, expirationDate, client, channelPackage);
    }
}
