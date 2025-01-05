package com.sudoku.controller;

import com.sudoku.model.*;
import com.sudoku.utils.FileHandler;
import com.sudoku.utils.Logger;
import com.sudoku.view.SudokuGUI;

import java.util.Optional;
import java.util.Stack;

public class GameController {
    private SudokuGrid grid;
    private final TimerLogic timer;
    private SudokuGUI gui;
    private DifficultyLevel difficulty;
    private final Stack<GameState> undoStack;
    private final Stack<GameState> redoStack;

    public GameController() {
        this.grid = new SudokuGrid(9);
        this.difficulty = DifficultyLevel.EASY;
        this.undoStack = new Stack<>();
        this.redoStack = new Stack<>();
        TimerLogic tempTimer = new TimerLogic(e -> updateTimerDisplay());
        this.timer = tempTimer;
    }

    private void updateTimerDisplay() {
        if (gui != null) {
            gui.updateTimer(timer.getFormattedTime());
        }
    }

    public void setGUI(SudokuGUI gui) {
        this.gui = gui;
    }

    public void startNewGame() {
        grid = new SudokuGrid(9);
        grid.generatePuzzle(difficulty);
        undoStack.clear();
        redoStack.clear();
        timer.reset();
        timer.start();
        saveState();
        updateGUI();
    }

    public void updateCell(int row, int col, int value) {
        if (grid.isValidMove(row, col, value)) {
            grid.setValue(row, col, value);
            saveState();
            updateGUI();
            checkWinCondition();
        }
    }

    public void checkWinCondition() {
        if (grid.isSolved()) {
            timer.stop();
            gui.showMessage("Congratulations! You've solved the puzzle!");
        }
    }

    public void provideHint() {
        Optional<Hint> hint = grid.getHint();
        hint.ifPresent(h -> {
            String validNumbers = h.getValidNumbers().toString();
            gui.showMessage("Valid numbers for cell (" + (h.getRow() + 1) + "," + 
                          (h.getCol() + 1) + "): " + validNumbers);
            gui.highlightCell(h.getRow(), h.getCol());
        });
    }

    public void setDifficulty(DifficultyLevel difficulty) {
        this.difficulty = difficulty;
    }

    public void saveGame() {
        try {
            GameState state = new GameState(grid, timer.getElapsedSeconds());
            FileHandler.saveGame(state);
            gui.showMessage("Game saved successfully!");
        } catch (Exception e) {
            Logger.error("Failed to save game", e);
            gui.showMessage("Failed to save game: " + e.getMessage());
        }
    }

    public void loadGame() {
        try {
            Optional<GameState> savedState = FileHandler.loadGame();
            if (savedState.isPresent()) {
                GameState state = savedState.get();
                grid.loadState(state);
                timer.setElapsedSeconds(state.getElapsedTime());
                undoStack.clear();
                redoStack.clear();
                saveState();
                updateGUI();
                gui.showMessage("Game loaded successfully!");
            } else {
                gui.showMessage("No saved game found.");
            }
        } catch (Exception e) {
            Logger.error("Failed to load game", e);
            gui.showMessage("Failed to load game: " + e.getMessage());
        }
    }

    private void saveState() {
        undoStack.push(new GameState(grid, timer.getElapsedSeconds()));
        redoStack.clear();
    }

    public void undo() {
        if (!undoStack.isEmpty()) {
            GameState currentState = new GameState(grid, timer.getElapsedSeconds());
            redoStack.push(currentState);
            
            GameState previousState = undoStack.pop();
            grid.loadState(previousState);
            timer.setElapsedSeconds(previousState.getElapsedTime());
            updateGUI();
        }
    }

    public void redo() {
        if (!redoStack.isEmpty()) {
            GameState currentState = new GameState(grid, timer.getElapsedSeconds());
            undoStack.push(currentState);
            
            GameState nextState = redoStack.pop();
            grid.loadState(nextState);
            timer.setElapsedSeconds(nextState.getElapsedTime());
            updateGUI();
        }
    }

    private void updateGUI() {
        if (gui != null) {
            gui.updateGrid(grid);
        }
    }

    public int getGridSize() {
        return grid.getSize();
    }
} 