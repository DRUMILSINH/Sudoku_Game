package com.sudoku.view;

import com.sudoku.controller.GameController;
import com.sudoku.model.SudokuGrid;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GridPanel extends JPanel {
    private final GameController controller;
    private final JTextField[][] cells;
    private final int size;
    private final int subgridSize;
    private Point highlightedCell;
    
    public GridPanel(GameController controller) {
        this.controller = controller;
        this.size = controller.getGridSize();
        this.subgridSize = (int) Math.sqrt(size);
        this.cells = new JTextField[size][size];
        this.highlightedCell = null;
        
        setupGrid();
    }
    
    private void setupGrid() {
        setLayout(new GridLayout(size, size));
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                cells[row][col] = createCell(row, col);
                add(cells[row][col]);
            }
        }
    }
    
    private JTextField createCell(int row, int col) {
        JTextField cell = new JTextField();
        cell.setHorizontalAlignment(JTextField.CENTER);
        cell.setFont(new Font("Arial", Font.BOLD, 20));
        
        // Set borders to create subgrid visual separation
        int top = (row % subgridSize == 0) ? 2 : 1;
        int left = (col % subgridSize == 0) ? 2 : 1;
        int bottom = ((row + 1) % subgridSize == 0) ? 2 : 1;
        int right = ((col + 1) % subgridSize == 0) ? 2 : 1;
        
        cell.setBorder(BorderFactory.createMatteBorder(top, left, bottom, right, Color.BLACK));
        
        cell.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!isValidInput(c)) {
                    e.consume();
                    return;
                }
                
                SwingUtilities.invokeLater(() -> {
                    if (Character.isDigit(c)) {
                        controller.updateCell(row, col, Character.getNumericValue(c));
                    } else if (Character.isLetter(c)) {
                        controller.updateCell(row, col, Character.toUpperCase(c) - 'A' + 10);
                    }
                });
            }
        });
        
        return cell;
    }
    
    private boolean isValidInput(char c) {
        if (size == 9) {
            return Character.isDigit(c) && c != '0';
        } else if (size == 16) {
            return (Character.isDigit(c) && c != '0') || 
                   (Character.toUpperCase(c) >= 'A' && Character.toUpperCase(c) <= 'F');
        }
        return false;
    }
    
    public void updateGrid(SudokuGrid grid) {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                JTextField cell = cells[row][col];
                int value = grid.getValue(row, col);
                
                if (value == 0) {
                    cell.setText("");
                    cell.setEditable(true);
                    cell.setForeground(Color.BLUE);
                } else {
                    String text = (size == 9 || value <= 9) ? 
                                String.valueOf(value) : 
                                Character.toString((char)('A' + value - 10));
                    cell.setText(text);
                    cell.setEditable(!grid.isOriginal(row, col));
                    cell.setForeground(grid.isOriginal(row, col) ? Color.BLACK : Color.BLUE);
                }
            }
        }
        
        if (highlightedCell != null) {
            cells[highlightedCell.x][highlightedCell.y].setBackground(Color.YELLOW);
        }
    }
    
    public void highlightCell(int row, int col) {
        // Clear previous highlight
        if (highlightedCell != null) {
            cells[highlightedCell.x][highlightedCell.y].setBackground(Color.WHITE);
        }
        
        // Set new highlight
        highlightedCell = new Point(row, col);
        cells[row][col].setBackground(Color.YELLOW);
    }
}