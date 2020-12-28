package study.anatoliy.netcracker;

import study.anatoliy.netcracker.repository.ContractRepository;
import study.anatoliy.netcracker.parser.ContractParser;
import study.anatoliy.netcracker.util.inject.Injector;
import study.anatoliy.netcracker.util.inject.InjectorException;
import study.anatoliy.netcracker.util.inject.bean.SyntaxBeanException;

public class Main {


    public static void main(String[] args) throws SyntaxBeanException, InjectorException {
        ClassLoader classLoader = Main.class.getClassLoader();
        String file = classLoader.getResource("contracts.csv").getFile();

        ContractParser parser = new ContractParser();
        ContractRepository repo = new ContractRepository(10);

        Injector injector = new Injector();
        injector.scan("study.anatoliy.netcracker");

        injector.inject(parser);
        injector.inject(repo);

        parser.parseFile(file, repo);

    }
}
