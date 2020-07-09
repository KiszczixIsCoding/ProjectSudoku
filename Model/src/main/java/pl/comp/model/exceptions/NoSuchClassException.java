package pl.comp.model.exceptions;

public class NoSuchClassException extends SudokuGameApplicationRuntimeException {
    public NoSuchClassException() {
    }

    public NoSuchClassException(String message, Throwable cause) {
        super(message, cause);
    }
}
