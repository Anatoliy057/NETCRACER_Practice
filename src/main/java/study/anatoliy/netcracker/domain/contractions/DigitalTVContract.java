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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        DigitalTVContract that = (DigitalTVContract) o;
        return channelPackage == that.channelPackage;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), channelPackage);
    }

    @Override
    public String toString() {
        return "DigitalTVContract{" +
                "channelPackage=" + channelPackage +
                "} " + super.toString();
    }
}
