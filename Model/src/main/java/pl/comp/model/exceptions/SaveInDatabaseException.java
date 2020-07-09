package pl.comp.model.exceptions;

public class SaveInDatabaseException extends SudokuGameApplicationException {
    public SaveInDatabaseException() {
    }

    public SaveInDatabaseException(String message) {
        super(message);
    }

    public SaveInDatabaseException(String message, Throwable exception) {
        super(message, exception);
    }
}
