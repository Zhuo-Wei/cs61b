public class NBody {

    private static String starField = "./images/starfield.jpg";

    private static int readSize(String s){
        In in = new In(s);
        int size = in.readInt();
        return size;
    }
    public static double readRadius(String s){
        In in = new In(s);
        int firstItemInFile = in.readInt();
        double secondItemInFile = in.readDouble();
        return secondItemInFile;

    }
    public static Planet[] readPlanets(String s){
        In in = new In(s);
        int size = in.readInt();
        Planet[] p = new Planet[size];
        double secondItemInFile = in.readDouble();
        for (int i = 0; i < size; i++) {
            double xP = in.readDouble();
            double yP = in.readDouble();
            double xV = in.readDouble();
            double yV = in.readDouble();
            double m = in.readDouble();
            String img = in.readString();
            p[i] = new Planet(xP, yP, xV, yV, m, img);
        }

        return p;

    }
    public static void main(String[] args) {
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        Planet[] p = readPlanets(filename);
        double radius = readRadius(filename);
        int size = readSize(filename) ;

        StdDraw.setScale(-radius, radius);
        StdDraw.picture(0, 0, starField);
        for (int i = 0; i < size; i++) {
            p[i].draw();

        }
        /* Enables double buffering. */
        StdDraw.enableDoubleBuffering();

        /* Creates an Animation. */
        double time = 0;
        while(time < T ){
            double[] xForces = new double[size];
            double[] yForces = new double[size];

            for (int i = 0; i < size; i++) {
               xForces[i] = p[i].calcNetForceExertedByX(p);
               yForces[i] = p[i].calcNetForceExertedByY(p);
            }

            for (int i = 0; i < size; i++) {
               p[i].update(dt,xForces[i],yForces[i]);
               p[i].draw();
            }
            StdDraw.picture(0, 0, starField);
            for (int i = 0; i < size; i++) {
                p[i].draw();
            }
            StdDraw.show();
            StdDraw.pause(10);
            time += dt;
        }

        StdOut.printf("%d\n", p.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < p.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    p[i].xxPos, p[i].yyPos, p[i].xxVel, p[i].yyVel, p[i].mass, p[i].imgFileName);
        }

    }
}
