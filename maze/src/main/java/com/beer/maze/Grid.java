package com.beer.maze;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Grid {

    protected Dimension dimension;
    protected Point player;
    protected Point start;
    protected Point end;
    protected List<Point> disabled;
    protected List<Point> visited;
    public Grid(Dimension size) {
        dimension = size;
        player = new Point(0, 0);
        start = new Point(0, 0);
        end = new Point (0, 0);
        disabled = new ArrayList<>();
        visited = new ArrayList<>();
    }

    public int[] getDiagnalX() {
        int[] arr = new int[2];
        arr[0] = getRandomInt(dimension.width / 2);
        arr[1] = getRandomInt(dimension.width / 2) + dimension.width / 2;

        return arr;
    }
    public int[] getDiagnalY() {
        int[] arr = new int[2];
        arr[0] = getRandomInt(dimension.height / 2);
        arr[1] = getRandomInt(dimension.height / 2) + dimension.height / 2;

        return arr;
    }

    public int getRandomInt(int max) {
        return (int) (Math.random() * max);
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