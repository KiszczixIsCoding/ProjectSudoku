package pl.comp.model.exceptions;

public class FxmlLoadException extends SudokuGameApplicationException {
    public FxmlLoadException() {
    }

    public FxmlLoadException(String message) {
        super(message);
    }

    public FxmlLoadException(String message, Throwable cause) {
        super(message, cause);
    }
}
