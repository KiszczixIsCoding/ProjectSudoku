import org.junit.jupiter.api.Test;
import pl.comp.model.SudokuColumn;
import pl.comp.model.SudokuField;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SudokuColumnTest {
    @Test
    public void toStringTest() {
        SudokuColumn testElement = new SudokuColumn();

        List<SudokuField> testFields1 = Arrays.asList(new SudokuField[9]);
        for (int i = 1; i <= 9; i++) {
            testFields1.set(i - 1, new SudokuField());
            testFields1.get(i - 1).setFieldValue(i);
        }
        testElement.setElementsList(testFields1);
        assertTrue(testElement.toString().contains("SudokuColumn"));
    }
}
