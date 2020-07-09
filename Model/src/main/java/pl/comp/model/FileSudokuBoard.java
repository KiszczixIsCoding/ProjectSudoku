package pl.comp.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ResourceBundle;
import pl.comp.model.exceptions.IncorrectDataSize;
import pl.comp.model.exceptions.NoDataException;
import pl.comp.model.exceptions.NoSuchFileException;
import pl.comp.model.exceptions.UnparsableStringToIntException;

public class FileSudokuBoard implements Dao<SudokuBoard> {
    private String fileName;
    private FileReader fileReader;
    private FileWriter fileWriter;
    private ResourceBundle resourceBundle = ResourceBundle.getBundle("language_version");

    public FileSudokuBoard(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public SudokuBoard read() throws NoSuchFileException, NoDataException,
            UnparsableStringToIntException, IncorrectDataSize {
        SudokuBoard board = new SudokuBoard();
        int value;
        String readValues;
        try (BufferedReader readFile = new BufferedReader(fileReader = new FileReader(fileName))) {
            readValues = readFile.readLine();
            if (readValues.isEmpty()) {
                throw new NoDataException(resourceBundle
                        .getString("exception.noData"));
            } else {
                try {
                    int index = 0;
                    do {
                        value = Integer.parseInt(readValues.substring(index, index + 1));
                        System.out.println(value + " " + index);
                        board.set(index, value);
                    } while (++index < 81);
                    return board;
                } catch (NumberFormatException ex) {
                    throw new UnparsableStringToIntException(resourceBundle
                            .getString("exception.stringParse"), ex);
                } catch (StringIndexOutOfBoundsException e) {
                    throw new IncorrectDataSize(resourceBundle
                            .getString("exception.incorrectData"), e);
                }
            }
        } catch (IOException exception) {
            throw new NoSuchFileException(resourceBundle
                    .getString("exception.file") + ": " + fileName, exception);
        }
    }

    @Override
        public void write(SudokuBoard board) throws NoSuchFileException {
        StringBuilder writeString = new StringBuilder();
        try (BufferedWriter writeFile = new BufferedWriter(fileWriter = new FileWriter(fileName))) {
            for (int index = 0; index < 81; index++) {
                writeString.append(board.get(index));
            }
            writeFile.write(writeString.toString());
        } catch (IOException exception) {
            throw new NoSuchFileException(resourceBundle
                    .getString("exception.file") + ": " + fileName, exception);
        }
    }
    /*
     Metoda finalize jest zakomentowana, poniewaz nie jest akceptowana przez checkstyle
    @Override
    public final void finalize() {
        try {
            fileReader.close();
            fileWriter.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    } */
}
