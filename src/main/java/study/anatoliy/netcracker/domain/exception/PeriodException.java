package study.anatoliy.netcracker.domain.exception;

import study.anatoliy.netcracker.domain.contractions.Contract;
import study.anatoliy.netcracker.domain.client.Client;

import java.time.LocalDate;

/**
 * PeriodException throw when a given period is impossible,
 * for example, when its beginning is later than its end
 *
 * @see Contract
 * @see Client
 * @author Udarczev Anatoliy
 */
public class PeriodException extends Exception {

    /** Date of start and end of period */
    private final LocalDate start, end;

    public PeriodException(LocalDate start, LocalDate end, String msg) {
        super(msg);
        this.start = start;
        this.end = end;
    }

    public PeriodException(LocalDate start, LocalDate end) {
        super("The end date is earlier than or equals its start");
        this.start = start;
        this.end = end;
    }

    public LocalDate getStart() {
        return start;
    }

    public LocalDate getEnd() {
        return end;
    }
}
