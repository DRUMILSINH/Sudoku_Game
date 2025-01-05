package controller;

import model.SudokuGrid;
import model.GameState;
import model.DifficultyLevel;
import model.TimerLogic;
import view.SudokuGUI;
import view.GridPanel;
import utils.FileHandler;
import utils.Logger;
import java.util.*;

public class GameController {
    private final SudokuGrid grid;
    private final SudokuGUI gui;
    private final TimerLogic timer;
    private final Stack<GameState> undoStack;
    private final Stack<GameState> redoStack;
    private DifficultyLevel currentDifficulty;
    
    public GameController() {
        this.grid = new SudokuGrid(9); // Default size
        this.gui = new SudokuGUI(this);
        this.timer = new TimerLogic(this);
        this.undoStack = new Stack<>();
        this.redoStack = new Stack<>();
        this.currentDifficulty = DifficultyLevel.MEDIUM;
    }
    
    public void startGame() {
        gui.setVisible(true);
        startNewGame();
    }
    
    public void startNewGame() {
        grid.generatePuzzle(currentDifficulty);
        timer.reset();
        timer.start();
        undoStack.clear();
        redoStack.clear();
        updateView();
    }
    
    public void updateCell(int row, int col, int value) {
        if (grid.isOriginal(row, col)) return;
        
        // Save state for undo
        saveState();
        
        // Update grid
        if (grid.isValidMove(row, col, value)) {
            grid.setValue(row, col, value);
            updateView();
            checkWinCondition();
        } else {
            gui.showMessage("Invalid move!");
        }
    }
    
    public void checkSolution() {
        boolean complete = true;
        for (int i = 0; i < grid.getSize(); i++) {
            for (int j = 0; j < grid.getSize(); j++) {
                if (grid.getValue(i, j) == 0) {
                    complete = false;
                    break;
                }
            }
        }
        
        if (complete) {
            timer.stop();
            gui.showMessage("Congratulations! You've solved the puzzle!");
        } else {
            gui.showMessage("The puzzle is not complete yet!");
        }
    }
    
    public void getHint() {
        grid.getHint().ifPresent(hint -> {
            gui.showMessage("Hint: Try " + hint.getValidNumbers().get(0) + 
                           " at position (" + (hint.getRow() + 1) + "," + (hint.getCol() + 1) + ")");
            gui.highlightCell(hint.getRow(), hint.getCol());
        });
    }
    
    public void undo() {
        if (!undoStack.isEmpty()) {
            redoStack.push(new GameState(grid));
            GameState state = undoStack.pop();
            grid.loadState(state);
            updateView();
        }
    }
    
    public void redo() {
        if (!redoStack.isEmpty()) {
            undoStack.push(new GameState(grid));
            GameState state = redoStack.pop();
            grid.loadState(state);
            updateView();
        }
    }
    
    private void saveState() {
        undoStack.push(new GameState(grid));
        redoStack.clear();
    }
    
    private void updateView() {
        for (int i = 0; i < grid.getSize(); i++) {
            for (int j = 0; j < grid.getSize(); j++) {
                gui.getGridPanel().updateCell(i, j, 
                    grid.getValue(i, j), 
                    grid.isOriginal(i, j));
            }
        }
    }
    
    public void setDifficulty(DifficultyLevel difficulty) {
        this.currentDifficulty = difficulty;
    }
    
    public int getGridSize() {
        return grid.getSize();
    }
    
    public void updateTimer(String time) {
        gui.updateTimer(time);
    }
    
    public GridPanel getGridPanel() {
        return gui.getGridPanel();
    }
    
    public void loadGame() {
        try {
            FileHandler.loadGame().ifPresentOrElse(
                state -> {
                    grid.loadState(state);
                    updateView();
                    timer.setElapsedTime(state.getElapsedTime());
                    timer.start();
                },
                () -> gui.showMessage("No saved game found.")
            );
        } catch (Exception e) {
            gui.showMessage("Error loading game: " + e.getMessage());
            Logger.error("Failed to load game", e);
        }
    }
} 