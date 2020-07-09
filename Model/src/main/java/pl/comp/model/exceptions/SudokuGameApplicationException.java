package pl.comp.model.exceptions;

public class SudokuGameApplicationException extends Exception {

    public SudokuGameApplicationException() {
        super();
    }

    public SudokuGameApplicationException(String message) {
        super(message);
    }

    public SudokuGameApplicationException(String message, Throwable exception) {
        super(message,exception);
    }

}
