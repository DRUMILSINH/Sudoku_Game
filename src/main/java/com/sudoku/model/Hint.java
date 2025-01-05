package com.sudoku.model;

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
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Try ");
        if (validNumbers.size() == 1) {
            sb.append(validNumbers.get(0));
        } else {
            sb.append("one of ").append(validNumbers);
        }
        sb.append(" in position (").append(row + 1).append(",").append(col + 1).append(")");
        return sb.toString();
    }
} 