package study.anatoliy.netcracker.domain.contractions;

import study.anatoliy.netcracker.domain.client.Client;
import study.anatoliy.netcracker.domain.exception.PeriodException;
import study.anatoliy.netcracker.util.Checks;

import java.time.LocalDate;
import java.util.Objects;

public abstract class Contract {

    protected final long id;

    protected final LocalDate startDate;
    protected LocalDate expirationDate;

    protected final Client client;

    public Contract(long id, LocalDate startDate, LocalDate expirationDate, Client client) throws PeriodException {
        Checks.numberIsPositive(id, "ID can not be negative");
        this.id = id;
        this.startDate = Objects.requireNonNull(startDate);
        this.expirationDate = expirationDate;
        this.client = Objects.requireNonNull(client);
        checkPeriodContract();
        checkBirthClient();
    }

    public abstract TypeContract getType();

    public long getId() {
        return id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Client getClient() {
        return client;
    }

    private void checkPeriodContract() throws PeriodException {
        if (expirationDate == null) {
            return;
        }
        Checks.periodMoreZero(startDate, expirationDate);
    }

    private void checkBirthClient() throws PeriodException {
        Checks.periodMoreZero(client.getBirthDate(), startDate, "The client cannot conclude a contract because it does not exist yet");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contract contract = (Contract) o;
        return id == contract.id &&
                startDate.equals(contract.startDate) &&
                Objects.equals(expirationDate, contract.expirationDate) &&
                client.equals(contract.client);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, startDate, expirationDate, client);
    }

    @Override
    public String toString() {
        return "Contract{" +
                "id=" + id +
                ", startDate=" + startDate +
                ", expirationDate=" + expirationDate +
                ", client=" + client +
                '}';
    }
}
