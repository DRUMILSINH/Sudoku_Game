package com.sudoku.model;

public enum DifficultyLevel {
    EASY(0.3),
    MEDIUM(0.5),
    HARD(0.7);
    
    private final double removalRate;
    
    DifficultyLevel(double removalRate) {
        this.removalRate = removalRate;
    }
    
    public double getRemovalRate() {
        return removalRate;
    }
} 