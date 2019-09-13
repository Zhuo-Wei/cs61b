package bearmaps;
import java.util.Arrays;
import java.util.List;
public class NaivePointSet implements PointSet {
    public List<Point> naivePointSet;

    public NaivePointSet(List<Point> points) {
        this.naivePointSet = points;
    }

    public Point nearest(double x, double y) {
        Point target = new Point(x, y);
        Point firstPoint = naivePointSet.get(0);
        double distance = Point.distance(firstPoint, target);
        int smallest = 0;
        for (int i = 1; i < naivePointSet.size(); i++) {
            double temp = Point.distance(naivePointSet.get(i), target);
            if (temp <= distance) {
                distance = temp;
                smallest = i;
            }
        }
        return naivePointSet.get(smallest);
    }

    public static void main(String[] args) {
        Point p1 = new Point(1.1, 2.2); // constructs a Point with x = 1.1, y = 2.2
        Point p2 = new Point(3.3, 4.4);
        Point p3 = new Point(-2.9, 4.2);


        NaivePointSet nn = new NaivePointSet(Arrays.asList(p1,p2,p3));
        Point ret = nn.nearest(3.0, 4.0); // returns p2
        System.out.println(ret);
        ret.getX(); // evaluates to 3.3
        ret.getY(); // evaluates to 4.4
    }
}

