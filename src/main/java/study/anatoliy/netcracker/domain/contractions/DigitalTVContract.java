package study.anatoliy.netcracker.domain.contractions;

import study.anatoliy.netcracker.domain.client.Client;
import study.anatoliy.netcracker.domain.exception.PeriodException;

import java.time.LocalDate;
import java.util.Objects;

public class DigitalTVContract extends Contract {

    private ChannelPackage channelPackage;

    public DigitalTVContract(long ID, LocalDate startDate, LocalDate expirationDate, Client client, ChannelPackage channelPackage) throws PeriodException {
        super(ID, startDate, expirationDate, client);
        this.channelPackage = Objects.requireNonNull(channelPackage);
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
