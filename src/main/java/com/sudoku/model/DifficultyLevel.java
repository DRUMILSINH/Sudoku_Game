package com.sudoku.model;

public enum DifficultyLevel {
    EASY(40),
    MEDIUM(30),
    HARD(25);
    
    private final int initialCells;
    
    DifficultyLevel(int initialCells) {
        this.initialCells = initialCells;
    }
    
    public int getInitialCells() {
        return initialCells;
    }
} 