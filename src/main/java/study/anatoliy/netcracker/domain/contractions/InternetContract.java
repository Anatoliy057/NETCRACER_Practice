package study.anatoliy.netcracker.domain.contractions;

import study.anatoliy.netcracker.domain.client.Client;
import study.anatoliy.netcracker.domain.exception.PeriodException;
import study.anatoliy.netcracker.util.Checks;

import java.time.LocalDate;
import java.util.Objects;

public class InternetContract extends Contract {

    private int megabits;

    public InternetContract(long ID, LocalDate startDate, LocalDate expirationDate, Client client, int megabits) throws PeriodException {
        super(ID, startDate, expirationDate, client);
        Checks.numberIsPositive(megabits, "Internet speed cannot be negative");
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
