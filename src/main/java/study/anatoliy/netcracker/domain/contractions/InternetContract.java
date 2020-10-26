package study.anatoliy.netcracker.domain.contractions;

import study.anatoliy.netcracker.client.Client;

import java.time.LocalDate;

public class InternetContract extends Contract {

    private int megabits;

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
}
