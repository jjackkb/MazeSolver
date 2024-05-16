package com.beer.maze;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Grid {

    private Dimension dimension;
    private Point player;
    private Point start;
    private Point end;
    private List<Point> disabled;
    private List<Point> visited;
    public Grid(Dimension size) {
        dimension = size;
        player = new Point(0, 0);
        start = new Point(0, 0);
        end = new Point (0, 0);
        disabled = new ArrayList<>();
        visited = new ArrayList<>();
    }

    public Dimension getDimension() {
        return dimension;
    }
    public Point getStart() {
        return start;
    }
    public Point getEnd() {
        return end;
    }
    public List<Point> getDisabled() {
        return disabled;
    }
    public List<Point> getVisited() {
        return visited;
    }
}
