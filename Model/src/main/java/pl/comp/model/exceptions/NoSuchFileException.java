package pl.comp.model.exceptions;

public class NoSuchFileException extends SudokuGameApplicationException {

    public NoSuchFileException() {
        super();
    }

    public NoSuchFileException(String message, Throwable exception) {
        super(message, exception);
    }
}
