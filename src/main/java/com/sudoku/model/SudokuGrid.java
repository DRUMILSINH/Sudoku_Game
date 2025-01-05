package com.sudoku.model;

import java.util.*;

public class SudokuGrid {
    private final int[][] grid;
    private final boolean[][] originalCells;
    private final int size;
    private final int subgridSize;
    private final Random random;

    public SudokuGrid(int size) {
        if (size != 9 && size != 16) {
            throw new IllegalArgumentException("Grid size must be 9 or 16");
        }
        this.size = size;
        this.subgridSize = (int) Math.sqrt(size);
        this.grid = new int[size][size];
        this.originalCells = new boolean[size][size];
        this.random = new Random();
    }

    public void generatePuzzle(DifficultyLevel level) {
        clearGrid();
        generateSolution(0, 0);
        removeCells(level.getInitialCells());
        markOriginalCells();
    }

    private void clearGrid() {
        for (int i = 0; i < size; i++) {
            Arrays.fill(grid[i], 0);
            Arrays.fill(originalCells[i], false);
        }
    }

    private boolean generateSolution(int row, int col) {
        if (col >= size) {
            row++;
            col = 0;
        }
        if (row >= size) {
            return true;
        }

        List<Integer> numbers = getShuffledNumbers();
        for (int num : numbers) {
            if (isValidMove(row, col, num)) {
                grid[row][col] = num;
                if (generateSolution(row, col + 1)) {
                    return true;
                }
                grid[row][col] = 0;
            }
        }
        return false;
    }

    private List<Integer> getShuffledNumbers() {
        List<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= size; i++) {
            numbers.add(i);
        }
        Collections.shuffle(numbers, random);
        return numbers;
    }

    private void removeCells(int cellsToKeep) {
        int cellsToRemove = (size * size) - cellsToKeep;
        while (cellsToRemove > 0) {
            int row = random.nextInt(size);
            int col = random.nextInt(size);
            if (grid[row][col] != 0) {
                grid[row][col] = 0;
                cellsToRemove--;
            }
        }
    }

    private void markOriginalCells() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                originalCells[i][j] = grid[i][j] != 0;
            }
        }
    }

    public boolean isValidMove(int row, int col, int num) {
        return isValidInRow(row, num) && 
               isValidInColumn(col, num) && 
               isValidInSubgrid(row, col, num);
    }

    private boolean isValidInRow(int row, int num) {
        for (int j = 0; j < size; j++) {
            if (grid[row][j] == num) return false;
        }
        return true;
    }

    private boolean isValidInColumn(int col, int num) {
        for (int i = 0; i < size; i++) {
            if (grid[i][col] == num) return false;
        }
        return true;
    }

    private boolean isValidInSubgrid(int row, int col, int num) {
        int startRow = (row / subgridSize) * subgridSize;
        int startCol = (col / subgridSize) * subgridSize;
        
        for (int i = 0; i < subgridSize; i++) {
            for (int j = 0; j < subgridSize; j++) {
                if (grid[startRow + i][startCol + j] == num) return false;
            }
        }
        return true;
    }

    public boolean isSolved() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (grid[i][j] == 0 || !isValidMove(i, j, grid[i][j])) {
                    return false;
                }
            }
        }
        return true;
    }

    public Optional<Hint> getHint() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (grid[i][j] == 0) {
                    List<Integer> validNumbers = new ArrayList<>();
                    for (int num = 1; num <= size; num++) {
                        if (isValidMove(i, j, num)) {
                            validNumbers.add(num);
                        }
                    }
                    if (!validNumbers.isEmpty()) {
                        return Optional.of(new Hint(i, j, validNumbers));
                    }
                }
            }
        }
        return Optional.empty();
    }

    public int getValue(int row, int col) {
        return grid[row][col];
    }

    public void setValue(int row, int col, int value) {
        if (row < 0 || row >= size || col < 0 || col >= size) {
            throw new IllegalArgumentException("Invalid row or column index");
        }
        if (!originalCells[row][col]) {
            grid[row][col] = value;
        }
    }

    public boolean isOriginal(int row, int col) {
        return originalCells[row][col];
    }

    public int getSize() {
        return size;
    }

    public void loadState(GameState state) {
        int[][] newGrid = state.getGridState();
        boolean[][] newOriginalCells = state.getOriginalCells();
        
        for (int i = 0; i < size; i++) {
            System.arraycopy(newGrid[i], 0, grid[i], 0, size);
            System.arraycopy(newOriginalCells[i], 0, originalCells[i], 0, size);
        }
    }
} 