package com.sudoku.main;

import com.sudoku.controller.GameController;
import com.sudoku.view.SudokuGUI;

import javax.swing.SwingUtilities;

public class SudokuGame {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameController controller = new GameController();
            SudokuGUI gui = new SudokuGUI(controller);
            controller.setGUI(gui);
            controller.startNewGame();
            gui.setVisible(true);
        });
    }
}