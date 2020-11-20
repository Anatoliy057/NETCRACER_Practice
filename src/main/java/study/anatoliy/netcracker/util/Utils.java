package study.anatoliy.netcracker.util;

import study.anatoliy.netcracker.domain.exception.PeriodException;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Utils {

    public static void periodMoreZero(LocalDate start, LocalDate end) throws PeriodException {
        if (start.isAfter(end)) {
            throw new PeriodException(start, end);
        }
    }

    public static void periodMoreZero(LocalDate start, LocalDate end, String msg) throws PeriodException {
        if (start.isAfter(end)) {
            throw new PeriodException(start, end, msg);
        }
    }

    public static void numberIsPositive(long n, String msg) {
        if (n < 0) {
            throw new IllegalArgumentException(msg + ": " + n);
        }
    }

    public static void numberIsPositive(int n, String msg) {
        if (n < 0) {
            throw new IllegalArgumentException(msg + ": " + n);
        }
    }

    public static void numberIsPositive(double n, String msg) {
        if (Double.compare(n, 0) < 0) {
            throw new IllegalArgumentException(msg + ": " + n);
        }
    }

    public static boolean isInteger(String strNum) {
        return strNum.matches("-?\\d+");
    }

    public static LocalDate tryToLocalDate(String strDate) {
        try {
            return LocalDate.parse(strDate);
        } catch (DateTimeParseException e) {
            return null;
        }
    }
}
