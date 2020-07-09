package pl.comp.model.exceptions;

public class UnparsableStringToIntException extends SudokuGameApplicationException {
    public UnparsableStringToIntException(String message, Throwable exception) {
        super(message, exception);
    }
}
