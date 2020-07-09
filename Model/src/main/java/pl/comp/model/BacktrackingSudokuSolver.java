package pl.comp.model;

import java.util.Random;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class BacktrackingSudokuSolver implements SudokuSolver {
    private boolean isFull =  false;

    @Override
    public boolean solve(final SudokuBoard board) {
        int index = 0;
        boolean check = true;
        if (isFull) {
            for (int i = 0; i < 81; i++) {
                    board.set(i, 0);
            }
            isFull = false;
        }
        for (index = 0; index < 81; index++) {
            if (board.get(index) == 0) {
                check = false;
                break;
            }
        }
        if (check) {
            isFull = true;
            return true;
        }

        Random rand = new Random();
        int testedNumber = rand.nextInt(9) + 1;

        for (int tries = 1; tries <= 9; tries++) {
            board.set(index, testedNumber);
            if (board.getSudokuRow(index).verify() && board.getSudokuColumn(index).verify()
                    && board.getSudokuBox(index).verify()) {
                if (solve(board)) {
                    return true;
                } else {
                    board.set(index, 0);
                }
            } else {
                board.set(index, 0);
            }
            testedNumber++;
            if (testedNumber == 10) {
                testedNumber = 1;
            }
        }
        return false;
    }

    public boolean isFull() {
        return isFull;
    }

    public void setFull(boolean full) {
        isFull = full;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj instanceof BacktrackingSudokuSolver) {
            return new EqualsBuilder()
                    .append(isFull, ((BacktrackingSudokuSolver) obj).isFull).isEquals();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(isFull).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append(isFull).toString();
    }
}
