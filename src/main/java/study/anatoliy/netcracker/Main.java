package study.anatoliy.netcracker;

import org.slf4j.LoggerFactory;
import study.anatoliy.netcracker.repository.ContractRepository;
import study.anatoliy.netcracker.util.ContractParser;

public class Main {

    public static void main(String[] args) {
        ClassLoader classLoader = Main.class.getClassLoader();
        String file = classLoader.getResource("contracts.csv").getFile();

        ContractParser parser = new ContractParser();
        ContractRepository repo = new ContractRepository(10);
        parser.parseFile(file, repo);
    }
}
