package com.beer;
import java.awt.Point;
import java.util.List;
import java.util.ArrayList;

public class Game {
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
    public Game(int gridWidth, int gridHeight, int cellWidth, int cellHeight) {
        barrierPercentage = 0.05;
        gridX = gridWidth;
        gridY = gridHeight;
        cell_width = cellWidth;
        cell_height = cellHeight;
        grid_width = gridX * cell_width;
        grid_height = gridY * cell_height;
        window_width = grid_width + (2 * cell_width);
        window_height = grid_height + 250;

        win = new Window(this);
        maze = new Maze(this);
        win.start();
    }

    public Grid getGrid() { return grid; }
    public List<Point> getPoints() {
        return new ArrayList<>() {{ add(start); add(end); add(pos); }};
    }
}
