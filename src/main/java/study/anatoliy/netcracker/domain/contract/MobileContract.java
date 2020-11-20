package study.anatoliy.netcracker.domain.contract;

import study.anatoliy.netcracker.domain.client.Client;
import study.anatoliy.netcracker.domain.exception.PeriodException;
import study.anatoliy.netcracker.util.Utils;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Mobile communication contract entity class
 *
 * @see Contract
 * @author Udarczev Anatoliy
 */
public class MobileContract extends Contract {

    /** Number of available call minutes */
    private int minutes;
    /** Number of available SMS */
    private int sms;
    /** Number of available Internet traffic in megabytes */
    private int megabytes;

    /**
     * @throws IllegalArgumentException if one of: minutes, sms, megabytes < 0
     */
    public MobileContract(long ID, LocalDate startDate, LocalDate expirationDate, Client client, int minutes, int sms, int megabytes) throws PeriodException {
        super(ID, startDate, expirationDate, client);
        Utils.numberIsPositive(minutes, "The number of minutes cannot be negative");
        Utils.numberIsPositive(minutes, "The number of sms cannot be negative");
        Utils.numberIsPositive(minutes, "The number of megabytes cannot be negative");
        this.minutes = minutes;
        this.sms = sms;
        this.megabytes = megabytes;
    }

    @Override
    public TypeContract getType() {
        return TypeContract.MOBILE;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getSms() {
        return sms;
    }

    public void setSms(int sms) {
        this.sms = sms;
    }

    public int getMegabytes() {
        return megabytes;
    }

    public int getGigabytes() {
        return getMegabytes()/1024;
    }

    public void setMegabytes(int megabytes) {
        this.megabytes = megabytes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        MobileContract that = (MobileContract) o;
        return minutes == that.minutes &&
                sms == that.sms &&
                megabytes == that.megabytes;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), minutes, sms, megabytes);
    }

    @Override
    public String toString() {
        return "MobileContract{" +
                "minutes=" + minutes +
                ", sms=" + sms +
                ", megabytes=" + megabytes +
                "} " + super.toString();
    }
}
