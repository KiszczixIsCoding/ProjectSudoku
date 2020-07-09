package pl.comp.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class SudokuColumn extends SudokuElement {
    @Override
    public String toString() {
        ToStringBuilder returnString = new ToStringBuilder(this);
        for (int i = 0; i < 9; i++) {
            returnString.append(getElementsList().get(i).getFieldValue());
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
        if (obj instanceof SudokuColumn) {
            EqualsBuilder builder = new EqualsBuilder();
            for (int i = 0; i < 9; i++) {
                builder.append(getElementsList().get(i).getFieldValue(),
                        ((SudokuColumn) obj).getElementsList());
            }
            return builder.isEquals();
        }
        return false;
    }
}
