package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import java.util.Random;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

public class Game {

    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;



    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public static void playWithKeyboard() {
        UI.drawMainMenu();
        char command = UI.waitCommand();
            if (command == 'n') {
                long seed = UI.enterSeed();
                TERenderer ter = new TERenderer();
                ter.initialize(WIDTH, HEIGHT);
                TETile[][] world = setWorld(seed);
                Random r = new Random(seed);
                Player p = setPlayer(world,r);
                ter.renderFrame(world);
                play(world, p);
            } else if (command == 'l') {

            }
            command = UI.waitCommand();
        }


    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        // Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().
        TERenderer ter = new TERenderer();
        long seed;


        if (input.toLowerCase().contains("n") && input.toLowerCase().contains("s")) {
            int start = input.toLowerCase().indexOf("n") + 1;
            int end = input.toLowerCase().indexOf("s");
            try {
                seed = Long.parseLong(input.substring(start, end));
            } catch(Exception e) {
                throw new RuntimeException("Seed has to be an integer but you input: \"" + input.substring(start, end) + "\"");
            }
        } else {
            throw new RuntimeException("You must put a string start with 'n' and end with 's'.");
        }

        TETile[][] world = setWorld(seed);
        return world;
    }
    public static TETile[][] setWorld(long seed) {
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        Random r = new Random(seed);
        MapGenerator.connectRooms(world, r);
        MapGenerator.drawDoor(world);

        return world;
    }
    public static Player setPlayer(TETile[][] world, Random r){
        Position pp = new Position(0, 0);
        while(!MapGenerator.checkFloor(world[pp.x][pp.y])) {
            pp.x = r.nextInt(WIDTH);
            pp.y = r.nextInt(HEIGHT);
        }
        Player p = new Player(pp, world);
        return p;
    }

    public static void play(TETile[][] world, Player p) {
        while (true) {
            char command = UI.waitCommand();
        if (command == 'w') {
            p.moveUp(world);
        }

        if (command == 's') {
            p.moveDown(world);
        }

        if (command == 'a') {
            p.moveLeft(world);
        }

        if (command == 'd') {
            p.moveRight(world);
        }
    }
    }
    public static void main(String[] args) {
        playWithKeyboard();

    }
}
