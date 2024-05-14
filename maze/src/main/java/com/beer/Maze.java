package com.beer;
import java.awt.Point;
import java.util.ArrayList;

public class Maze {
    private int x;
    private int y;
    private Point start;
    private Point end;
    private InputListener inputListener;
    protected Window win;
    public Maze(int sq_X, int sq_Y, int cell_W, int cell_H) {
        inputListener = new InputListener(this);
        win = new Window(sq_X, sq_Y, cell_H, cell_W);
        x = 0;
        y = 0;

        win.getContentPane().add(inputListener);
        genRandomStartEnd();
    }

    public void genRandomStartEnd() {
        int randomX1 = (int) (Math.random() * win.getNumSquaresX()-2) + 1;
        int randomX2 = (int) (Math.random() * win.getNumSquaresX()-2) + 1;

        Point start = new Point(randomX1, win.getNumSquaresY()-1);
        Point end = new Point(randomX2, 0);

        this.start = start;
        this.end = end;
        win.grid.setStartCell(start);
        win.grid.setEndCell(end);
    }

    public void startPlay() {
        x = start.x;
        y = start.y;
    }

    public void genShortPath() {
        int initDist = win.getDist(start, end);
        int length = (int) (Math.random() * (win.getNumSquaresX()*win.getNumSquaresY()-initDist)) + initDist;
        System.out.println(length);
        Point randPoint = start;
        ArrayList<Point> possibles;

        for (int i = 0; i < length; i++) {
            possibles = getPossibles(randPoint, length-i);
            Point min = randPoint;

            for (Point p : possibles)
                if (win.getDist(p, end) < win.getDist(min, end))
                    min = p;

            randPoint = min;

            if (randPoint.x == end.x && randPoint.y == end.y) {
                System.out.println("Found end point in " + i + " moves");
                return;
            }

            System.out.println(i);
            win.grid.enableCell(randPoint);
            win.grid.reload();
            try {
                Thread.sleep(050);
            } catch (Exception e) {System.out.println(e);}
        }
    }

    private ArrayList<Point> getPossibles(Point p, int length) {
        ArrayList<Point> arr = new ArrayList<>();

        for (int x = -1; x <= 1; x += 2) {
            if (win.checkLoc(p.x + x, p.y))
                if (length > win.getDist(p.x + x, p.y, end))
                    arr.add(new Point(p.x + x, p.y));
            if (win.checkLoc(p.x, p.y + x))
                if (length > win.getDist(p.x, p.y + x, end))
                    arr.add(new Point(p.x, p.y + x));
        }

        return arr;
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public void setX(int newX) {
        int t = x;
        x = newX;

        if (win.grid.isVisited(x, y)) {
            x = t;
        }
        else {
            win.grid.visitCell(x, y);
        }
        win.grid.reload();
    }
    public void setY(int newY) {
        int t = y;
        y = newY;

        if (win.grid.isVisited(x, y)) {
            y = t;
        }
        else {
            win.grid.visitCell(x, y);
        }
        win.grid.reload();
    }
}