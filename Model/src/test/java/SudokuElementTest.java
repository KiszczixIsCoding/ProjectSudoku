import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.ToStringBuilder;
import pl.comp.model.SudokuColumn;
import pl.comp.model.SudokuElement;
import pl.comp.model.SudokuField;
import pl.comp.model.SudokuRow;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SudokuElementTest {
    @Test
    public void setAndGerElementsTest() {
        List<SudokuField> fields = Arrays.asList(new SudokuField[9]);
        for (int i = 0; i < 9; i++) {
            fields.set(i, new SudokuField(i + 1));
        }
        SudokuElement elem = new SudokuElement();
        elem.setElementsList(fields);
        for (int k = 0; k < 9; k++) {
            assertEquals(k+1, elem.getElementsList().get(k).getFieldValue());
        }

    }
    @Test
    public void verifyMethodTest() {
        SudokuElement testElement = new SudokuElement();
        List<SudokuField> testFields = Arrays.asList(new SudokuField[9]);
        for (int i = 1; i <= 9; i++) {
            testFields.set(i - 1, new SudokuField());
            testFields.get(i - 1).setFieldValue(i);
        }
        testElement.setElementsList(testFields);
        assertTrue(testElement.verify());

        testFields.get(5).setFieldValue(9);
        testElement.setElementsList(testFields);
        assertFalse(testElement.verify());
    }
    @Test
    public void checkGetAndSetMethods() {
        SudokuElement testElement = new SudokuElement();
        List<SudokuField> testFields = Arrays.asList(new SudokuField[9]);
        for(int i = 1; i <= 9 ; i++) {
            testFields.set(i-1, new SudokuField());
            testFields.get(i-1).setFieldValue(i);
        }
        testElement.setElementsList(testFields);
        assertEquals(9,testElement.getElementsList().get(8).getFieldValue());
    }

    @Test
    public void toStringTest() {
        SudokuElement testElement = new SudokuElement();

        List<SudokuField> testFields1 = Arrays.asList(new SudokuField[9]);
        for (int i = 1; i <= 9; i++) {
            testFields1.set(i - 1, new SudokuField());
            testFields1.get(i - 1).setFieldValue(i);
        }

        testElement.setElementsList(testFields1);
        assertEquals("SudokuElement", testElement.toString().substring(14,27));
    }

    @Test
    public void equalsTest() {
        SudokuElement testElement1 = new SudokuElement();
        List<SudokuField> testFields1 = Arrays.asList(new SudokuField[9]);
        for(int i = 1; i <= 9; i++) {
            testFields1.set(i-1, new SudokuField());
            testFields1.get(i-1).setFieldValue(i);
        }
        testElement1.setElementsList(testFields1);
        SudokuElement testElement2 = testElement1.clone();
        SudokuElement testElement3 = testElement1.clone();
        testElement3.getElementsList().get(5).setFieldValue(testElement1
                .getElementsList().get(5).getFieldValue() + 1);

        assertFalse(testElement1.equals(null));
        assertTrue(testElement1.equals(testElement1));
        assertTrue(testElement1.equals(testElement2));
        assertFalse(testElement2.equals(testElement3));
    }

    @Test
        public void hashCodeTest() {
        SudokuElement testElement1 = new SudokuElement();
        List<SudokuField> testFields1 = Arrays.asList(new SudokuField[9]);
        for(int i = 1; i <= 9; i++) {
            testFields1.set(i-1, new SudokuField());
            testFields1.get(i-1).setFieldValue(i);
        }
        testElement1.setElementsList(testFields1);

        SudokuElement testElement2 = testElement1.clone();
        SudokuElement testElement3 = testElement1.clone();

        testElement3.getElementsList().get(5).setFieldValue(testElement1
                .getElementsList().get(5).getFieldValue() + 1);

        assertEquals(testElement1.hashCode(),testElement2.hashCode());
        assertNotEquals(testElement2.hashCode(),testElement3.hashCode());
    }
    @Test
    public void cloneTest() {
        SudokuElement element1 = new SudokuRow();
        List<SudokuField> fieldsList = Arrays.asList(new SudokuField[9]);
        for (int i = 0; i < 9; i++) {
            fieldsList.set(i, new SudokuField());
            fieldsList.get(i).setFieldValue(i + 1);
        }
        element1.setElementsList(fieldsList);
        SudokuElement element2 = element1.clone();
        assertFalse(element1 == element2);
        assertFalse(element1.getElementsList() == element2.getElementsList());
    }
}