package study.anatoliy.netcracker.repository;

public class ContractAlreadyExistsException extends Exception {

    public ContractAlreadyExistsException(long id) {
        super(String.format("A contract with this id = %d already exists", id));
    }
}
