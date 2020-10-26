package study.anatoliy.netcracker.domain.contractions;

public class DigitalTVContractBuilder extends ContractBuilder {

    private ChannelPackage channelPackage;

    public DigitalTVContractBuilder setChannelPackage(ChannelPackage channelPackage) {
        this.channelPackage = channelPackage;
        return this;
    }

    public DigitalTVContract build() {
        return new DigitalTVContract(id, startDate, expirationDate, client, channelPackage);
    }
}
