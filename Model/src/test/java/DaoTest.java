import org.junit.jupiter.api.Test;
import pl.comp.model.FileSudokuBoard;
import pl.comp.model.JpaSudokuBoard;
import pl.comp.model.SudokuBoardDaoFactory;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DaoTest {
    @Test
    public void daoFactoryTest() {
        SudokuBoardDaoFactory daoFactory = new SudokuBoardDaoFactory();
        assertTrue(daoFactory.getFileDao("") instanceof FileSudokuBoard);
        assertTrue(daoFactory.getJpaDao("") instanceof JpaSudokuBoard);
    }
}
