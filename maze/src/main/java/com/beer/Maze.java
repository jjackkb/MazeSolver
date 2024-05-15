package com.beer;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Maze {
    private Game game;
    public List<Point> disabledCells = new ArrayList<>();
    public List<Point> visitedCells = new ArrayList<>();
    public Maze(Game newGame) {
        game = newGame;
        game.pos = new Point(0, 0);
        game.start = new Point(game.pos);
        game.end = new Point(game.start);
        
        assert (game.barrierPercentage <= 1.0 && game.barrierPercentage >= 0.0);
        game.win.getContentPane().add(new InputListener(game));
    }
    
    public void genMap() {
        int totalCells = (int) (game.barrierPercentage * (game.gridX * game.gridY));
        Point point1 = newRandomPoint();
        Point point2 = newRandomPoint();

        disabledCells.clear();
        visitedCells.clear();
        game.start.move(point1.x, game.gridY - 1);
        game.end.move(point2.x, 0);
        game.pos.setLocation(game.start);

        for (int i = 0; i < totalCells; i++) {
            Point point = newRandomPoint();
            if (isCellAvailable(point) && !game.end.equals(point) && !game.start.equals(point)) {
                disabledCells.add(point);
            }
        }
        game.movesCount = 0;
        game.win.updateGames();
        visitedCells.clear();
        game.grid.repaint();
    }
    public int getPossibles() {
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
    public void setX(Integer newX) {
        Point newPoint = new Point(newX, game.pos.y);
        if (isCellAvailable(newPoint)) {
            game.pos.setLocation(newPoint);
            visitedCells.add(newPoint);
            game.win.updateMoves();
        }
    }
    public void setY(Integer newY) {
        Point newPoint = new Point(game.pos.x, newY);
        if (isCellAvailable(newPoint)) {
            game.pos.setLocation(newPoint);
            visitedCells.add(newPoint);
            game.win.updateMoves();
        }
    }
    private Point newRandomPoint() {
        int randomX = (int) (Math.random() * game.gridX);
        int randomY = (int) (Math.random() * game.gridY);
        return new Point(randomX, randomY);
    }   
    public boolean isCellAvailable(Point point) {
        if (game.end.equals(point)) {
            game.gameCount++;
            genMap();
            return false;
        } 
        else if (game.start.equals(point)) {
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
}