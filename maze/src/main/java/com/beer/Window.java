package com.beer;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Point;
import java.awt.Graphics;
import java.awt.Color;

import java.util.ArrayList;
import java.util.List;

public class Window extends JFrame {
    
    private int numSquaresX;
    private int numSquaresY;
    private int window_W;
    private int window_H;
    private Grid grid;
    
    public Window(int sq_X, int sq_Y) {
        super("Maze");
        
        numSquaresX = sq_X;
        numSquaresY = sq_Y;
        window_W = (numSquaresX * 25) + 50;
        window_H = (numSquaresY * 25) + 150;
        
        grid = new Grid(numSquaresX, numSquaresY, 25, 25);

        setSize(window_W, window_H);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(grid);

        setVisible(true);
    }

    public void disableCell(int x, int y) {
        grid.disableCell(x, y);
    }

    public void visitCell(int x, int y) {
        grid.visitCell(x, y);
    }

    public void leaveCell(int x, int y) {
        grid.leaveCell(x, y);
    }

    public int getNumSquaresX() {
        return numSquaresX;
    }

    public int getNumSquaresY() {
        return numSquaresY;
    }
}

class Grid extends JPanel {
    
    private List<Point> disabledCells;
    private List<Point> visitedCells;
    private int width;
    private int height;
    private int sizeWidth;
    private int sizeHeight;
    
    public Grid(int sqX, int sqY, int sWidth, int sHeight) {
        super();
        sizeWidth = sWidth;
        sizeHeight = sHeight;

        width = sqX * sizeWidth;
        height = sqY * sizeHeight;

        disabledCells = new ArrayList<>();
        visitedCells = new ArrayList<>();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        for (Point fillCell : disabledCells) { //draw disabled cells
            int cellX = sizeWidth + (fillCell.x * sizeWidth);
            int cellY = sizeHeight + (fillCell.y * sizeHeight);
            g.setColor(Color.darkGray);
            g.fillRect(cellX, cellY, sizeWidth, sizeHeight);
        }
        
        for (Point fillCell : visitedCells) { //draw visited cells
            int cellX = sizeWidth + (fillCell.x * sizeWidth);
            int cellY = sizeHeight + (fillCell.y * sizeHeight);
            g.setColor(Color.yellow);
            g.fillRect(cellX, cellY, sizeWidth, sizeHeight);
        }
        
        g.setColor(Color.BLACK);
        g.drawRect(sizeWidth, sizeHeight, width, height);
        
        for (int i = sizeWidth; i <= width; i += sizeWidth) {
            g.drawLine(i, sizeHeight, i, height+sizeHeight);
        }
        
        for (int i = sizeHeight; i <= height; i += sizeHeight) {
            g.drawLine(sizeWidth, i, width+sizeHeight, i);
        }
    }
    
    public void disableCell(int x, int y) {
        disabledCells.add(new Point(x, y));
        repaint();
    }
    
    public void visitCell(int x, int y) {
        visitedCells.add(new Point(x, y));
        repaint();
    }

    public void leaveCell(int x, int y) {
        for (Point p : visitedCells)
            if(p.x == x && p.y == y)
                visitedCells.remove(p);
    }
}