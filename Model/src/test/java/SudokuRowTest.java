import org.junit.jupiter.api.Test;
import pl.comp.model.SudokuElement;
import pl.comp.model.SudokuField;
import pl.comp.model.SudokuRow;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SudokuRowTest {
    @Test
    public void toStringTest() {
        SudokuRow testElement = new SudokuRow();

        List<SudokuField> testFields1 = Arrays.asList(new SudokuField[9]);
        for (int i = 1; i <= 9; i++) {
            testFields1.set(i - 1, new SudokuField());
            testFields1.get(i - 1).setFieldValue(i);
        }
        testElement.setElementsList(testFields1);
        assertTrue(testElement.toString().contains("SudokuRow"));
    }
}
