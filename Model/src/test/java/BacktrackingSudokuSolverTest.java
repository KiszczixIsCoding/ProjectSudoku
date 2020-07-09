import org.junit.jupiter.api.Test;
import pl.comp.model.BacktrackingSudokuSolver;
import pl.comp.model.SudokuBoard;
import pl.comp.model.SudokuSolver;

import static org.junit.jupiter.api.Assertions.*;

class BacktrackingSudokuSolverTest {
    @Test
    public void testSolving() {
        SudokuBoard testBoard = new SudokuBoard();
        SudokuSolver testSolver = new BacktrackingSudokuSolver();
        assertTrue(testSolver.solve(testBoard));
        assertTrue(testBoard.checkBoard());
    }

    @Test
    public void setIsTrueTest() {
        SudokuBoard testBoard = new SudokuBoard();
        BacktrackingSudokuSolver testSolver = new BacktrackingSudokuSolver();
        assertFalse(testSolver.isFull());
        testSolver.setFull(true);
        assertTrue(testSolver.isFull());
    }

    @Test
    public void toStringTest() {
        SudokuSolver sv = new BacktrackingSudokuSolver();
        assertEquals("BacktrackingSudokuSolver", sv.toString().substring(14,38));
    }

    @Test
    public void equalsTest() {
        BacktrackingSudokuSolver testSolver1 = new BacktrackingSudokuSolver();
        BacktrackingSudokuSolver testSolver2 = new BacktrackingSudokuSolver();
        BacktrackingSudokuSolver testSolver3 = new BacktrackingSudokuSolver();
        testSolver1.setFull(true);
        testSolver2.setFull(false);
        testSolver3.setFull(true);

        assertTrue(testSolver1.equals(testSolver1));
        assertFalse(testSolver1.equals(null));
        assertFalse(testSolver1.equals(testSolver2));
        assertTrue(testSolver1.equals(testSolver3));
    }

    @Test
    public void hashCodeTest() {
        BacktrackingSudokuSolver testSolver1 = new BacktrackingSudokuSolver();
        BacktrackingSudokuSolver testSolver2 = new BacktrackingSudokuSolver();
        BacktrackingSudokuSolver testSolver3 = new BacktrackingSudokuSolver();
        testSolver1.setFull(true);
        testSolver2.setFull(false);
        testSolver3.setFull(false);
        assertNotEquals(testSolver1.hashCode(), testSolver2.hashCode());
        assertEquals(testSolver2.hashCode(), testSolver3.hashCode());
    }
}