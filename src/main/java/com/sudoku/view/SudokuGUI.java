package com.sudoku.view;

import com.sudoku.controller.GameController;
import com.sudoku.model.DifficultyLevel;
import com.sudoku.model.SudokuGrid;
import javax.swing.*;
import java.awt.*;
import java.io.InputStream;
import java.util.Properties;

public class SudokuGUI extends JFrame {
    private final GameController controller;
    private final GridPanel gridPanel;
    private final JPanel controlPanel;
    private final JLabel timerLabel;
    
    public SudokuGUI(GameController controller) {
        this.controller = controller;
        this.gridPanel = new GridPanel(controller);
        this.controlPanel = new JPanel();
        this.timerLabel = new JLabel("00:00");
        
        setupUI();
    }
    
    private void setupUI() {
        setTitle("Sudoku Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        
        // Add grid panel
        add(gridPanel, BorderLayout.CENTER);
        
        // Setup control panel
        setupControlPanel();
        add(controlPanel, BorderLayout.SOUTH);
        
        // Setup menu
        setupMenu();
        
        // Apply default theme
        try {
            InputStream themeStream = getClass().getResourceAsStream("/themes/light-theme.properties");
            Properties themeProps = new Properties();
            themeProps.load(themeStream);
            // Apply theme properties
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception e) {
            System.err.println("Error loading theme: " + e.getMessage());
        }
        
        pack();
        setLocationRelativeTo(null);
    }
    
    private void setupControlPanel() {
        controlPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
        
        JButton newGameBtn = new JButton("New Game");
        JButton hintBtn = new JButton("Hint");
        JButton undoBtn = new JButton("Undo");
        JButton redoBtn = new JButton("Redo");
        JButton checkBtn = new JButton("Check Solution");
        
        newGameBtn.addActionListener(e -> controller.startNewGame());
        hintBtn.addActionListener(e -> controller.provideHint());
        undoBtn.addActionListener(e -> controller.undo());
        redoBtn.addActionListener(e -> controller.redo());
        checkBtn.addActionListener(e -> controller.checkWinCondition());
        
        controlPanel.add(newGameBtn);
        controlPanel.add(hintBtn);
        controlPanel.add(undoBtn);
        controlPanel.add(redoBtn);
        controlPanel.add(checkBtn);
        controlPanel.add(new JLabel("Time:"));
        controlPanel.add(timerLabel);
    }
    
    private void setupMenu() {
        JMenuBar menuBar = new JMenuBar();
        
        // Game menu
        JMenu gameMenu = new JMenu("Game");
        JMenuItem saveItem = new JMenuItem("Save");
        JMenuItem loadItem = new JMenuItem("Load");
        JMenuItem exitItem = new JMenuItem("Exit");
        
        saveItem.addActionListener(e -> controller.saveGame());
        loadItem.addActionListener(e -> controller.loadGame());
        exitItem.addActionListener(e -> {
            controller.saveGame();
            System.exit(0);
        });
        
        // Difficulty menu
        JMenu difficultyMenu = new JMenu("Difficulty");
        for (DifficultyLevel level : DifficultyLevel.values()) {
            JMenuItem item = new JMenuItem(level.name());
            item.addActionListener(e -> {
                controller.setDifficulty(level);
                controller.startNewGame();
            });
            difficultyMenu.add(item);
        }
        
        gameMenu.add(difficultyMenu);
        gameMenu.addSeparator();
        gameMenu.add(saveItem);
        gameMenu.add(loadItem);
        gameMenu.addSeparator();
        gameMenu.add(exitItem);
        
        menuBar.add(gameMenu);
        setJMenuBar(menuBar);
    }
    
    public void updateGrid(SudokuGrid grid) {
        gridPanel.updateGrid(grid);
    }
    
    public void updateTimer(String time) {
        timerLabel.setText(time);
    }
    
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
    
    public void highlightCell(int row, int col) {
        gridPanel.highlightCell(row, col);
    }
} 