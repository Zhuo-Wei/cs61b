package bearmaps;
import java.util.Arrays;
import java.util.List;

public class KDTree implements PointSet {
    public List<Point> pointList;
    //public Point best;
    //public int size;
    private Node root;
    private static final boolean horizontal = false;
    private static final boolean vertical = true;

    private class Node {
        private Point point;
        private boolean hORv;
        /* Children of this Node. */
        private Node left; // left and down
        private Node right; // right and up

        private Node(Point p, boolean hv) {
            point = p;
            hORv = hv;
        }

        public Point getPoint() {
            return point;
        }

        public Node getLeft() {
            return left;
        }

        public Node getRight() {
            return right;
        }

        public boolean getOrientation() {
            return hORv;
        }
    }

    public KDTree(List<Point> points) {
        pointList = points;

        //cite
        for (Point point : points) {
            root = add(point, root, horizontal);
        }

    }

    private Node add(Point p, Node n, boolean orientation) {
        if(n == null) {
           // return null;
            return new Node(p, orientation);
        }
        if(p.equals(n.getPoint())) {
            return n;
        }
        if (compare(p, n.getPoint(), orientation) < 0) {
            n.left = add(p, n.getLeft(), !orientation);
        }
        else {
            n.right = add(p, n.getRight(), !orientation);
        }
        return n;
    }

    private int compare (Point p1, Point p2, boolean orientation) {
        if (orientation == horizontal) {
            return Double.compare(p1.getX(), p2.getX());
        }
        else return Double.compare(p1.getY(), p2.getY());
    }

    private Node nearestHelper (Point target, Node best, Node n) {
        if (n == null) {
            return best;
        }
        if (Double.compare(n.getPoint().distance(n.point, target), best.getPoint().distance(best.getPoint(), target)) < 0) {
            best = n;
        }
        Node goodSide = n;
        Node badSide= n;
        int cmp = compare(target, n.getPoint(), n.getOrientation());
        if (cmp < 0) {
            //return nearestHelper1(target, best, n.getLeft());
            goodSide = n.getLeft();
            badSide = n.getRight();
        }else {
            //return nearestHelper1(target, best, n.getRight());
            goodSide = n.getRight();
            badSide = n.getLeft();
        }
        best = nearestHelper(target,best,goodSide);
        if(isWorthy(target, best, n)) {
            best = nearestHelper(target,best,badSide);
        }
        return best;
        //else return n;


    }
    private boolean isWorthy(Point target, Node best, Node n) {
        Point potentialBest = n.point;
        if (n.getOrientation() == horizontal) {
            potentialBest = new Point(n.getPoint().getX(), target.getY());
        }
        else {
            potentialBest = new Point(target.getX(), n.getPoint().getY());
        }
        double dis = potentialBest.distance(target, potentialBest);
        if (Double.compare(dis, target.distance(target,best.point)) < 0) {
            return true;
        }
        else return false;
    }
    //You can assume points has at least size 1.
    @Override
    public Point nearest(double x, double y) {
        Point target = new Point(x, y);
        Node best = nearestHelper(target, root, root);
        //Node temp = nearestHelper2(target, best, root);
        //if (Double.compare(target.distance(target, best.getPoint()), target.distance(temp.getPoint(), target))< 0) {
            return best.getPoint();
        //}
        //else return temp.getPoint();
    }


    public static void main(String[] args) {
        Point p1 = new Point(2, 3); // constructs a Point with x = 1.1, y = 2.2
        Point p2 = new Point(4, 2);
        Point p3 = new Point(4, 2);
        Point p4 = new Point(4, 5);
        Point p5 = new Point(3, 3);
        Point p6 = new Point(1, 5);
        Point p7 = new Point(4, 4);

        KDTree nn = new KDTree(Arrays.asList(p1, p2, p3, p4, p5, p6, p7));
        System.out.println(nn.root.point);
        //Returns the closest point to the inputted coordinates. This should take O(logN) time on average,
        // where N is the number of points.
    }

}
