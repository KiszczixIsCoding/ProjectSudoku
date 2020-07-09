package pl.comp.model.exceptions;

public class IncorrectDataSize extends SudokuGameApplicationException {
    public IncorrectDataSize() {
    }

    public IncorrectDataSize(String message) {
        super(message);
    }

    public IncorrectDataSize(String message, Throwable exception) {
        super(message, exception);
    }
}
