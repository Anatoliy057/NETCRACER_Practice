package study.anatoliy.netcracker.domain.validation;

import java.util.Objects;

public class ValidationMessage {

    private ValidationStatus status;
    private String message;

    public ValidationMessage(ValidationStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public ValidationStatus getStatus() {
        return status;
    }

    public static ValidationMessage error(String message) {
        return new ValidationMessage(ValidationStatus.ERROR, message);
    }

    public static ValidationMessage warn(String message) {
        return new ValidationMessage(ValidationStatus.WARN, message);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ValidationMessage that = (ValidationMessage) o;
        return message.equals(that.message) &&
                status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, status);
    }
}
