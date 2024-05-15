import org.junit.Assert;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import com.beer.Maze;
import com.beer.Game;
public class MazeTest {
    private Game game = new Game(10, 10, 5, 5);
    private Maze maze = new Maze(game);
    private List<Point> points = game.getPoints();
    private Point start = points.get(0);
    private Point end = points.get(1);
    private Point pos = points.get(2);
    private List<Point> emptyListPoint = new ArrayList<>();

    @Test
    public void genRandomStartEnd() {
        maze.disabledCells = new ArrayList<Point>() {{
            add(new Point(0, 3));
            add(new Point(6, 7));
            add(new Point(3, 4)); }};
        maze.visitedCells = new ArrayList<Point>() {{
            add(new Point(0, 0));
            add(new Point(0, 1));
            add(new Point(1, 1)); }};
        List<Point> lastDisabled = new ArrayList<>(maze.disabledCells);
        start.move(5, 5);
        
        maze.genMap();

        Assert.assertEquals(emptyListPoint, maze.visitedCells);
        Assert.assertEquals(pos, start);
        Assert.assertEquals(0, end.y);
        Assert.assertEquals(9, start.y);
        Assert.assertNotEquals(lastDisabled, maze.disabledCells);
        Assert.assertNotNull(maze.disabledCells);
    }
    @Test
    public void getPossibles() {
        start.move(0, 0);
        end.move(9, 0);
        maze.disabledCells.clear();
        
        int poss = maze.getPossibles();

        Assert.assertEquals(2, poss);
    }
    @Test
    public void setX() {
        pos.move(5, 5);
        maze.disabledCells.clear();
        maze.visitedCells.clear();

        maze.setX(6);

        Assert.assertEquals(6, pos.x);
        Assert.assertEquals(5, pos.y);
    }
    @Test
    public void setY() {
        pos.move(5, 5);
        maze.disabledCells.clear();
        maze.visitedCells.clear();

        maze.setY(4);

        Assert.assertEquals(5, pos.x);
        Assert.assertEquals(4, pos.y);
    }
    @Test
    public void isCellAvailable() {
        Point startPos = new Point(0, 0);
        Point endPos = new Point(1, 0);
        Point disabledCell = new Point(1, 1);
        Point visitedCell = new Point(2, 2);
        maze.disabledCells.clear();
        maze.visitedCells.clear();
        maze.disabledCells.add(new Point(disabledCell));
        maze.visitedCells.add(new Point(visitedCell));
        start.move(0, 0);
        end.move(1, 0);
        pos.move(5, 5);

        boolean posTrue = maze.isCellAvailable(pos);
        boolean disabledCellFalse = maze.isCellAvailable(disabledCell);
        boolean visitedCellFalse = maze.isCellAvailable(visitedCell);
        boolean startFalse = maze.isCellAvailable(startPos);
        boolean endFalse = maze.isCellAvailable(endPos);

        Assert.assertTrue(posTrue);
        Assert.assertFalse(disabledCellFalse);
        Assert.assertFalse(visitedCellFalse);
        Assert.assertFalse(startFalse);
        Assert.assertFalse(endFalse);
    }
}
