package pl.comp.model.exceptions;

public class IllegalNameArgumentException extends SudokuGameApplicationException {
    public IllegalNameArgumentException() {
    }

    public IllegalNameArgumentException(String message) {
        super(message);
    }

    public IllegalNameArgumentException(String message, Throwable exception) {
        super(message, exception);
    }
}
