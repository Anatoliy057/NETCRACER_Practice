package study.anatoliy.netcracker.domain.exception;

import study.anatoliy.netcracker.domain.contract.Contract;
import study.anatoliy.netcracker.repository.ContractRepository;

import java.util.Collection;

/**
 * ContractAlreadyExistsException throw when the contract with the specified id already exists
 * and you cannot add a new one with the same id
 *
 * @see ContractRepository#add(Contract)
 * @see ContractRepository#addAll(Collection)
 * @author Udarczev Anatoliy
 */
public class ContractAlreadyExistsException extends Exception {

    public ContractAlreadyExistsException(long id) {
        super(String.format("A contract with this id = %d already exists", id));
    }
}
