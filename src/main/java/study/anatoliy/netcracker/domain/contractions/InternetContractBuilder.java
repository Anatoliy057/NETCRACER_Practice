package study.anatoliy.netcracker.domain.contractions;

public class InternetContractBuilder extends ContractBuilder {

    private int megabits;

    public InternetContractBuilder setMegabits(int megabits) {
        this.megabits = megabits;
        return this;
    }

    public InternetContract build() {
        return new InternetContract(id, startDate, expirationDate, client, megabits);
    }
}
