package study.anatoliy.netcracker.domain.contract;

import study.anatoliy.netcracker.domain.client.Client;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Wired Internet Contract Entity Class
 *
 * @see Contract
 * @author Udarczev Anatoliy
 */
public class InternetContract extends Contract {

    /** Wired internet speed at Mbits */
    private int megabits;

    /**
     * @see Contract#Contract(long, LocalDate, LocalDate, Client)
     */
    public InternetContract(long ID, LocalDate startDate, LocalDate expirationDate, Client client, int megabits) {
        super(ID, startDate, expirationDate, client);
        this.megabits = megabits;
    }

    @Override
    public TypeContract getType() {
        return TypeContract.WIRED_INTERNET;
    }

    public int getMegabits() {
        return megabits;
    }

    public void setMegabits(int megabits) {
        this.megabits = megabits;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        InternetContract that = (InternetContract) o;
        return megabits == that.megabits;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), megabits);
    }

    @Override
    public String toString() {
        return "InternetContract{" +
                "megabits=" + megabits +
                "} " + super.toString();
    }
}
