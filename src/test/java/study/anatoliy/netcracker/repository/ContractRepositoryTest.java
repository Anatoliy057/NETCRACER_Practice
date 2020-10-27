package study.anatoliy.netcracker.repository;

import org.junit.jupiter.api.Test;
import study.anatoliy.netcracker.domain.client.Client;
import study.anatoliy.netcracker.domain.client.ClientBuilder;
import study.anatoliy.netcracker.domain.client.Gender;
import study.anatoliy.netcracker.domain.contractions.*;
import study.anatoliy.netcracker.domain.exception.PeriodException;

import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ContractRepositoryTest {

    Client client = new ClientBuilder()
            .setBirthDate(LocalDate.of(2000, 5, 2))
            .setFullName("Ударцев Анатолий Александрович")
            .setPassport("1234 567890")
            .setGender(Gender.MALE)
            .setID(0)
            .build();

    InternetContract internetContract = new InternetContractBuilder()
            .setMegabits(500)
            .setClient(client)
            .setStartDate(LocalDate.of(2019, 1, 1))
            .setExpirationDate(LocalDate.of(2020, 1, 1))
            .setId(0)
            .build();

    MobileContract mobileContract = new MobileContractBuilder()
            .setMegabytes(2048)
            .setMinutes(1000)
            .setSms(100)
            .setClient(client)
            .setStartDate(LocalDate.of(2016, 1, 1))
            .setExpirationDate(LocalDate.of(2019, 11, 26))
            .setId(1)
            .build();

    DigitalTVContract digitalTVContract = new DigitalTVContractBuilder()
            .setChannelPackage(ChannelPackage.STANDARD)
            .setClient(client)
            .setStartDate(LocalDate.of(2019, 9, 28))
            .setExpirationDate(LocalDate.of(2022, 9, 28))
            .setId(2)
            .build();

    InternetContractBuilder template = new InternetContractBuilder()
            .setMegabits(1000)
            .setClient(client)
            .setStartDate(LocalDate.of(2019, 1, 1))
            .setExpirationDate(LocalDate.of(2020, 12, 12));

    ContractRepositoryTest() throws PeriodException {
    }

    @Test
    public void add_repositoryIsEmpty_sizeIsOne() throws ContractAlreadyExistsException {
        ContractRepository repo = new ContractRepository();

        repo.add(internetContract);

        assertEquals(1, repo.size());
    }

    @Test
    public void add_repositoryIsFull_capacityGrow() throws ContractAlreadyExistsException, PeriodException {
        ContractRepository repo = new ContractRepository(10);
        for (int i = 4; i < 14; i++) {
            repo.add(template.setId(i).build());
        }

        repo.add(internetContract);
        repo.add(mobileContract);
        repo.add(digitalTVContract);

        assertEquals(13, repo.size());
    }

    @Test
    public void addAll_repositoryIsEmpty_sizeIsThree() throws ContractAlreadyExistsException {
        ContractRepository repo = new ContractRepository();

        repo.addAll(Arrays.asList(internetContract, mobileContract, digitalTVContract));

        assertEquals(3, repo.size());
    }

    @Test
    public void addAll_repositoryIsFull_capacityGrow() throws ContractAlreadyExistsException, PeriodException {
        ContractRepository repo = new ContractRepository(10);
        for (int i = 4; i < 14; i++) {
            repo.add(template.setId(i).build());
        }

        repo.addAll(Arrays.asList(internetContract, mobileContract, digitalTVContract));

        assertEquals(13, repo.size());
    }

    @Test
    public void add_existingContract_throwException() throws ContractAlreadyExistsException {
        ContractRepository repo = new ContractRepository();
        repo.add(internetContract);

        assertThrows(ContractAlreadyExistsException.class, () -> repo.add(internetContract));
    }

    @Test
    public void addAll_existingContract_throwException() throws ContractAlreadyExistsException {
        ContractRepository repo = new ContractRepository();
        repo.add(internetContract);

        assertThrows(ContractAlreadyExistsException.class, () ->  {
            repo.addAll(Arrays.asList(internetContract, mobileContract, digitalTVContract));
        });
    }

    @Test
    public void getById_existingContract_returnContract() throws ContractAlreadyExistsException {
        ContractRepository repo = new ContractRepository();
        repo.add(digitalTVContract);
        repo.add(internetContract);
        repo.add(mobileContract);

        Contract contract = repo.getByID(1);

        assertEquals(mobileContract, contract);
    }

    @Test
    public void getById_notExistingContract_returnNull() throws ContractAlreadyExistsException {
        ContractRepository repo = new ContractRepository();
        repo.add(digitalTVContract);
        repo.add(internetContract);

        Contract contract = repo.getByID(1);

        assertNull(contract);
    }

    @Test
    public void removeById_ExistingContract_returnContract() throws ContractAlreadyExistsException {
        ContractRepository repo = new ContractRepository();
        repo.add(digitalTVContract);
        repo.add(internetContract);
        repo.add(mobileContract);

        Contract contract = repo.removeById(0);

        assertEquals(internetContract, contract);
        assertEquals(2, repo.size());
    }

    @Test
    public void removeById_notExistingContract_returnNull() throws ContractAlreadyExistsException {
        ContractRepository repo = new ContractRepository();
        repo.add(digitalTVContract);
        repo.add(internetContract);
        repo.add(mobileContract);

        Contract contract = repo.removeById(10);

        assertNull(contract);
        assertEquals(3, repo.size());
    }

}