package study.anatoliy.netcracker.domain.contract;

import study.anatoliy.netcracker.domain.client.Client;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Digital television contract entity class
 * @author Udarczev Anatoliy
 * @see Contract
 */
public class DigitalTVContract extends Contract {

    /**
     * Channel package type
     * @see ChannelPackage
     */
    private ChannelPackage channelPackage;

    /**
     * @see Contract#Contract(long, LocalDate, LocalDate, Client)
     */
    public DigitalTVContract(long ID, LocalDate startDate, LocalDate expirationDate, Client client, ChannelPackage channelPackage) {
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
