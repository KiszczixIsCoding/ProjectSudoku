package pl.comp.model.exceptions;

public class IllegalFieldValueException extends SudokuGameApplicationRuntimeException {
    public IllegalFieldValueException() {
    }

    public IllegalFieldValueException(String message) {
        super(message);
    }

    public IllegalFieldValueException(String message, Throwable exception) {
        super(message, exception);
    }
}
