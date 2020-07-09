package pl.comp.model.exceptions;

public class IllegalCloneException extends SudokuGameApplicationRuntimeException {
    public IllegalCloneException() {
    }

    public IllegalCloneException(String message) {
        super(message);
    }

    public IllegalCloneException(String message, Throwable cause) {
        super(message, cause);
    }
}
