package com.sudoku.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class SudokuGrid {
    private final int size;
    private final int[][] grid;
    private final boolean[][] original;

    public SudokuGrid(int size) {
        if (size != 9 && size != 16) {
            throw new IllegalArgumentException("Unsupported grid size. Only 9x9 and 16x16 are supported.");
        }
        this.size = size;
        this.grid = new int[size][size];
        this.original = new boolean[size][size];
    }

    public int getSize() {
        return size;
    }

    public int getValue(int row, int col) {
        return grid[row][col];
    }

    public boolean isOriginal(int row, int col) {
        return original[row][col];
    }

    public void generatePuzzle(DifficultyLevel level) {
        // Clear the grid
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j] = 0;
                original[i][j] = false;
            }
        }

        // Example: Pre-fill some cells as per removal rate
        int filledCells = (int) (size * size * (1 - level.getRemovalRate()));
        while (filledCells > 0) {
            int row = (int) (Math.random() * size);
            int col = (int) (Math.random() * size);
            if (grid[row][col] == 0) {
                int value = (int) (Math.random() * size) + 1;
                if (isValidMove(row, col, value)) {
                    grid[row][col] = value;
                    original[row][col] = true;
                    filledCells--;
                }
            }
        }
    }

    public Optional<Hint> getHint() {
        List<Hint> possibleHints = new ArrayList<>();

        // Look for cells where only one number is possible
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (grid[row][col] == 0) {
                    List<Integer> validNumbers = findValidNumbers(row, col);
                    if (!validNumbers.isEmpty()) {
                        possibleHints.add(new Hint(row, col, validNumbers));
                    }
                }
            }
        }

        // Sort hints by number of possibilities (easier hints first)
        possibleHints.sort(Comparator.comparingInt(h -> h.getValidNumbers().size()));

        return possibleHints.isEmpty() ? Optional.empty() : Optional.of(possibleHints.get(0));
    }

    private List<Integer> findValidNumbers(int row, int col) {
        List<Integer> valid = new ArrayList<>();
        for (int num = 1; num <= size; num++) {
            if (isValidMove(row, col, num)) {
                valid.add(num);
            }
        }
        return valid;
    }

    public void setValue(int row, int col, int value) {
        if (row < 0 || row >= size || col < 0 || col >= size) {
            throw new IllegalArgumentException("Invalid cell position");
        }
        grid[row][col] = value;
    }

    public void loadState(GameState state) {
        int[][] newGrid = state.getGridState();
        boolean[][] newOriginal = state.getOriginalCells();
        for (int i = 0; i < size; i++) {
            System.arraycopy(newGrid[i], 0, grid[i], 0, size);
            System.arraycopy(newOriginal[i], 0, original[i], 0, size);
        }
    }

    public boolean isValidMove(int row, int col, int value) {
        // Check row and column
        for (int i = 0; i < size; i++) {
            if (grid[row][i] == value || grid[i][col] == value) {
                return false;
            }
        }

        // Check subgrid
        int subgridSize = (int) Math.sqrt(size);
        int startRow = row - row % subgridSize;
        int startCol = col - col % subgridSize;
        for (int i = startRow; i < startRow + subgridSize; i++) {
            for (int j = startCol; j < startCol + subgridSize; j++) {
                if (grid[i][j] == value) {
                    return false;
                }
            }
        }

        return true;
    }
} 