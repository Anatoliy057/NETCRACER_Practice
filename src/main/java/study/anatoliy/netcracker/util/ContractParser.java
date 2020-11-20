package study.anatoliy.netcracker.util;

import com.opencsv.CSVReader;
import org.apache.log4j.Logger;
import study.anatoliy.netcracker.domain.client.Client;
import study.anatoliy.netcracker.domain.client.ClientBuilder;
import study.anatoliy.netcracker.domain.client.Gender;
import study.anatoliy.netcracker.domain.contract.*;
import study.anatoliy.netcracker.domain.exception.PeriodException;
import study.anatoliy.netcracker.repository.ContractAlreadyExistsException;
import study.anatoliy.netcracker.repository.ContractRepository;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ContractParser {

    public final static Collection<Validator<String[]>> DEFAULT_VALIDATORS;
    private static final Logger logger = Logger.getLogger(ContractParser.class);

    static {
        DEFAULT_VALIDATORS = new ArrayList<>();

        DEFAULT_VALIDATORS.add((String[] line) -> {
            String typeContract = line[0].toUpperCase();

            TypeContract type;
            try {
                type = TypeContract.valueOf(typeContract);
            } catch (IllegalArgumentException e) {
                throw new ValidException(String.format("Unknown type contract %s", typeContract));
            }

            int index = line.length - 1;
            String addInfo = line[index];

            if (type == TypeContract.DIGITAL_TV) {
                try {
                    ChannelPackage.valueOf(addInfo.toUpperCase());
                } catch (IllegalArgumentException e) {
                    throw new ValidException(String.format("Unknown channel package '%s' for digital tv contract", addInfo));
                }
            } else if (type == TypeContract.MOBILE) {
                if (!addInfo.matches("(-?\\d+);\\1;\\1")) {
                    throw new ValidException("Addition information for mobile contract must match this: '(-?\\d+);\\1;\\1'");
                }
            } else if (type == TypeContract.WIRED_INTERNET) {
                if (!Utils.isInteger(addInfo)) {
                    throw new ValidException("Addition information for wired internet contract must be integer, but: " + addInfo);
                }
            }
        });

        DEFAULT_VALIDATORS.add((String[] line) -> {
            if (!Utils.isInteger(line[1])) {
                throw new ValidException("ID of contract must be integer, but: " + line[1]);
            }

            if (Utils.tryToLocalDate(line[2]) == null) {
                throw new ValidException("Start date of contract must be date, like yyyy-mm-dd, but: " + line[2]);
            }

            if (Utils.tryToLocalDate(line[3]) == null) {
                throw new ValidException("Expiration date of contract must be date, like yyyy-mm-dd, but: " + line[3]);
            }

            if (!Utils.isInteger(line[4])) {
                throw new ValidException("ID of client must be integer, but: " + line[4]);
            }

            if (Utils.tryToLocalDate(line[5]) == null) {
                throw new ValidException("Birth date of client must be date, like yyyy-mm-dd, but: " + line[5]);
            }

            String strGender = line[8].toUpperCase();
            try {
                Gender.valueOf(strGender);
            } catch (IllegalArgumentException e) {
                throw new ValidException(String.format("Unknown gender '%s' of client", strGender));
            }
        });
    }

    private final List<Validator<String[]>> validators;
    private final static int SIZE_COLUMNS = 10;

    public ContractParser() {
        validators = new ArrayList<>();
        addAllValidators(DEFAULT_VALIDATORS);
    }

    public ContractParser(Collection<Validator<String[]>> validators) {
        this.validators = new ArrayList<>(validators);
    }

    public ContractParser withDefaultValidator() {
        addAllValidators(DEFAULT_VALIDATORS);
        return this;
    }

    public void addValidator(Validator<String[]> validator) {
        validators.add(validator);
    }

    public void addAllValidators(Collection<Validator<String[]>> validators) {
        this.validators.addAll(validators);
    }

    public void parse(String file, ContractRepository repo) {
        try (FileReader fileReader = new FileReader(file)) {
            CSVReader reader = new CSVReader(fileReader);
            int index = -1;

            for (String[] line : reader) {
                index++;

                if (line.length != SIZE_COLUMNS) {
                    logger.error("Must be 10 arguments at line %s" + index);
                    continue;
                }

                try {
                    valid(line);
                } catch (ValidException e) {
                    logger.error(String.format("Failed valid the contract at %d cause: %s", index, e.getMessage()));
                    continue;
                }

                Client client;
                try {
                    client = new ClientBuilder()
                            .setID(Long.parseLong(line[4]))
                            .setBirthDate(LocalDate.parse(line[5]))
                            .setFullName(line[6])
                            .setPassport(line[7])
                            .setGender(Gender.valueOf(line[8].toUpperCase()))
                            .build();
                } catch (PeriodException e) {
                    logger.error("Client initialization error at line " + index, e);
                    continue;
                }

                Contract contract = null;
                TypeContract type = TypeContract.valueOf(line[0].toUpperCase());
                try {
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

                    //contract can not be null in this case
                    repo.add(contract);
                } catch (PeriodException | IllegalArgumentException e) {
                    logger.error("Contract initialization error at line " + index, e);

                } catch (ContractAlreadyExistsException e) {
                    logger.error("Can't add contract " + index, e);
                }
            }
        } catch (IOException e) {
            logger.error("Failed to read the file", e);
        }
    }

    private void valid(String[] line) throws ValidException {
        for (Validator<String[]> validator :
                validators) {
            validator.valid(line);
        }
    }
}
