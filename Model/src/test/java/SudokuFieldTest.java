import org.junit.jupiter.api.Test;
import pl.comp.model.SudokuField;
import pl.comp.model.exceptions.IllegalFieldValueException;

import static org.junit.jupiter.api.Assertions.*;

class SudokuFieldTest {
    @Test
    public void constructorTest() {
        SudokuField testField = new SudokuField(5);
        assertEquals(5,testField.getFieldValue());
    }
    @Test
    public void getAndSetTest()
    {
        SudokuField testField = new SudokuField();
        testField.setFieldValue(5);
        assertEquals(5, testField.getFieldValue());
        assertThrows(IllegalFieldValueException.class, () -> {
        testField.setFieldValue(10);
    });
    }

    @Test
    public void equalsTest() {
        SudokuField testField1 = new SudokuField();
        SudokuField testField2 = new SudokuField();
        testField1.setFieldValue(3);
        testField2.setFieldValue(5);
        boolean status;
        status = testField1.equals(null);
        assertFalse(status);
        status = testField1.equals(testField1);
        assertTrue(status);
        status = testField1.equals(testField2);
        assertFalse(testField1.equals(testField2));
    }

    @Test
    public void hashCodeTest() {
        SudokuField testField1 = new SudokuField();
        SudokuField testField2 = new SudokuField();
        testField1.setFieldValue(9);
        testField2.setFieldValue(8);
        assertNotEquals(testField2.hashCode(),testField1.hashCode());

        testField2.setFieldValue(9);
        assertEquals(testField2.hashCode(),testField1.hashCode());
    }

    @Test
    public void toStringTest() {
            SudokuField testField1 = new SudokuField();
            testField1.setFieldValue(9);
            assertEquals("SudokuField", testField1.toString().substring(14,25));
    }

    @Test
    public void cloneTest() {
        SudokuField sudokuField1 = new SudokuField();
        sudokuField1.setFieldValue(4);
        SudokuField sudokuField2 = sudokuField1.clone();
        assertNotSame(sudokuField1, sudokuField2);
        assertEquals(sudokuField1.getFieldValue(),sudokuField2.getFieldValue());
        sudokuField2.setFieldValue(5);
        assertNotEquals(4,5);
    }
}