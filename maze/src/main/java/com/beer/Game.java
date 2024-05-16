package com.beer;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;

public class Game {
    protected Integer gameCount;
    protected Integer movesCount;
    protected double barrierPercentage;
    protected int window_width;
    protected int window_height;
    protected int grid_width;
    protected int grid_height;
    protected int cell_width;
    protected int cell_height;
    protected int gridX;
    protected int gridY;
    protected Point pos;
    protected Point start;
    protected Point end;
    protected Maze maze;
    protected Window win;
    protected Grid grid;
    public Game(int gridWidth, int gridHeight, int cellWidth, int cellHeight, double percent) {
        barrierPercentage = percent;
        gameCount = 0;
        movesCount = 0;
        gridX = gridWidth;
        gridY = gridHeight;
        cell_width = cellWidth;
        cell_height = cellHeight;
        grid_width = gridX * cell_width;
        grid_height = gridY * cell_height;
        window_width = grid_width + (3 * cell_width);
        window_height = grid_height + 150;

        win = new Window(this);
        maze = new Maze(this);
        win.start();
    }

    public ActionListener getResetActionListener() {
        return new ActionListener() { //reset button listener
            public void actionPerformed(ActionEvent e){  
                maze.genMap();
            }}; 
    }
    public Integer getGames() {
        return gameCount;
    }
    public Integer getMoves() {
        return movesCount;
    }
    public Grid getGrid() { return grid; }
    public List<Point> getPoints() { return new ArrayList<>() {{ add(start); add(end); add(pos); }}; }
}