package bearmaps;

import edu.princeton.cs.algs4.Stopwatch;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class KDTreeTest {
    private static Random r = new Random(500);

    private static KDTree buildTree() {
        Point p1 = new Point(2, 3); // constructs a Point with x = 1.1, y = 2.2
        Point p2 = new Point(4, 2);
        Point p3 = new Point(4, 2);
        Point p4 = new Point(4, 5);
        Point p5 = new Point(3, 3);
        Point p6 = new Point(1, 5);
        Point p7 = new Point(4, 4);
        KDTree nn = new KDTree(Arrays.asList(p1, p2, p3, p4, p5, p6, p7));
        return nn;
    }

    @Test
    public void naiveTestNearest() {
        KDTree kd = buildTree();
        Point goal = new Point(5, 1);
        Point result = kd.nearest(4, 6);
        Point ans = new Point(4, 5);
        assertEquals(ans, result);
    }

    private Point randomPoint() {
        double x = r.nextDouble();
        double y = r.nextDouble();
        Point p = new Point(x, y);
        return p;
    }

    private List<Point> randomPoints(int n) {
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            points.add(randomPoint());
        }
        return points;
    }
    @Test
    public void testNearest() {
        List<Point> ps = randomPoints(10000);
        KDTree kd = new KDTree(ps);
        NaivePointSet nps = new NaivePointSet(ps);

        List<Point> targets = randomPoints(200);
        for (Point p: targets) {
            Point ans = nps.nearest(p.getX(),p.getY());
            Point act = kd.nearest(p.getX(),p.getY());
            assertEquals(ans, act);
        }
    }
    public void timekd (int pointCount, int queryCount) {
        List<Point> ps = randomPoints(pointCount);
        KDTree kd = new KDTree(ps);
        NaivePointSet nps = new NaivePointSet(ps);

        List<Point> targets = randomPoints(queryCount);
        Stopwatch sw = new Stopwatch();
        for (Point p : targets) {
            kd.nearest(p.getX(), p.getY());

        }
        double time =sw.elapsedTime();
        System.out.println("kd: time elapsed for" + queryCount + "queries on" + pointCount + " points: '" + time);

        sw = new Stopwatch();
        for (Point p : targets) {
            //Point act = kd.nearest(p.getX(), p.getY());
            nps.nearest(p.getX(),p.getY());
        }
        time = sw.elapsedTime();
        System.out.println("naive: time elapsed for" + queryCount + "queries on" + pointCount + " points: '" + time);

    }

    @Test
    public void time2() {
        timekd(100000,2000);
    }
        //
        //Point ans = nps.nearest(p.getX(),p.getY());

}