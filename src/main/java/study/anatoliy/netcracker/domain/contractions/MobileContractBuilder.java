package study.anatoliy.netcracker.domain.contractions;

public class MobileContractBuilder extends ContractBuilder {

    private int minutes;
    private int sms;
    private int megabytes;

    public MobileContractBuilder setMinutes(int minutes) {
        this.minutes = minutes;
        return this;
    }

    public MobileContractBuilder setSms(int sms) {
        this.sms = sms;
        return this;
    }

    public MobileContractBuilder setMegabytes(int megabytes) {
        this.megabytes = megabytes;
        return this;
    }

    public MobileContract build() {
        return new MobileContract(id, startDate, expirationDate, client, minutes, sms, megabytes);
    }
}
