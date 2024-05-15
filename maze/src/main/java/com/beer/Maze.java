package com.beer;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Maze {
    private Game game;
    protected List<Point> disabledCells = new ArrayList<>();
    protected List<Point> visitedCells = new ArrayList<>();
    public Maze(Game newGame) {
        game = newGame;
        game.pos = new Point(0, 0);
        game.start = new Point(game.pos);
        game.end = new Point(game.start);
        
        assert (game.barrierPercentage <= 1.0 && game.barrierPercentage >= 0.0);
        game.win.getContentPane().add(new InputListener(game));
    }
    
    private void genRandomStartEnd() {
        disabledCells.clear();
        visitedCells.clear();
        Point point1 = newRandomPoint();
        Point point2 = newRandomPoint();
        
        game.start.move(point1.x, game.gridY - 1);
        game.pos.setLocation(game.start);
        game.end.move(point2.x, 0);
        visitedCells.clear();
    }
    private void genBarriers() {
        int totalCells = (int) (game.barrierPercentage * (game.gridX * game.gridY));
        
        for (int i = 0; i < totalCells; i++) {
            Point point = newRandomPoint();
            if (isCellAvailable(point) && !game.end.equals(point) && !game.start.equals(point)) {
                disabledCells.add(point);
            }
        }
    }
    protected void newMaze() {
        genRandomStartEnd();
        genBarriers();
        game.grid.repaint();
    }
    protected int getPossibles() {
        int possibles = 0;
        for (int i = -1; i <= 1; i += 2) {
            Point point1 = new Point(game.pos.x + i, game.pos.y);
            Point point2 = new Point(game.pos.x, game.pos.y + i);
            if (isCellAvailable(point1))
            possibles++;
            if (isCellAvailable(point2))
            possibles++;
        }
        return possibles;
    }
    
    //helpers
    protected void setX(Integer newX) {
        Point newPoint = new Point(newX, game.pos.y);
        if (isCellAvailable(newPoint)) {
            game.pos.setLocation(newPoint);
            if (!game.start.equals(game.pos)) {
                visitedCells.add(newPoint);
            }
        }
    }
    protected void setY(Integer newY) {
        Point newPoint = new Point(game.pos.x, newY);
        if (game.maze.isCellAvailable(newPoint)) {
            game.pos.setLocation(newPoint);
            if (!game.start.equals(game.pos)) {
                visitedCells.add(newPoint);
            }
        }
    }
    private Point newRandomPoint() {
        int randomX = (int) (Math.random() * game.gridX);
        int randomY = (int) (Math.random() * game.gridY);
        return new Point(randomX, randomY);
    }   
    protected boolean isDisabled(int x, int y) {
        for (Point point : disabledCells) {
            if (point.x == x && point.y == y) {
                return true;
            }
        }
        return false;
    }
    protected boolean isVisited(int x, int y) {
        for (Point point : visitedCells) {
            if (point.x == x && point.y == y) {
                return true;
            }
        }
        return false;
    }
    protected boolean isCellAvailable(Point point) {
        if (game.end.equals(point)) {
            newMaze();
            return false;
        } 
        else {
            if (point.x < game.gridX && point.x >= 0 && point.y < game.gridY && point.y >= 0) {
                for (Point dC : disabledCells) {
                    if (dC.equals(point)) {
                        return false;
                    }
                }
                for (Point vC : visitedCells) {
                    if (vC.equals(point)) {
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
    protected int getDistance(Point p1, Point p2) {
        return Math.abs(p1.x - p2.x) + Math.abs(p1.y - p2.y);
    }
}