package pl.comp.model;

import com.google.common.collect.Range;
import java.io.Serializable;
import java.util.ResourceBundle;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.comp.model.exceptions.IllegalCloneException;
import pl.comp.model.exceptions.IllegalFieldValueException;

@Entity
@Table(name = "SudokuField")
public class SudokuField implements Cloneable, Serializable, Comparable<SudokuField> {

    @Id
    @GeneratedValue

    private Long id;
    private int value;

    @ManyToOne
    @JoinColumn(name = "sudokuBoard_id")
    private SudokuBoard sudokuBoard;

    public SudokuField() {
    }

    public SudokuField(int value) {
        this.value = value;
    }

    public int getFieldValue() {
        return value;
    }

    public void setFieldValue(int value) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("language_version");
        Range<Integer> range = Range.closed(0,9);
        if (range.contains(value)) {
            this.value = value;
        } else {
            throw new IllegalFieldValueException(resourceBundle
                    .getString("exception.incorrectValue"));
        }
    }

    public SudokuField clone() {
        Logger logger = LogManager.getLogger(SudokuField.class);
        ResourceBundle resourceBundle = ResourceBundle.getBundle("language_version");
        try {
            return (SudokuField) super.clone();
        } catch (CloneNotSupportedException e) {
            logger.error(resourceBundle.getString("exception.clone"));
            throw new IllegalCloneException(resourceBundle.getString("exception.clone"),e);
        }
    }

    @Override
    public int compareTo(SudokuField fieldToCompare) {
        return Integer.compare(this.value, fieldToCompare.getFieldValue());
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("value",value).toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        return new EqualsBuilder().appendSuper(super.equals(obj))
                .append(value, ((SudokuField) obj).value).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(value).toHashCode();
    }

}
