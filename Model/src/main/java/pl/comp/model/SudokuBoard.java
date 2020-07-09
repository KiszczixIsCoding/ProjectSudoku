package pl.comp.model;

import com.google.common.collect.Range;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import pl.comp.model.exceptions.NoSuchClassException;
import pl.comp.model.exceptions.StreamsException;

@Entity
@Table(name = "SudokuBoard")
public class SudokuBoard implements Cloneable, Serializable {

    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @OneToMany(mappedBy = "sudokuBoard", cascade = CascadeType.ALL)
    private List<SudokuField> board = Arrays.asList(new SudokuField[81]);

    public SudokuBoard() {
        for (int i = 0; i < 81; i++) {
            board.set(i, new SudokuField());
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SudokuElement getSudokuRow(int index) {
        SudokuElement element = new SudokuRow();
        List<SudokuField> valuesList = Arrays.asList(new SudokuField[9]);
        for (int i = index - index % 9; i < index - index % 9 + 9; i++) {
            valuesList.set(i % 9, new SudokuField());
            valuesList.get(i % 9).setFieldValue(board.get(i).getFieldValue());
        }
        element.setElementsList(valuesList);
        return element;
    }

    public SudokuElement getSudokuColumn(int index) {
        SudokuElement element = new SudokuColumn();
        List<SudokuField> valuesList = Arrays.asList(new SudokuField[9]);
        int counter = 0;
        for (int i = index % 9; i < index % 9 + 9 * 9; i += 9) {
            valuesList.set(counter, new SudokuField());
            valuesList.get(counter).setFieldValue(board.get(i).getFieldValue());
            counter++;
        }
        element.setElementsList(valuesList);
        return element;
    }

    public Long getId() {
        return id;
    }

    public SudokuElement getSudokuBox(int index) {
        SudokuElement element = new SudokuBox();
        List<SudokuField> valuesList = Arrays.asList(new SudokuField[9]);
        int counter = 0;
        int row = index % 9;
        int col = index / 9;
        for (int i = col - col % 3; i < (col - col % 3) + 3; i++) {
            for (int j = row - row % 3; j < (row - row % 3) + 3; j++) {
                valuesList.set(counter, new SudokuField());
                valuesList.get(counter).setFieldValue(board.get(i * 9 + j).getFieldValue());
                counter++;
            }
        }
        element.setElementsList(valuesList);
        return element;
    }

    public boolean checkBoard() {
        SudokuElement elementRow = new SudokuRow();
        SudokuElement elementCol = new SudokuColumn();
        SudokuElement elementBox = new SudokuBox();
        List<SudokuField> verifyBoard = Arrays.asList(new SudokuField[9]);
        for (int i = 0; i < 9; i++) {
            verifyBoard.set(i, new SudokuField());
        }

        Range<Integer> valuesRange = Range.closed(1,9);
        for (int i = 0; i < 81; i++) {
            if (!valuesRange.contains(board.get(i).getFieldValue())) {
                return false;
            }
            verifyBoard.get(i % 9).setFieldValue(board.get(i).getFieldValue());
            if (i % 9 == 8) {
                elementRow.setElementsList(verifyBoard);
                if (!elementRow.verify()) {
                    return false;
                } else {
                    for (int k = 0; k < 9; k++) {
                        verifyBoard.get(k).setFieldValue(0);
                    }
                }
            }
        }
        for (int i = 0; i < 9; i++) {
            int counter = 0;
            for (int index = i; index < 81; index += 9) {
                verifyBoard.get(counter).setFieldValue(board.get(index).getFieldValue());
                counter++;
            }
            elementCol.setElementsList(verifyBoard);
            if (!elementCol.verify()) {
                return false;
            } else {
                for (int k = 0; k < 9; k++) {
                    verifyBoard.get(k).setFieldValue(0);
                }
            }
        }

        int counter1 = 0;
        for (int i = 0; i < 81; i++) {
            for (int j = i; j < i + 3 * 9; j += 9) {
                verifyBoard.get(counter1).setFieldValue(board.get(j).getFieldValue());
                counter1++;
            }
            if (counter1 == 9) {
                counter1 = 0;
                elementBox.setElementsList(verifyBoard);
                if (!elementBox.verify()) {
                    return false;
                } else {
                    for (int k = 0; k < 9; k++) {
                        verifyBoard.get(k).setFieldValue(0);
                    }
                }
            }
            if (i % 9 == 8) {
                i = i + 2 * 9;
            }
        }
        return true;
    }

    public int get(int index) {
        return board.get(index).getFieldValue();
    }

    public void set(int index, int value) {
        board.get(index).setFieldValue(value);
    }

    public List<SudokuField> getBoard() {
        return board;
    }

    @Override
    public SudokuBoard clone() {

        ResourceBundle resourceBundle = ResourceBundle.getBundle("language_version");
        try {
            ByteArrayOutputStream byteArrayOStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOStream = new ObjectOutputStream(byteArrayOStream);
            objectOStream.writeObject(this);

            ByteArrayInputStream byteArrayIStream =
                    new ByteArrayInputStream(byteArrayOStream.toByteArray());
            ObjectInputStream objectIStream = new ObjectInputStream(byteArrayIStream);

            return (SudokuBoard)objectIStream.readObject();
        } catch (IOException e) {
            throw new StreamsException(resourceBundle.getString("exception.ioStream"),e);
        } catch (ClassNotFoundException e) {
            throw new NoSuchClassException(resourceBundle.getString("exception.class"),e);
        }
    }

    @Override
    public String toString() {
        ToStringBuilder returnString = new ToStringBuilder(this);
        for (int i = 0; i < 81; i++) {
            returnString.append(board.get(i).getFieldValue());
        }
        return returnString.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj instanceof SudokuBoard) {
            EqualsBuilder eqBuilder =  new EqualsBuilder();
            for (int index = 0; index < 81; index++) {
                eqBuilder.append(((SudokuBoard) obj).board.get(index)
                        .getFieldValue(), board.get(index).getFieldValue());
            }
            return eqBuilder.isEquals();
        }
        return false;

    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(board).toHashCode();
    }
}
