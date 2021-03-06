package study.anatoliy.netcracker.parser;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import study.anatoliy.netcracker.parser.ContractParser;
import study.anatoliy.netcracker.domain.exception.ContractAlreadyExistsException;
import study.anatoliy.netcracker.repository.ContractRepository;
import study.anatoliy.netcracker.util.sort.ISorter;
import study.anatoliy.netcracker.util.sort.QuickSorter;
import study.anatoliy.netcracker.util.sort.Sorters;
import study.anatoliy.netcracker.util.validator.Validator;
import study.anatoliy.netcracker.validators.Validators;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

class ContractParserTest {

    private static ISorter sorter = Sorters.getQuickSorter();
    private static List<Validator> validators = new ArrayList<>();

    static {
        validators.add(Validators.validatorBirthDateClient());
        validators.add(Validators.validatorDatesContract());
        validators.add(Validators.validatorInternetContract());
        validators.add(Validators.validatorIdClient());
        validators.add(Validators.validatorIdContract());
        validators.add(Validators.validatorMobileContract());
    }

    public ContractParserTest() {}

    private ContractRepository getRepo() {
        ContractRepository repo = new ContractRepository();
        repo.setSorter(sorter);
        return repo;
    }

    private ContractParser getParser() {
        ContractParser repo = new ContractParser();
        repo.setValidators(validators);
        return repo;
    }

    @Test
    public void parseString_emptyString_nothingHappened() {
        Logger logger = mock(Logger.class);
        try (MockedStatic<LoggerFactory> mocked = mockStatic(LoggerFactory.class)) {
            mocked.when(() -> LoggerFactory.getLogger(any(Class.class))).thenReturn(logger);
            ContractRepository repo = getRepo();
            ContractParser parser = getParser();

            parser.parseString("", repo);

            verify(logger, times(0)).error(anyString());
        }
    }

    @Test
    public void parseString_mismatchCountColumns_errorLog() throws Exception {
        Logger logger = mock(Logger.class);
        try (MockedStatic<LoggerFactory> mocked = mockStatic(LoggerFactory.class)) {
            mocked.when(() -> LoggerFactory.getLogger(any(Class.class))).thenReturn(logger);
            ContractRepository repo = getRepo();
            ContractParser parser = getParser();
            String row = "digital_tv,9,2012-01-01,2019-01-01,2,1995-08-09,Полина Анастия Сергеевна,1234 566666,female";

            parser.parseString(row, repo);

            verify(logger, times(1)).error("Must be 10 arguments at line 0");

        }
    }

    @Test
    public void parseString_unknownTypeContract_errorLog() throws Exception {
        Logger logger = mock(Logger.class);
        try (MockedStatic<LoggerFactory> mocked = mockStatic(LoggerFactory.class)) {
            mocked.when(() -> LoggerFactory.getLogger(any(Class.class))).thenReturn(logger);
            ContractRepository repo = getRepo();
            ContractParser parser = getParser();
            String row = "digital_tv2,9,2012-01-01,2019-01-01,2,1995-08-09,Полина Анастия Сергеевна,1234 566666,female,standard";

            parser.parseString(row, repo);

            verify(logger, times(1)).error(anyString(), any(Throwable.class));
        }
    }

    @Test
    public void parseString_idContractCollision_errorLog() throws Exception {
        Logger logger = mock(Logger.class);
        try (MockedStatic<LoggerFactory> mocked = mockStatic(LoggerFactory.class)) {
            mocked.when(() -> LoggerFactory.getLogger(any(Class.class))).thenReturn(logger);
            ContractRepository repo = getRepo();
            ContractParser parser = getParser();
            String row = "digital_tv,9,2012-01-01,2019-01-01,2,1995-08-09,Полина Анастия Сергеевна,1234 566666,female,standard\n" +
                    "digital_tv,9,2012-01-01,2019-01-01,2,1995-08-09,Полина Анастия Сергеевна,1234 566666,female,standard";

            parser.parseString(row, repo);

            verify(logger, times(1)).error(anyString(), any(ContractAlreadyExistsException.class));
        }
    }

    @Test
    public void parseString_clientDataMismatch_errorLog() throws Exception {
        Logger logger = mock(Logger.class);
        try (MockedStatic<LoggerFactory> mocked = mockStatic(LoggerFactory.class)) {
            mocked.when(() -> LoggerFactory.getLogger(any(Class.class))).thenReturn(logger);
            ContractRepository repo = getRepo();
            ContractParser parser = getParser();
            String row = "digital_tv,9,2012-01-01,2019-01-01,2,1995-08-09,Полина Анастия Сергеевна,1234 566666,female,standard\n" +
                    "wired_internet,8,2018-01-01,2023-01-01,2,1995-08-09,Борденко Павел Алексеевич,1234 566666,female,2048\n";

            parser.parseString(row, repo);

            verify(logger, times(1)).error("Client data mismatch at 1 when such customer has already been registered");
        }
    }

}