import org.junit.jupiter.api.Test;
import pl.comp.model.SudokuBoard;
import pl.comp.model.SudokuBoardDaoFactory;
import pl.comp.model.exceptions.NoSuchFileException;

import static org.junit.jupiter.api.Assertions.*;

class FileSudokuBoardTest {

    @Test
    public void readTest() {
        SudokuBoardDaoFactory sbDao = new SudokuBoardDaoFactory();
        assertThrows(NoSuchFileException.class, () -> {
            sbDao.getFileDao("").read();
        });
    }

    @Test
    public void writeTest() {
        SudokuBoard sudoku = new SudokuBoard();
        SudokuBoardDaoFactory sbDao = new SudokuBoardDaoFactory();
        assertThrows(NoSuchFileException.class, () -> {
            sbDao.getFileDao("").write(sudoku);
        });
    }
}