package study.anatoliy.netcracker.repository;

import study.anatoliy.netcracker.domain.contract.Contract;
import study.anatoliy.netcracker.domain.contract.TypeContract;
import study.anatoliy.netcracker.util.*;

import java.util.*;
import java.util.function.Predicate;

/**
 * Repository of various contracts
 * Allows:
 * - Receive contracts with the specified ID
 * - Remove contracts with the specified ID
 * - Get filtered contracts
 * - Get sorted contracts
 *
 * @see Contract
 * @see TypeContract
 * 
 * @author Udarczev Anatoliy
 */
public class ContractRepository {

    /** Set of stored contract keys */
    private final Set<Long> ids;
    /** Array of stored contracts */
    private Contract[] contracts;
    /** Number of stored contracts */
    private int size;
    /** sorting algorithm */
    private ISorter sorter;

    /**
     * @param capacity estimated number of stored contracts
     * @param sorter the algorithm by which the sorting will be carried out
     * @throws IllegalArgumentException if capacity > 0
     * @throws NullPointerException if sorter is null
     */
    public ContractRepository(int capacity, ISorter sorter) {
        Objects.requireNonNull(sorter);
        Utils.numberIsPositive(capacity, "Capacity must be > 0");
        contracts = new Contract[capacity];
        size = 0;
        ids = new HashSet<>();
        this.sorter = sorter;
    }

    /**
     * @param capacity estimated number of stored contracts
     * @see ContractRepository#ContractRepository(int, ISorter) where type of sorter is QUICK
     */
    public ContractRepository(int capacity) {
        this(capacity, Sorters.get(TypeSorter.QUICK));
    }

    /**
     * @see ContractRepository#ContractRepository(int) where capacity is 8
     */
    public ContractRepository() {
        this(8);
    }

    /**
     * @param contract added contract
     * @throws ContractAlreadyExistsException if a contract with such an ID already exists
     *
     * @see ContractRepository#addIdContract(long)
     * @see ContractRepository#ensureCapacityInternal(int)
     */
    public void add(Contract contract) throws ContractAlreadyExistsException {
        addIdContract(contract.getId());
        ensureCapacityInternal(size + 1);
        contracts[size++] = contract;
    }

    /**
     * @param c lots of added contracts
     * @throws ContractAlreadyExistsException if a contract with such an ID already exists
     *
     * @see ContractRepository#addIdContract(long)
     * @see ContractRepository#ensureCapacityInternal(int)
     */
    public void addAll(Collection<Contract> c) throws ContractAlreadyExistsException {
        for (Contract contract :
                c) {
            addIdContract(contract.getId());
        }
        Contract[] src = c.toArray(new Contract[0]);
        ensureCapacityInternal(size + c.size());
        System.arraycopy(src, 0, contracts, size, src.length);
        size += c.size();
    }

    /**
     * @param predicate the condition by which contracts are filtered
     * @return collection of contracts meeting the specified conditions
     */
    public Collection<Contract> getAllFilterBy(Predicate<Contract> predicate) {
        List<Contract> filteredContracts = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            if (predicate.test(contracts[i])) {
                filteredContracts.add(contracts[i]);
            }
        }
        return filteredContracts;
    }

    /**
     * @param comparator comparator by which contracts will be sorted
     * @return list of contracts sorted by a given comparator
     */
    public List<Contract> getAllSortedBy(Comparator<Contract> comparator) {
        List<Contract> sortedContracts = Arrays.asList(Arrays.copyOf(contracts, size));
        sorter.sort(sortedContracts, comparator);
        return sortedContracts;
    }

    /**
     * @param id ID of the contract we are looking for
     * @return optional contract with the specified id
     *
     * @see ContractRepository#searchById(long)
     */
    public Optional<Contract> getByID(long id) {
        int index = searchById(id);
        return index < 0 ? Optional.empty() : Optional.of(contracts[index]);
    }

    /**
     * @param id ID of the contract to be deleted
     * @return the optional deleted contract
     *
     * @see ContractRepository#searchById(long)
     */
    public Optional<Contract> removeById(long id) {
        int index = searchById(id);
        if (index < 0) {
            return Optional.empty();
        }
        return Optional.of(remove(index));
    }

    /**
     * Checks if the array needs to be expanded to store contracts
     *
     * @param minCapacity minimum required capacity
     * @see ContractRepository#grow(int)
     */
    private void ensureCapacityInternal(int minCapacity) {
        if (contracts.length <= minCapacity) {
            grow(minCapacity + 1);
        }
    }

    /**
     * Expands an array 1.5 times larger than the previous one
     * or to the required value if it is larger
     *
     * @param minCapacity minimum required capacity
     */
    private void grow(int minCapacity) {
        if (minCapacity < 0) {
            throw new OutOfMemoryError();
        }
        int oldCapacity = contracts.length;
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        newCapacity = newCapacity < 0 ? Integer.MAX_VALUE : newCapacity;
        if (newCapacity < minCapacity) {
            newCapacity = minCapacity;
        }
        contracts = Arrays.copyOf(contracts, newCapacity);
    }

    /**
     * Sequential search
     *
     * @param id id of the contract we are looking for
     * @return index of the found contract, if not found will return -1
     */
    private int searchById(long id) {
        for (int i = 0; i < size; i++) {
            Contract c = contracts[i];
            if (c.getId() == id) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Removes the specified contract and shifts the positions of all contracts
     * from the right by one to the left
     *
     * @param index the index of the contract to be deleted
     * @return deleted contract
     *
     * @see ContractRepository#removeIdContract(long)
     */
    private Contract remove(int index) {
        Contract contract = contracts[index];

        int numMoved = size - index - 1;
        if (numMoved > 0)
            System.arraycopy(contracts, index+1, contracts, index,
                    numMoved);
        contracts[--size] = null;

        removeIdContract(contract.getId());

        return contract;
    }

    /**
     * Adds a contract ID to the set of existing contracts IDs
     *
     * @param id ID of the added contract
     * @throws ContractAlreadyExistsException if a contract with the specified id already exists
     */
    private void addIdContract(long id) throws ContractAlreadyExistsException {
        if (!ids.add(id)) {
            throw new ContractAlreadyExistsException(id);
        }
    }

    /**
     * Removes the contract ID from the set of existing contracts IDs
     *
     * @param id ID of the contract to be deleted
     */
    private void removeIdContract(long id) {
        ids.remove(id);
    }

    public int size() {
        return size;
    }

    public ISorter getSorter() {
        return sorter;
    }

    public ContractRepository setSorter(ISorter sorter) {
        this.sorter = sorter;
        return this;
    }

    @Override
    public String toString() {
        return "ContractRepository{" +
                "contracts=" + Arrays.toString(contracts) +
                ", size=" + size +
                '}';
    }
}
