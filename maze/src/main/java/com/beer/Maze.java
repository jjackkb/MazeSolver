package com.beer;
import java.awt.Point;

public class Maze {
    private int x;
    private int y;
    private Point start;
    private Point end;
    protected Window win;
    private InputListener inputListener;
    public Maze(double randPercent, Window window) {
        assert (randPercent <= 1.0 && randPercent >= 0.0);

        win = window;
        inputListener = new InputListener(this);
        x = 0; 
        y = 0;

        win.getContentPane().add(inputListener);
        genRandomStartEnd();
        genBarriers(randPercent);
    }

    public void genRandomStartEnd() {
        int randomX1 = (int) (Math.random() * win.getNumSquaresX()-2) + 1;
        int randomX2 = (int) (Math.random() * win.getNumSquaresX()-2) + 1;

        Point start = new Point(randomX1, win.getNumSquaresY()-1);
        Point end = new Point(randomX2, 0);

        this.start = start;
        this.end = end;
        win.setStartCell(start);
        win.setEndCell(end);
    }

    public void genBarriers(double percent) {
        int max = (int) (percent*(win.getNumSquaresX()*win.getNumSquaresY()));

        for (int i = 0; i < max; i++) {
            int randX = (int) (Math.random() * win.getNumSquaresX());
            int randY = (int) (Math.random() * win.getNumSquaresY());

            if (win.checkLoc(randX, randY) && !checkFinish(randX, randY)) {
                win.disableCell(new Point(randX, randY));
            }
        }
    }

    public void startPlay() {
        x = start.x;
        y = start.y;
    }

    public boolean checkFinish() {
        if (x == end.x && y == end.y) {
           return true;
        }
        return false;
    }
    public boolean checkFinish(int x, int y) {
        if (x == end.x && y == end.y) {
           return true;
        }
        return false;
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    public void setX(int newX) {
        if (!win.checkLoc(newX, y)) {
            return;
        }
        else {
            x = newX;
            win.visitCell(new Point(x, y));
        }
        win.reload();
        checkFinish();
    }
    public void setY(int newY) {
        if (!win.checkLoc(x, newY)) {
            return;
        }
        else {
            y = newY;
            win.visitCell(new Point(x, y));
        }
        win.reload();
        checkFinish();
    }
}