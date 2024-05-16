package com.beer.maze;

import java.awt.Dimension;
import java.awt.Point;

public class Maze extends Grid {

    private double barrierPercent;
    public Maze(Dimension grid_Dimension) {
        super(grid_Dimension);
        barrierPercent = 0.15;
    }

    public void fillDisabled() {
        int totalCells = (int) (barrierPercent * (dimension.width * dimension.height));

        for (int i = 0; i < totalCells; i++) {
            Point testPoint = new Point(getRandomInt(dimension.width), getRandomInt(dimension.height));
            if (isPointAvailable(testPoint)) {
                disabled.add(testPoint);
            }
        }
    }

    public void setRandomStartEnd() {
        if (getRandomBoolean()) { //x-axis
            int[] randX = getDiagnalX();
            if (getRandomBoolean()) { //start: top left   end: bottom right
                start.move(randX[0], 0);
                end.move(randX[1], dimension.height - 1);
            }
            else { //start: top right   end: bottom left
                start.move(randX[1], 0);
                end.move(randX[0], dimension.height - 1);
            }
        }
        else { //y-axis
            int[] randY = getDiagnalY();
            if (getRandomBoolean()) { //start: top left-side   end: bottom right-side
                start.move(0, randY[0]);
                end.move(dimension.width - 1, randY[1]);
            }
            else { //start: top right-side   end: bottom left-side
                start.move(0, randY[1]);
                end.move(dimension.width - 1, randY[0]);
            }
        }
    }

    public boolean isPointAvailable(Point point) {
        if (start.equals(point)) {
            return false;
        }
        else {
            if ((point.x < dimension.width && point.x >= 0) && (point.y < dimension.height && point.y >= 0)) {
                for (Point disabledCell : disabled) {
                    if (disabledCell.equals(point)) {
                        return false;
                    }
                }
                for (Point visitedCell : visited) {
                    if (visitedCell.equals(point)) {
                        return false;
                    }
                }
            }
            else {
                return false;
            }
        }
        return true;
    }
    public boolean isFinish() {
        if (player.equals((end))) {
            return true;
        }
        return false;
    }
    private boolean getRandomBoolean() {
        if (Math.random() <= 0.49) {
            return true;
        }
        return false;
    }

    //setter
    public void setBarrierPercent(double percent) {
        barrierPercent = percent;
    }
    public void setX(Integer newX) {
        Point newPoint = new Point(newX, player.y);
        if (isPointAvailable(newPoint)) {
            player.setLocation(newPoint);
            isFinish();
        }
    }
    public void setY(Integer newY) {
        Point newPoint = new Point(player.x, newY);
        if (isPointAvailable(newPoint)) {
            player.setLocation(newPoint);
            isFinish();
        }
    }
}