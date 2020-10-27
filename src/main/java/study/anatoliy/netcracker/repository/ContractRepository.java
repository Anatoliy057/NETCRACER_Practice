package study.anatoliy.netcracker.repository;

import study.anatoliy.netcracker.domain.contractions.Contract;
import study.anatoliy.netcracker.domain.contractions.TypeContract;
import study.anatoliy.netcracker.util.Checks;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;

/**
 * Repository of various contracts
 * Allows:
 * - Receive contracts with the specified ID
 * - Remove contracts with the specified ID
 *
 * @see Contract
 * @see TypeContract
 * @author Udarczev Anatoliy
 */
public class ContractRepository {

    /** Comparator for comparison contracts by id */
    private static final Comparator<Contract> comparator = Comparator.comparingLong(Contract::getId);

    /** Array of stored contracts */
    private Contract[] contracts;
    /** Number of stored contracts */
    private int size;
    /** Indicates if an array of contracts is sorted */
    private boolean isSorted;

    /**
     * @param capacity estimated number of stored contracts
     * @throws IllegalArgumentException if capacity > 0
     */
    public ContractRepository(int capacity) {
        Checks.numberIsPositive(capacity, "Capacity must be > 0");
        contracts = new Contract[capacity];
        size = 0;
        isSorted = true;
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
     */
    public void add(Contract contract) throws ContractAlreadyExistsException {
        checkIdCollision(contract.getId());
        ensureCapacityInternal(size + 1);
        contracts[size++] = contract;
        checkIsSorted(size-1);
    }

    /**
     * @param c lots of added contracts
     * @throws ContractAlreadyExistsException if a contract with such an ID already exists
     *
     * @see ContractRepository#checkIdCollision(long)
     * @see ContractRepository#ensureCapacityInternal(int)
     * @see ContractRepository#checkIsSorted(int)
     */
    public void addAll(Collection<Contract> c) throws ContractAlreadyExistsException {
        for (Contract contract :
                c) {
            checkIdCollision(contract.getId());
        }
        Contract[] src = c.toArray(new Contract[0]);
        ensureCapacityInternal(size + c.size());
        System.arraycopy(src, 0, contracts, size, src.length);
        size += c.size();
        checkIsSorted(size-1);
    }

    /**
     * @param id ID of the contract we are looking for
     * @return contract with the specified id, if not found will return null
     *
     * @see ContractRepository#sort()
     * @see ContractRepository#searchById(long)
     */
    public Contract getByID(long id) {
        if (!isSorted) {
            sort();
        }
        int index = searchById(id);
        return index < 0 ? null : contracts[searchById(id)];
    }

    /**
     * @param id ID of the contract to be deleted
     * @return Returns the deleted contract, if not found, return null
     *
     * @see ContractRepository#sort()
     * @see ContractRepository#searchById(long)
     */
    public Contract removeById(long id) {
        if (!isSorted) {
            sort();
        }
        int index = searchById(id);
        if (index < 0) {
            return null;
        }
        return remove(index);
    }

    public int size() {
        return size;
    }

    /**
     * Checks, in order with the specified index, whether the contracts array is sorted
     *
     * @param from from which index to check if the array of contracts is sorted
     */
    private void checkIsSorted(int from) {
        if (from < 0 || !isSorted) return;
        for (int i = from; i < size-1; i++) {
            if (comparator.compare(contracts[i], contracts[i+1]) < 0) {
                isSorted = false;
                return;
            }
        }
    }

    /**
     * Sorts an array of contracts by id
     */
    private void sort() {
        if (size != 0) {
            Arrays.sort(contracts, comparator);
        }
        isSorted = true;
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
     * Binary search
     *
     * @param id id of the contract we are looking for
     * @return index of the found contract, if not found will return -1
     */
    private int searchById(long id) {
        int low = 0;
        int high = size - 1;

        while (low <= high) {
            int mid = (low + high) >>> 1;
            Contract midContact = contracts[mid];
            int cmp = Long.compare(midContact.getId(), id);
            if (cmp < 0)
                low = mid + 1;
            else if (cmp > 0)
                high = mid - 1;
            else
                return mid;
        }
        return -(low + 1);
    }

    /**
     * Removes the specified contract and shifts the positions of all contracts
     * from the right by one to the left
     *
     * @param index the index of the contract to be deleted
     * @return deleted contract
     */
    private Contract remove(int index) {
        Contract contract = contracts[index];

        int numMoved = size - index - 1;
        if (numMoved > 0)
            System.arraycopy(contracts, index+1, contracts, index,
                    numMoved);
        contracts[--size] = null;

        return contract;
    }

    /**
     * @param id ID of the added contract
     * @throws ContractAlreadyExistsException if a contract with the specified id already exists
     *
     * @see ContractRepository#sort()
     */
    private void checkIdCollision(long id) throws ContractAlreadyExistsException {
        if (!isSorted) {
            sort();
        }
        if (searchById(id) >= 0) {
            throw new ContractAlreadyExistsException(id);
        }
    }

    @Override
    public String toString() {
        return "ContractRepository{" +
                "contracts=" + Arrays.toString(contracts) +
                ", size=" + size +
                ", isSorted=" + isSorted +
                '}';
    }
}
