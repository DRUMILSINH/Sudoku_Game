package model;

import java.util.List;

public class Hint {
    private final int row;
    private final int col;
    private final List<Integer> validNumbers;
    
    public Hint(int row, int col, List<Integer> validNumbers) {
        this.row = row;
        this.col = col;
        this.validNumbers = validNumbers;
    }
    
    public int getRow() { return row; }
    public int getCol() { return col; }
    public List<Integer> getValidNumbers() { return validNumbers; }
    
    public String toString() {
        return String.format("Try %s in position (%d,%d)", 
            validNumbers.size() == 1 ? validNumbers.get(0) : 
                "one of " + validNumbers, row + 1, col + 1);
    }
} 