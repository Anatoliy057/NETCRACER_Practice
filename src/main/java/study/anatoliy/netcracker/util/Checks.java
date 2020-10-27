package study.anatoliy.netcracker.util;

import study.anatoliy.netcracker.domain.exception.PeriodException;

import java.time.LocalDate;
import java.time.Period;

public class Checks {

    public static void periodMoreZero(LocalDate start, LocalDate end) throws PeriodException {
        Period period = Period.between(start, end);
        if (period.isNegative() || period.isZero()) {
            throw new PeriodException(start, end);
        }
    }

    public static void periodMoreZero(LocalDate start, LocalDate end, String msg) throws PeriodException {
        Period period = Period.between(start, end);
        if (period.isNegative() || period.isZero()) {
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
}
