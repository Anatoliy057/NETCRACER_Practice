package study.anatoliy.netcracker.domain.contract;

import study.anatoliy.netcracker.domain.client.Client;

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
     * @throws NullPointerException if one of the parameters (other than expirationDate) is null
     */
    protected Contract(long id, LocalDate startDate, LocalDate expirationDate, Client client) {
        this.id = id;
        this.startDate = Objects.requireNonNull(startDate);
        this.expirationDate = expirationDate;
        this.client = Objects.requireNonNull(client);
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
