package model;

import java.io.Serializable;

public class GameState implements Serializable {
    private static final long serialVersionUID = 1L;
    private final int[][] gridState;
    private final boolean[][] originalCells;
    private final int elapsedTime;
    
    public GameState(SudokuGrid grid, int elapsedTime) {
        int size = grid.getSize();
        this.gridState = new int[size][size];
        this.originalCells = new boolean[size][size];
        this.elapsedTime = elapsedTime;
        
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                gridState[i][j] = grid.getValue(i, j);
                originalCells[i][j] = grid.isOriginal(i, j);
            }
        }
    }
    
    public int[][] getGridState() {
        return gridState;
    }
    
    public boolean[][] getOriginalCells() {
        return originalCells;
    }
    
    public int getElapsedTime() {
        return elapsedTime;
    }
} 