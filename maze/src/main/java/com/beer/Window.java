package com.beer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Point;
import java.awt.Graphics;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Window extends JFrame {
    private double barrierPercent;
    private int numSquaresX;
    private int numSquaresY;
    private int window_W;
    private int window_H;
    private int cell_W;
    private int cell_H;
    protected Grid grid;
    protected Maze maze;
    public Window(int sq_X, int sq_Y, int C_w, int C_h, double percent) {
        super("Maze"); 
        barrierPercent = percent; 
        numSquaresX = sq_X;
        numSquaresY = sq_Y;
        cell_W = C_w;
        cell_H = C_h;
        window_W = (numSquaresX * cell_W) + (2*cell_W);
        window_H = (numSquaresY * cell_H) + 250;
        
        grid = new Grid(numSquaresX, numSquaresY, cell_W, cell_H);
        maze = new Maze(barrierPercent, this);
        JButton resetButton = new JButton("reset");

        resetButton.setBounds((window_W/2)-40, window_H-75, (window_W/2)+40, window_H-50); 
        resetButton.setSize(80, 35); 
        setSize(window_W, window_H);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        resetButton.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){  
                        resetMaze();
                    }  
                });  

        add(resetButton);
        add(grid);

        maze.startPlay();
        setVisible(true);
    }

    public void resetMaze() {
        grid.disabledCells.clear();
        maze = new Maze(barrierPercent, this);
        grid.enabledCells.clear();
        grid.visitedCells.clear();
        maze.startPlay();
    }
    public void reload() {
        grid.paintComponent(getGraphics());
    }

    public boolean checkLoc(int x, int y) {
        if (x < numSquaresX && x >= 0)
            if (y < numSquaresY && y >= 0)
                if (grid.checkLoc(x, y))
                    return true;
        return false;
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

    public void setStartCell(Point p) {
        grid.start = p;
    }
    public void setEndCell(Point p) {
        grid.end = p;
    }
    public void disableCell(Point p) {
        grid.disabledCells.add(p);
    }
    public void visitCell(Point p) {
        grid.visitedCells.add(p);
    }
}

class Grid extends JPanel {
    protected List<Point> enabledCells;
    protected List<Point> disabledCells;
    protected List<Point> visitedCells;
    protected Point start;
    protected Point end;
    protected int width;
    protected int height;
    protected int sqSizeWidth;
    protected int sqSizeHeight;
    protected int sqNumWidth;
    protected int sqNumHeight;
    public Grid(int sqX, int sqY, int sWidth, int sHeight) {
        super();
        sqNumWidth = sqX;
        sqNumHeight = sqY;
        sqSizeWidth = sWidth;
        sqSizeHeight = sHeight;
        width = sqX * sqSizeWidth;
        height = sqY * sqSizeHeight;

        enabledCells = new ArrayList<>();
        disabledCells = new ArrayList<>();
        visitedCells = new ArrayList<>();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //background
        g.setColor(new Color(253,255,252));
        g.fillRect(sqSizeWidth, sqSizeHeight, width, height);
        //start/end Points
        g.setColor(new Color(76,185,68));
        g.fillRect((sqSizeWidth+(start.x*sqSizeWidth)), (sqSizeHeight+(start.y*sqSizeHeight)), sqSizeWidth, sqSizeHeight);
        g.setColor(new Color(171,52,40));
        g.fillRect((sqSizeWidth+(end.x*sqSizeWidth)), (sqSizeHeight+(end.y*sqSizeHeight)), sqSizeWidth, sqSizeHeight);

        for (Point fillCell : disabledCells) { //draw disabled cells
            int cellX = sqSizeWidth + (fillCell.x * sqSizeWidth);
            int cellY = sqSizeHeight + (fillCell.y * sqSizeHeight);
            g.setColor(Color.BLACK);
            g.fillRect(cellX, cellY, sqSizeWidth, sqSizeHeight);
        }

        for (Point fillCell : visitedCells) { //draw visited cells
            int cellX = sqSizeWidth + (fillCell.x * sqSizeWidth);
            int cellY = sqSizeHeight + (fillCell.y * sqSizeHeight);
            g.setColor(new Color(244,158,76));
            g.fillRect(cellX, cellY, sqSizeWidth, sqSizeHeight);
        }
        
        //outer Rectangle
        g.setColor(new Color(42,43,42));
        g.drawRect(sqSizeWidth, sqSizeHeight, width, height);
        //crossed lines
        for (int i = sqSizeWidth; i <= width; i += sqSizeWidth) { //horizontal
            g.drawLine(i, sqSizeHeight, i, height+sqSizeHeight);
        }
        for (int i = sqSizeHeight; i <= height; i += sqSizeHeight) { //vertical
            g.drawLine(sqSizeWidth, i, width+sqSizeHeight, i); 
        }
    }

    public boolean isEnabled(int x, int y) {
        for (Point p : enabledCells) {
            if (p.x == x && p.y == y) {
                return true;
            }
        }
        return false;
    }
    public boolean isDisabled(int x, int y) {
        for (Point p : disabledCells) {
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
}