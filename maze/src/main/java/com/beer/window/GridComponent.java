package com.beer.window;

import com.beer.maze.Grid;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Color;

public class GridComponent extends JPanel {
    
    private final Color BACKGROUND = new Color(253, 255, 252);
    private final Color START = new Color(76, 185, 68);
    private final Color END = new Color(171, 52, 40);
    private final Color VISITED = new Color(244, 158, 76);
    private final Color DISABLED = Color.BLACK;
    private final Color LINE = new Color(42, 43, 42);
    private Dimension gridDimension;
    private Point start;
    private Point end;
    private Grid grid;
    private int cell_width;
    private int cell_height;
    public GridComponent(Grid superGrid, int cellWidth, int cellHeight) {
        grid = superGrid;
        cell_width = cellWidth;
        cell_height = cellHeight;
        gridDimension = grid.getDimension();
        start = grid.getStart();
        end = grid.getEnd();
    }

    public void paint(Graphics g) {
        paintBackground(g);
        paintStart(g);
        paintEnd(g);
        paintDisabled(g);
        paintVisited(g);
        paintGrid(g);
    }

    public void paintBackground(Graphics g) {
        g.setColor(BACKGROUND);
        g.fillRect(cell_width, cell_height, gridDimension.width, gridDimension.height);
    }
    public void paintStart(Graphics g) {
        g.setColor(START);
        g.fillRect((cell_width + (start.x * cell_width)), (cell_height + (start.y * cell_height)), cell_width, cell_height);
    }
    public void paintEnd(Graphics g) {
        g.setColor(END);
        g.fillRect((cell_width + (end.x * cell_width)), (cell_height + (end.y * cell_height)), cell_width, cell_height);
    }
    public void paintDisabled(Graphics g) {
        for (Point fillCell : grid.getDisabled()) { 
            int cellX = cell_width + (fillCell.x * cell_width);
            int cellY = cell_height + (fillCell.y * cell_height);
            g.setColor(DISABLED);
            g.fillRect(cellX, cellY, cell_width, cell_height);
        }
    } 
    public void paintVisited(Graphics g) {
        for (Point fillCell : grid.getVisited()) { 
            if (!start.equals(fillCell)) {
                int cellX = cell_width + (fillCell.x * cell_width);
                int cellY = cell_height + (fillCell.y * cell_height);
                g.setColor(VISITED);
                g.fillRect(cellX, cellY, cell_width, cell_height);
            }
        }
    }
    public void paintGrid(Graphics g) {
        g.setColor(LINE);
        g.drawRect(cell_width, cell_height, grid.getDimension().width, grid.getDimension().height);
        for (int i = cell_width; i <= grid.getDimension().width; i += cell_width) { //horizontal
            g.drawLine(i, cell_height, i, grid.getDimension().height + cell_height);
        }
        for (int i = cell_height; i <= grid.getDimension().height; i += cell_height) { //vertical
            g.drawLine(cell_width, i, grid.getDimension().width + cell_height, i); 
        }
    }
} 