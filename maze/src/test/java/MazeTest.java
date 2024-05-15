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

    @Test
    public void genMap() {
        List<Point> emptyListPoint = new ArrayList<>();
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
        end.move(0, 0);
        pos.move(3, 0); 
        maze.disabledCells.clear();
        maze.visitedCells.clear();
        Point disabledCell = new Point(2, 0);
        maze.disabledCells.add(disabledCell);

        maze.setX(2);
        Point newPointDisable = new Point(pos);
        maze.setX(1);
        Point newPointPass = new Point(pos);
        maze.setX(0);
        Point newPointEnd = new Point(pos);

        Assert.assertNotEquals(newPointDisable, disabledCell);
        Assert.assertEquals(newPointPass, new Point(1, 0));
        Assert.assertEquals(newPointEnd, start);
    }
    @Test
    public void setY() {
        end.move(0, 0);
        Point disabledCell = new Point(0, 2);
        pos.move(0, 3); 
        maze.disabledCells.clear();
        maze.visitedCells.clear();
        maze.disabledCells.add(disabledCell);

        maze.setY(2);
        Point newPointDisable = new Point(pos);
        maze.setY(1);
        Point newPointPass = new Point(pos);
        maze.setY(0);
        Point newPointEnd = new Point(pos);

        Assert.assertNotEquals(newPointDisable, disabledCell);
        Assert.assertEquals(newPointPass, new Point(0, 1));
        Assert.assertEquals(newPointEnd, start);
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
        Assert.assertEquals(pos, start);
    }
}