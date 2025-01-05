package view;

import controller.GameController;
import model.DifficultyLevel;
import javax.swing.*;
import java.awt.*;

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
        
        pack();
        setLocationRelativeTo(null);
    }
    
    private void setupControlPanel() {
        controlPanel.setLayout(new FlowLayout());
        
        JButton newGameBtn = new JButton("New Game");
        JButton checkBtn = new JButton("Check Solution");
        JButton hintBtn = new JButton("Hint");
        JButton undoBtn = new JButton("Undo");
        JButton redoBtn = new JButton("Redo");
        
        newGameBtn.addActionListener(e -> controller.startNewGame());
        checkBtn.addActionListener(e -> controller.checkSolution());
        hintBtn.addActionListener(e -> controller.getHint());
        undoBtn.addActionListener(e -> controller.undo());
        redoBtn.addActionListener(e -> controller.redo());
        
        controlPanel.add(newGameBtn);
        controlPanel.add(checkBtn);
        controlPanel.add(hintBtn);
        controlPanel.add(undoBtn);
        controlPanel.add(redoBtn);
        controlPanel.add(timerLabel);
    }
    
    private void setupMenu() {
        JMenuBar menuBar = new JMenuBar();
        
        JMenu gameMenu = new JMenu("Game");
        JMenu difficultyMenu = new JMenu("Difficulty");
        
        for (DifficultyLevel level : DifficultyLevel.values()) {
            JMenuItem item = new JMenuItem(level.name());
            item.addActionListener(e -> controller.setDifficulty(level));
            difficultyMenu.add(item);
        }
        
        gameMenu.add(difficultyMenu);
        menuBar.add(gameMenu);
        
        setJMenuBar(menuBar);
    }
    
    public void updateTimer(String time) {
        timerLabel.setText(time);
    }
    
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
    
    public GridPanel getGridPanel() {
        return gridPanel;
    }
} 