package study.anatoliy.netcracker.domain.contractions;

import study.anatoliy.netcracker.domain.client.Client;

import java.time.LocalDate;

public class MobileContract extends Contract {

    private int minutes;
    private int sms;
    private int megabytes;

    public MobileContract(long ID, LocalDate startDate, LocalDate expirationDate, Client client, int minutes, int sms, int megabytes) {
        super(ID, startDate, expirationDate, client);
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
}
