package study.anatoliy.netcracker.domain.contractions;

import study.anatoliy.netcracker.client.Client;

import java.time.LocalDate;

public class DigitalTVContract extends Contract {

    private ChannelPackage channelPackage;

    public DigitalTVContract(long ID, LocalDate startDate, LocalDate expirationDate, Client client, ChannelPackage channelPackage) {
        super(ID, startDate, expirationDate, client);
        this.channelPackage = channelPackage;
    }

    @Override
    public TypeContract getType() {
        return TypeContract.DIGITAL_TV;
    }

    public ChannelPackage getChannelPackage() {
        return channelPackage;
    }

    public void setChannelPackage(ChannelPackage channelPackage) {
        this.channelPackage = channelPackage;
    }
}
