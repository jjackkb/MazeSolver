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
        List<Point> points = game.getPoints();
        Point start = points.get(0);
        Point end = points.get(1);
        Point pos = points.get(2);
        start.move(5, 5);
        
        maze.genRandomStartEnd();

        Assert.assertEquals(emptyListPoint, maze.disabledCells);
        Assert.assertEquals(emptyListPoint, maze.visitedCells);
        Assert.assertEquals(pos, start);
        Assert.assertEquals(0, end.y);
        Assert.assertEquals(9, start.y);
    }
}
