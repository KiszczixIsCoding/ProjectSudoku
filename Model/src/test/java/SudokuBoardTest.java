import com.google.common.collect.Range;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.jupiter.api.Test;
import pl.comp.model.BacktrackingSudokuSolver;
import pl.comp.model.SudokuBoard;
import pl.comp.model.SudokuSolver;

import static org.junit.jupiter.api.Assertions.*;

public class SudokuBoardTest {
    @Test
    public void checkValues() {
        SudokuBoard testBoard = new SudokuBoard();
        SudokuSolver testSolver = new BacktrackingSudokuSolver();
        testSolver.solve(testBoard);
        Range<Integer> valuesRange = Range.closed(1,9);
        for(int index = 0; index < 81; index++) {
            assertTrue(valuesRange.contains(testBoard.get(index)));
        }
    }
    @Test
    public void testGetField() {
        SudokuBoard testBoard = new SudokuBoard();
        assertEquals(0, testBoard.get(0));
        assertEquals(testBoard.getBoard().get(0).getFieldValue(), testBoard.get(0));
    }
    @Test
    public void testSetField(){
        SudokuBoard testBoard = new SudokuBoard();
        testBoard.set(2,3);
        assertEquals(3,testBoard.get(2));
        assertEquals(3,testBoard.getBoard().get(2).getFieldValue());
    }
    @Test
    public void getAndSetName() {
        SudokuBoard testBoard = new SudokuBoard();
        testBoard.setName("sudoku");
        assertEquals("sudoku", testBoard.getName());
    }
    @Test
    public void checkIfArrayIsChanged()
    {
        SudokuSolver testSolver = new BacktrackingSudokuSolver();
        SudokuBoard testBoard = new SudokuBoard();
        SudokuBoard testBoard1 = new SudokuBoard();
        testSolver.solve(testBoard1); // I wypełnienie
        for(int index = 0; index < 81; index++) {
                testBoard.set(index,testBoard1.get(index)); // przepisanie wartości z testowanej tablicy do pomocniczej
        }
        testSolver.solve(testBoard1); // II wypełnienie tablicy
        boolean checkDifferent = false;
        for(int index = 0; index < 81; index++)
        {
            if (testBoard.get(index) != testBoard1.get(index)) {
                checkDifferent = true;
                break;
            }
        }
        assertTrue(checkDifferent);
    }
    @Test
    public void checkGetSudokuElement() {

        SudokuBoard testBoard = new SudokuBoard();
        for (int i = 0; i < 81; i++) {
            testBoard.set(i, i % 9 + 1);
        }

        int index = 12;
        for(int counter = 0; counter < 9; counter++) {
            assertEquals(testBoard.getSudokuRow(index).getElementsList().get(counter).getFieldValue(),
                    testBoard.get(index - index % 9 + counter));
        }
        index = 20;
        int valueIndex = index % 9;
        for(int counter = 0; counter < 9; counter++) {
            assertEquals(testBoard.getSudokuColumn(index).getElementsList().get(counter).getFieldValue(),
                    testBoard.get(valueIndex));
            valueIndex += 9;
        }
        index = 15;
        int row = index % 9;
        int col = index / 9;
        int counter = 0;
        for (int i = col - col % 3; i < (col - col % 3) + 3; i++) {
            for (int j = row - row % 3; j < (row - row % 3) + 3; j++) {
                assertEquals(testBoard.getSudokuBox(index).getElementsList().get(counter).getFieldValue(), testBoard.get(j));
                counter++;
            }
        }
    }

    @Test
    public void equalsTest() {
        SudokuBoard testBoard1 = new SudokuBoard();
        SudokuBoard testBoard2 = new SudokuBoard();
        SudokuBoard testBoard3 = new SudokuBoard();
        for (int i = 0; i < 81; i++) {
            testBoard1.set(i, i % 9);
            testBoard1.set(i, (80 - i) % 9);

            testBoard3.set(i, i % 9);
            testBoard3.set(i, (80 - i) % 9);
        }

        assertTrue(testBoard1.equals(testBoard1));
        assertFalse(testBoard1.equals(null));
        assertFalse(testBoard1.equals(testBoard2));
        assertTrue(testBoard1.equals(testBoard3));
    }

    @Test
    public void hashCodeTest() {
        SudokuBoard testBoard1 = new SudokuBoard();
        SudokuBoard testBoard2 = new SudokuBoard();
        for (int i = 0; i < 81; i++) {
            testBoard1.set(i, i % 9);
            testBoard1.set(i, (80 - i) % 9);
        }
        assertNotEquals(testBoard1.hashCode(),testBoard2.hashCode());
    }

    @Test
    public void toStringTest() {
        StringBuilder toString = new StringBuilder();
        SudokuBoard testBoard = new SudokuBoard();
        for (int i = 0; i < 81; i++) {
            testBoard.set(i, i % 9 + 1);
        }
        assertEquals("SudokuBoard",testBoard.toString().substring(14,25));
    }
    @Test
    public void cloneTest() {
        SudokuBoard sudokuBoard1 = new SudokuBoard();
        for (int i = 0; i < 81; i++) {
            sudokuBoard1.set(i, i % 9 + 1);
        }
        SudokuBoard sudokuBoard2 = sudokuBoard1.clone();

        assertNotSame(sudokuBoard2, sudokuBoard1);
        assertEquals(sudokuBoard2.get(4), sudokuBoard1.get(4));
        sudokuBoard1.set(4,9);
        assertNotEquals(sudokuBoard2.get(4), sudokuBoard1.get(4));
    }
}