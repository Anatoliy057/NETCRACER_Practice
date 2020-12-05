package study.anatoliy.netcracker.util;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Utils {

    public static boolean periodMoreZero(LocalDate start, LocalDate end) {
        return start.isAfter(end);
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
