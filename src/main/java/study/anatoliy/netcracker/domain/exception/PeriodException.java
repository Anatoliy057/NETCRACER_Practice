package study.anatoliy.netcracker.domain.exception;

import java.time.LocalDate;

public class PeriodException extends Exception {

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
