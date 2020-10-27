package study.anatoliy.netcracker.domain.contract;

import study.anatoliy.netcracker.domain.client.Client;
import study.anatoliy.netcracker.domain.exception.PeriodException;
import study.anatoliy.netcracker.util.Checks;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Abstract entity of the contract
 *
 * @author Udarczev Anatoliy
 */
public abstract class Contract {

    /** ID of contract */
    protected final long id;

    /** Start date of the contract */
    protected final LocalDate startDate;
    /**
     * Contract end date.
     * * If null then the contract is unlimited.
     */
    protected LocalDate expirationDate;

    /** The client with whom the contract was signed */
    protected final Client client;

    /**
     * @throws PeriodException if the start date of the contract is later than its end
     * @throws PeriodException if the client's date of birth is later than the start of the contract
     * @throws IllegalArgumentException if id of contract < 0
     * @throws NullPointerException if one of the parameters (other than expirationDate) is null
     */
    protected Contract(long id, LocalDate startDate, LocalDate expirationDate, Client client) throws PeriodException {
        Checks.numberIsPositive(id, "ID can not be negative");
        this.id = id;
        this.startDate = Objects.requireNonNull(startDate);
        this.expirationDate = expirationDate;
        this.client = Objects.requireNonNull(client);
        checkPeriodContract();
        checkBirthClient();
    }

    /**
     *
     * @return type of contract
     * @see TypeContract
     */
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
