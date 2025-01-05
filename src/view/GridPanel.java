package view;

import controller.GameController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GridPanel extends JPanel {
    private final GameController controller;
    private final JTextField[][] cells;
    private final int size;
    private final int subgridSize;
    
    public GridPanel(GameController controller) {
        this.controller = controller;
        this.size = controller.getGridSize();
        this.subgridSize = (int) Math.sqrt(size);
        this.cells = new JTextField[size][size];
        
        setupGrid();
    }
    
    private void setupGrid() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                gbc.gridx = col;
                gbc.gridy = row;
                
                JTextField cell = createCell(row, col);
                cells[row][col] = cell;
                add(cell, gbc);
            }
        }
    }
    
    private JTextField createCell(int row, int col) {
        JTextField cell = new JTextField(2);
        cell.setHorizontalAlignment(JTextField.CENTER);
        cell.setFont(new Font("Arial", Font.BOLD, 20));
        cell.setPreferredSize(new Dimension(50, 50));
        
        // Add borders
        int top = row % subgridSize == 0 ? 2 : 1;
        int left = col % subgridSize == 0 ? 2 : 1;
        int bottom = row == size - 1 ? 2 : 1;
        int right = col == size - 1 ? 2 : 1;
        
        cell.setBorder(BorderFactory.createMatteBorder(top, left, bottom, right, Color.BLACK));
        
        // Add input listeners
        cell.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!isValidInput(c)) {
                    e.consume();
                    return;
                }
                
                SwingUtilities.invokeLater(() -> {
                    int value = Character.getNumericValue(c);
                    controller.updateCell(row, col, value);
                });
            }
        });
        
        return cell;
    }
    
    private boolean isValidInput(char c) {
        if (!Character.isDigit(c)) return false;
        int value = Character.getNumericValue(c);
        return value >= 0 && value <= size;
    }
    
    public void updateCell(int row, int col, int value, boolean isOriginal) {
        JTextField cell = cells[row][col];
        cell.setText(value == 0 ? "" : String.valueOf(value));
        cell.setForeground(isOriginal ? Color.BLACK : Color.BLUE);
        cell.setEditable(!isOriginal);
    }
    
    public void highlightError(int row, int col) {
        cells[row][col].setBackground(new Color(255, 200, 200));
    }
    
    public void clearHighlight(int row, int col) {
        cells[row][col].setBackground(Color.WHITE);
    }
} 