package pl.comp.model.exceptions;

public class FxmlLoadRuntimeException extends SudokuGameApplicationRuntimeException {
    public FxmlLoadRuntimeException() {
    }

    public FxmlLoadRuntimeException(String message) {
        super(message);
    }

    public FxmlLoadRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
