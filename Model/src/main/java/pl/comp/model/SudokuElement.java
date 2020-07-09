package pl.comp.model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import pl.comp.model.exceptions.NoSuchClassException;
import pl.comp.model.exceptions.StreamsException;

public class SudokuElement implements Cloneable, Serializable {
    private List<SudokuField> elementsList = Arrays.asList(new SudokuField[9]);

    public SudokuElement() {
        for (int i = 0; i < 9; i++) {
            elementsList.set(i, new SudokuField()); // Tworzenie obiektów w tablicy
        }
    }

    public void setElementsList(final List<SudokuField> sudokuFieldsList) {
        for (int i = 0; i < 9; i++) {
            elementsList.get(i).setFieldValue(sudokuFieldsList.get(i).getFieldValue());
        }
    }

    public List<SudokuField> getElementsList() {
        return elementsList;
    }

    public boolean verify() {
            for (int i = 0; i < 9; i++) {
                for (int j = i + 1; j < 9; j++) {
                    if ((elementsList.get(i).getFieldValue() == elementsList.get(j).getFieldValue())
                            && (elementsList.get(i).getFieldValue() != 0)) {
                        return false;
                    }
                }
            }
        return true;
    }

    public SudokuElement clone() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("language_version");
        try {
            ByteArrayOutputStream byteArrayOStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOStream = new ObjectOutputStream(byteArrayOStream);
            objectOStream.writeObject(this);

            ByteArrayInputStream byteArrayIStream =
                    new ByteArrayInputStream(byteArrayOStream.toByteArray());
            ObjectInputStream objectIStream = new ObjectInputStream(byteArrayIStream);
            return (SudokuElement)objectIStream.readObject();
        } catch (IOException e) {
            throw new StreamsException(resourceBundle.getString("exception.ioStream"),e);
        } catch (ClassNotFoundException e) {
            throw new NoSuchClassException(resourceBundle.getString("exception.class"),e);
        }
    }

    @Override
    public String toString() {
        ToStringBuilder returnString = new ToStringBuilder(this);
        for (int i = 0; i < 9; i++) {
            returnString.append(getElementsList().get(i).getFieldValue());
        }
        return  returnString.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj instanceof SudokuElement) {
            EqualsBuilder builder = new EqualsBuilder();
            for (int i = 0; i < 9; i++) {
                builder.append(getElementsList().get(i).getFieldValue(),
                        ((SudokuElement) obj).getElementsList().get(i).getFieldValue());
            }
            return builder.isEquals();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(getElementsList()).toHashCode();
    }
}
