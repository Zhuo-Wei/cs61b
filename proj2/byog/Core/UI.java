package byog.Core;
import byog.TileEngine.TETile;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.io.Serializable;
public class UI {
    public static int WIDTH = MapGenerator.WIDTH;
    public static int HEIGHT = MapGenerator.HEIGHT;
    public static void drawMainMenu() {

        StdDraw.setCanvasSize(WIDTH * 16, HEIGHT * 16);
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT);
        StdDraw.setFont(new Font("Times New Roman", Font.BOLD, 60));
        StdDraw.setPenColor(Color.WHITE);

        StdDraw.enableDoubleBuffering();
        StdDraw.clear(StdDraw.PINK);
        StdDraw.text(WIDTH / 2, HEIGHT * 0.7, "CS61B: The Game");
        StdDraw.show();

        StdDraw.setFont(new Font("Times New Roman", Font.BOLD, 20));
        StdDraw.text(WIDTH / 2, HEIGHT * 0.4, "New Game (N)");
        StdDraw.text(WIDTH / 2, HEIGHT * 0.35, "Load Game (L)");
        StdDraw.text(WIDTH / 2, HEIGHT * 0.3, "Quit (Q)");

        StdDraw.show();
    }
    private static String precessString(String input) {
        String moveStr = input;
        if ((moveStr.charAt(0) == 'n')) {
            moveStr = moveStr.substring(1);
        } else if ((moveStr.charAt(0) == 'l')) {

        } else if ((moveStr.charAt(0) == 'q')) {

        }
        moveStr = moveStr.replaceAll("\\d", "");
        return moveStr;
    }
    /**public String solicitNCharsInput(int n) {
     String input = "";
     drawFrame(input);

     while (input.length() < n) {
     if (!StdDraw.hasNextKeyTyped()) {
     continue;
     }
     char key = StdDraw.nextKeyTyped();
     input += String.valueOf(key);
     drawFrame(input);
     }
     StdDraw.pause(500);
     return input;
     }
     **/
    public static char waitCommand() {
        while (!StdDraw.hasNextKeyTyped()) {
            StdDraw.pause(10);
            continue;
        }
        return StdDraw.nextKeyTyped();
    }
    public static long enterSeed() {
        StdDraw.setCanvasSize(WIDTH * 16, HEIGHT * 16);
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT);
        StdDraw.setFont(new Font("Times New Roman", Font.BOLD, 20));
        StdDraw.setPenColor(Color.WHITE);


        StdDraw.clear(StdDraw.PINK);
        StdDraw.text(WIDTH / 2, HEIGHT * 0.6,
                "Please enter a seed, or press 'r' for a random seed. Press s to confirm: ");
        StdDraw.show();
        char c = waitCommand();
        long seed = 0;
        while (c != 's') {
            if (c == 'r') {
                seed = (long) Math.random() * 1000;
                break;
            }
            if (Character.isDigit(c)) {
                StdDraw.clear(StdDraw.PINK);
                StdDraw.clear(StdDraw.PINK);
                StdDraw.text(WIDTH / 2, HEIGHT * 0.6,
                        "Please enter a seed, or press 'r' for a random seed. Press s to confirm: ");
                StdDraw.show();
                seed = 10 * seed + Long.parseLong("" + c);
                StdDraw.clear(StdDraw.PINK);
                StdDraw.text(WIDTH / 2, HEIGHT * 0.5, "Seed: " + seed);
                StdDraw.show();
            } else {
                StdDraw.clear(StdDraw.PINK);
                StdDraw.text(WIDTH / 2, HEIGHT * 0.6,
                        "Please enter a seed, or press 'r' for a random seed. Press s to confirm: ");

                StdDraw.show();
                StdDraw.text(WIDTH / 2, HEIGHT * 0.5, "Seed: " + seed);
                StdDraw.text( WIDTH / 2, HEIGHT * 0.4, "numbers only pls");
                StdDraw.show();
            }
            c = waitCommand();
        }
        return seed;
    }


}
