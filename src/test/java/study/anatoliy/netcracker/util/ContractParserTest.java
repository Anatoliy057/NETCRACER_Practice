package study.anatoliy.netcracker.util;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import study.anatoliy.netcracker.repository.ContractAlreadyExistsException;
import study.anatoliy.netcracker.repository.ContractRepository;

import static org.mockito.Mockito.*;

class ContractParserTest {


    public ContractParserTest() {}

    @Test
    public void parseString_emptyString_nothingHappened() {
        Logger logger = mock(Logger.class);
        try (MockedStatic<LoggerFactory> mocked = mockStatic(LoggerFactory.class)) {
            mocked.when(() -> LoggerFactory.getLogger(any(Class.class))).thenReturn(logger);
            ContractRepository repo = new ContractRepository();
            ContractParser parser = new ContractParser();

            parser.parseString("", repo);

            verify(logger, times(0)).error(anyString());
        }
    }

    @Test
    public void parseString_mismatchCountColumns_errorLog() throws Exception {
        Logger logger = mock(Logger.class);
        try (MockedStatic<LoggerFactory> mocked = mockStatic(LoggerFactory.class)) {
            mocked.when(() -> LoggerFactory.getLogger(any(Class.class))).thenReturn(logger);
            ContractRepository repo = new ContractRepository();
            ContractParser parser = new ContractParser();
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
            ContractRepository repo = new ContractRepository();
            ContractParser parser = new ContractParser();
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
            ContractRepository repo = new ContractRepository();
            ContractParser parser = new ContractParser();
            String row = "digital_tv,9,2012-01-01,2019-01-01,2,1995-08-09,Полина Анастия Сергеевна,1234 566666,female,standard\n" +
                    "digital_tv,9,2012-01-01,2019-01-01,2,1995-08-09,Полина Анастия Сергеевна,1234 566666,female,standard";

            parser.parseString(row, repo);

            verify(logger, times(1)).error(anyString(), any(ContractAlreadyExistsException.class));
        }
    }

}