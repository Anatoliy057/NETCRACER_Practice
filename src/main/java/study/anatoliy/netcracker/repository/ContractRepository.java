package study.anatoliy.netcracker.repository;

import study.anatoliy.netcracker.domain.contractions.Contract;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;

public class ContractRepository {

    private static final Comparator<Contract> comparator = Comparator.comparingLong(Contract::getId);

    private Contract[] contracts;
    private int size;
    private boolean isSorted;

    public ContractRepository(int capacity) {
        contracts = new Contract[capacity];
        size = 0;
        isSorted = true;
    }

    public ContractRepository() {
        this(8);
    }

    public void add(Contract contract) throws ContractAlreadyExistsException {
        checkIdCollision(contract.getId());
        ensureCapacityInternal(size + 1);
        contracts[size++] = contract;
        checkIsSorted(size-1);
    }

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

    public Contract getByID(long id) {
        if (!isSorted) {
            sort();
        }
        int index = searchById(id);
        return index < 0 ? null : contracts[searchById(id)];
    }

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

    private void checkIsSorted(int from) {
        if (from < 0 || !isSorted) return;
        for (int i = from; i < size-1; i++) {
            if (comparator.compare(contracts[i], contracts[i+1]) < 0) {
                isSorted = false;
                return;
            }
        }
    }

    private void sort() {
        if (size != 0) {
            Arrays.sort(contracts, comparator);
        }
        isSorted = true;
    }

    private void ensureCapacityInternal(int minCapacity) {
        if (contracts.length <= minCapacity) {
            grow(minCapacity + 1);
        }
    }

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

    private Contract remove(int index) {
        Contract contract = contracts[index];

        int numMoved = size - index - 1;
        if (numMoved > 0)
            System.arraycopy(contracts, index+1, contracts, index,
                    numMoved);
        contracts[--size] = null;

        return contract;
    }

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
