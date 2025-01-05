package test;

import model.SudokuGrid;
import model.DifficultyLevel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SudokuGridTest {
    private SudokuGrid grid;
    
    @BeforeEach
    void setUp() {
        grid = new SudokuGrid(9);
    }
    
    @Test
    void testGridInitialization() {
        assertEquals(9, grid.getSize());
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                assertEquals(0, grid.getValue(i, j));
            }
        }
    }
    
    @Test
    void testInvalidGridSize() {
        assertThrows(IllegalArgumentException.class, () -> new SudokuGrid(10));
    }
    
    @Test
    void testPuzzleGeneration() {
        grid.generatePuzzle(DifficultyLevel.EASY);
        int nonEmptyCells = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (grid.getValue(i, j) != 0) {
                    nonEmptyCells++;
                }
            }
        }
        assertTrue(nonEmptyCells > 0);
    }
    
    @Test
    void testValidMove() {
        assertTrue(grid.isValidMove(0, 0, 1));
        grid.setValue(0, 0, 1);
        assertFalse(grid.isValidMove(0, 1, 1));
        assertFalse(grid.isValidMove(1, 0, 1));
    }
} 