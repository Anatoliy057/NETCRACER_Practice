package study.anatoliy.netcracker.parser;

import com.opencsv.CSVReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import study.anatoliy.netcracker.domain.client.Client;
import study.anatoliy.netcracker.domain.client.ClientBuilder;
import study.anatoliy.netcracker.domain.client.Gender;
import study.anatoliy.netcracker.domain.contract.*;
import study.anatoliy.netcracker.domain.exception.ContractAlreadyExistsException;
import study.anatoliy.netcracker.domain.validation.ValidationMessage;
import study.anatoliy.netcracker.domain.validation.ValidationStatus;
import study.anatoliy.netcracker.repository.ContractRepository;
import study.anatoliy.netcracker.validators.ClientValidator;
import study.anatoliy.netcracker.validators.ContractValidator;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static study.anatoliy.netcracker.domain.validation.ValidationStatus.*;

/**
 * Parsing contracts in csv format to the repository
 *
 * @author Udarczev Anatoliy
 */
public class ContractParser {

    private final Logger logger = LoggerFactory.getLogger(ContractParser.class);

    private ClientValidator clientValidator = ClientValidator.getInstance();
    private ContractValidator contractValidator = ContractValidator.getInstance();

    /** Number arguments of row */
    private final static int SIZE_COLUMNS = 10;

    public ContractParser() {}

    /**
     * Parses the csv file into contracts, if validation fails,
     * a log is displayed error and the contract is not added
     *
     * @param file file in csv format
     * @param repo repository where contracts will be added
     *
     * @see ContractParser#parseFile(String, ContractRepository)
     */
    public void parseFile(String file, ContractRepository repo) {
        try (FileReader fileReader = new FileReader(file)) {
            parse(fileReader, repo);
        } catch (IOException e) {
            logger.error("Failed to read the file", e);
        }
    }

    /**
     * Parses the csv string into contracts, if validation line or initialization contract fails,
     * a log is displayed error and the contract is not added
     *
     * @param text string in csv format
     * @param repo repository where contracts will be added
     *
     * @see ContractParser#parseFile(String, ContractRepository)
     */
    public void parseString(String text, ContractRepository repo) {
        parse(new StringReader(text), repo);
    }

    /**
     * Reads from the reader and converts data from csv format into contracts.
     * If validation line, contract, client or initialization contract fails,
     * a log is displayed error and the contract is not added
     *
     * @param reader source reader
     * @param repo repository where contracts will be added
     */
    private void parse(Reader reader, ContractRepository repo) {
        CSVReader csvReader = new CSVReader(reader);
        int index = -1;

        for (String[] line : csvReader) {
            index++;

            if (line.length != SIZE_COLUMNS) {
                logger.error("Must be 10 arguments at line " + index);
                continue;
            }

            try {
                Client client;
                    client = new ClientBuilder()
                            .setId(Long.parseLong(line[4]))
                            .setBirthDate(LocalDate.parse(line[5]))
                            .setFullName(line[6])
                            .setPassport(line[7])
                            .setGender(Gender.valueOf(line[8].toUpperCase()))
                            .build();

                    Optional<Client> cached = ClientCache.get(client.getId());

                    if (cached.isPresent()) {
                        if (cached.get().equals(client)) {
                            client = cached.get();
                        } else {
                            logger.error(String.format("Client data mismatch at %d when such customer has already been registered", index));
                            continue;
                        }
                    } else {
                        if (validClient(index, client).compareTo(ERROR) <= 0) {
                            continue;
                        }

                        ClientCache.put(client);
                    }

                Contract contract = null;
                try {
                    TypeContract type = TypeContract.valueOf(line[0].toUpperCase());
                    if (type == TypeContract.DIGITAL_TV) {
                        contract = new DigitalTVContractBuilder()
                                .setClient(client)
                                .setId(Long.parseLong(line[1]))
                                .setStartDate(LocalDate.parse(line[2]))
                                .setExpirationDate(LocalDate.parse(line[3]))
                                .setChannelPackage(ChannelPackage.valueOf(line[9].toUpperCase()))
                                .build();

                    } else if (type == TypeContract.WIRED_INTERNET) {
                        contract = new InternetContractBuilder()
                                .setClient(client)
                                .setId(Long.parseLong(line[1]))
                                .setStartDate(LocalDate.parse(line[2]))
                                .setExpirationDate(LocalDate.parse(line[3]))
                                .setMegabits(Integer.parseInt(line[9]))
                                .build();

                    } else if (type == TypeContract.MOBILE) {
                        String[] addInfo = line[9].split(";");
                        contract = new MobileContractBuilder()
                                .setClient(client)
                                .setId(Long.parseLong(line[1]))
                                .setStartDate(LocalDate.parse(line[2]))
                                .setExpirationDate(LocalDate.parse(line[3]))
                                .setMinutes(Integer.parseInt(addInfo[0]))
                                .setSms(Integer.parseInt(addInfo[1]))
                                .setMegabytes(Integer.parseInt(addInfo[2]))
                                .build();
                    }

                    if (validContract(index, contract).compareTo(ERROR) <= 0) {
                        continue;
                    }

                    repo.add(contract);
                } catch (ContractAlreadyExistsException e) {
                    logger.error("Can't add contract " + index, e);
                }
            } catch (Exception e) {
                logger.error("Can't parse contract " + index, e);
            }
        }
    }

    /**
     * Makes client validation for all validators
     *
     * @param index parsed line number
     * @param client validated client
     * @return Overall validation status (highest)
     */
    private ValidationStatus validClient(int index, Client client) {
        ValidationStatus status = ValidationStatus.SUCCESSFUL;
        for (ValidationMessage message :
                clientValidator.doValidation(client)) {
            logValidationMessage(index, message);

            status = status.compareTo(message.getStatus()) > 0 ? status : message.getStatus();
        }

        return status;
    }

    /**
     * Makes contract validation for all validators
     *
     * @param index parsed line number
     * @param contract validated contract
     * @return Overall validation status (highest)
     */
    private ValidationStatus validContract(int index, Contract contract) {
        ValidationStatus status = ValidationStatus.SUCCESSFUL;
        for (ValidationMessage message :
                contractValidator.doValidation(contract)) {
            logValidationMessage(index, message);

            status = status.compareTo(message.getStatus()) < 0 ? status : message.getStatus();
        }

        return status;
    }

    /**
     * Logs the result message of validation depending on the status
     *
     * @param index parsed line number
     * @param message result message of validation
     */
    private void logValidationMessage(int index, ValidationMessage message) {
        if (message.getStatus() == ValidationStatus.ERROR) {
            logger.error(String.format("Error valid at %d, cause: %s", index, message.getMessage()));
        } else if (message.getStatus() == ValidationStatus.WARN) {
            logger.warn(String.format("Warning valid at %d, cause: %s", index, message.getMessage()));
        }
    }

    private static class ClientCache {

        private static final Map<Long, Client> cache = new HashMap<>();

        private static Optional<Client> get(long id) {
            return Optional.ofNullable(cache.get(id));
        }

        private static void put(Client client) {
            cache.put(client.getId(), client);
        }
    }
}
