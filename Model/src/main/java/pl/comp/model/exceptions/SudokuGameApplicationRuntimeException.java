package pl.comp.model.exceptions;

public class SudokuGameApplicationRuntimeException extends RuntimeException {

    public SudokuGameApplicationRuntimeException() {
    }

    public SudokuGameApplicationRuntimeException(String message) {
        super(message);
    }

    public SudokuGameApplicationRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
