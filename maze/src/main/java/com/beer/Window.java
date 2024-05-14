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
    private int cell_W;
    private int cell_H;
    protected Grid grid;
    public Window(int sq_X, int sq_Y, int C_w, int C_h) {
        super("Maze"); 
        numSquaresX = sq_X;
        numSquaresY = sq_Y;
        cell_W = C_w;
        cell_H = C_h;
        window_W = (numSquaresX * cell_W) + 50;
        window_H = (numSquaresY * cell_H) + 250;
        
        grid = new Grid(numSquaresX, numSquaresY, cell_W, cell_H);
        setSize(window_W, window_H);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(grid);
        setVisible(true);
    }

    public boolean checkLoc(int x, int y) {
        if (x < numSquaresX && x >= 0)
            if (y < numSquaresY && y >= 0)
                if (grid.checkLoc(x, y))
                    return true;
        return false;
    }
    public int getDist(int x, int y, Point p) {
        return Math.abs(x - p.x) + Math.abs(y - p.y);
    }
    public int getDist(Point p1, Point p2) {
        return Math.abs(p1.x - p2.x) + Math.abs(p1.y - p2.y);
    }
    public int getNumSquaresX() {
        return numSquaresX;
    }
    public int getNumSquaresY() {
        return numSquaresY;
    }
    public int getCell_W() {
        return cell_W;
    }
    public int getCell_H() {
        return cell_H;
    }
    public int getWin_W() {
        return window_W;
    }
    public int getWin_H() {
        return window_H;
    }
}

class Grid extends JPanel {
    private List<Point> enabledCells;
    private List<Point> disabledCells;
    private List<Point> visitedCells;
    private Point start;
    private Point end;
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

        enabledCells = new ArrayList<>();
        disabledCells = new ArrayList<>();
        visitedCells = new ArrayList<>();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //background
        g.setColor(Color.darkGray);
        g.fillRect(sizeWidth, sizeHeight, width, height);
        //start/end Points
        g.setColor(Color.green);
        g.fillRect((sizeWidth+(start.x*sizeWidth)), (sizeHeight+(start.y*sizeHeight)), sizeWidth, sizeHeight);
        g.setColor(Color.red);
        g.fillRect((sizeWidth+(end.x*sizeWidth)), (sizeHeight+(end.y*sizeHeight)), sizeWidth, sizeHeight);

        for (Point fillCell : enabledCells) { //draw enabled cells
            int cellX = sizeWidth + (fillCell.x * sizeWidth);
            int cellY = sizeHeight + (fillCell.y * sizeHeight);
            g.setColor(Color.white);
            g.fillRect(cellX, cellY, sizeWidth, sizeHeight);
        }
        
        for (Point fillCell : visitedCells) { //draw visited cells
            int cellX = sizeWidth + (fillCell.x * sizeWidth);
            int cellY = sizeHeight + (fillCell.y * sizeHeight);
            g.setColor(Color.yellow);
            g.fillRect(cellX, cellY, sizeWidth, sizeHeight);
        }
        
        //outer Rectangle
        g.setColor(Color.BLACK);
        g.drawRect(sizeWidth, sizeHeight, width, height);
        
        //crossed lines
        for (int i = sizeWidth; i <= width; i += sizeWidth) { //horizontal
            g.drawLine(i, sizeHeight, i, height+sizeHeight);
        }
        
        for (int i = sizeHeight; i <= height; i += sizeHeight) { //vertical
            g.drawLine(sizeWidth, i, width+sizeHeight, i); 
        }
    }
    
    public void reload() {
        paintComponent(getGraphics());
    }
    public boolean isEnabled(int x, int y) {
        for (Point p : enabledCells) {
            if (p.x == x && p.y == y) {
                return true;
            }
        }
        return false;
    }
    public boolean isVisited(int x, int y) {
        for (Point p : visitedCells) {
            if (p.x == x && p.y == y) {
                return true;
            }
        }
        return false;
    }
    public boolean checkLoc(int x, int y) {
        if (start.x == x && start.y == y)
            return false;

        for (Point p : enabledCells)
            if (p.x == x && p.y == y)
                return false;

        for (Point p : disabledCells)
            if (p.x == x && p.y == y)
                return false;

        for (Point p : visitedCells)
            if (p.x == x && p.y == y)
                return false;

        return true;
    }

    public void clearEnabled() {
        enabledCells.clear();
    }
    public void setStartCell(Point p) {
        start = new Point(p);
    }
    public void setEndCell(Point p) {
        end = new Point(p);
    }
    public void enableCell(Point p) {
        enabledCells.add(p);
    }
    public void enableCell(int x, int y) {
        enabledCells.add(new Point(x, y));
    }
    public void disableCell(Point p) {
        disabledCells.add(p);
    }
    public void disableCell(int x, int y) {
        disabledCells.add(new Point(x, y));
    }
    public void visitCell(Point p) {
        visitedCells.add(p);
    }
    public void visitCell(int x, int y) {
        visitedCells.add(new Point(x, y));
    }
    public void leaveCell(int x, int y) {
        visitedCells.remove(new Point(x, y));
    }
}