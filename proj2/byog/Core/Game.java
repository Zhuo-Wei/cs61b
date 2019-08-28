package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

public class Game {

    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;
    private TETile[][] world;
    private Player player;
    private Key key;


    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
        drawMainMenu();
        char command = waitCommand();
        if (command == 'n') {
            long seed = enterSeed();
            TERenderer ter = new TERenderer();
            ter.initialize(WIDTH, HEIGHT);
            world = setWorld(seed);
            Random r = new Random(seed);
            player = setPlayer(r);
            key = setKey(r);
            ter.renderFrame(world);
            play();
        } else if (command == 'l') {
            TERenderer ter = new TERenderer();
            ter.initialize(WIDTH, HEIGHT);
            SaveObject o = SaveLoad.loadWorld();
            world = o.world;
            player = o.player;
            key = o.key;
            ter.renderFrame(world);
            play();

        }
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
    public  TETile[][] playWithInputString(String input) {
        // Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().

        long seed = getSeed(input);
        world = setWorld(seed);
        Random r = new Random(seed);
        player = setPlayer(r);
        key = setKey(r);
        String s = processString(input);
        stringPlay(s);
        return world;
    }
    public TETile[][] setWorld(long seed) {
        world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        Random r = new Random(seed);
        ArrayList<MapGenerator.Room> rooms  = MapGenerator.drawRooms(world, r);
        MapGenerator.connectRooms(world, r, rooms);
        MapGenerator.drawDoor(world);

        return world;
    }
    public Player setPlayer(Random r) {
        Position pp = new Position(0, 0);
        while (!MapGenerator.checkFloor(world[pp.x][pp.y])) {
            pp.x = r.nextInt(WIDTH);
            pp.y = r.nextInt(HEIGHT);
        }
        Player p = new Player(pp, world);
        return p;
    }
    public Key setKey(Random r) {
        Position pp = new Position(0, 0);
        while (!MapGenerator.checkFloor(world[pp.x][pp.y])) {
            pp.x = r.nextInt(WIDTH);
            pp.y = r.nextInt(HEIGHT);
        }
        Key k = new Key(pp, world);
        return k;
    }

    public long getSeed(String input) {
        long seed = 0;
        for (int i = 0; i < input.length(); i += 1) {
            if (Character.isDigit(input.charAt(i))) {
                seed = 10 * seed + Long.parseLong("" + input.charAt(i));
            }
        }
        return seed;
    }

    public void play() {
        while (true) {
            char command = waitCommand2();

            if (command == 'w') {
                player.moveUp(world);
                StdDraw.show();
            }

            if (command == 's') {
                player.moveDown(world);
                StdDraw.show();
            }

            if (command == 'a') {
                player.moveLeft(world);
                StdDraw.show();
            }

            if (command == 'd') {
                player.moveRight(world);
                StdDraw.show();
            }
            if (command == 'q') {
                SaveLoad.saveGame(new SaveObject(world, player, key));
                playWithKeyboard();
            }
        }
    }
    private void stringPlay(String s) {
        for (int i = 0; i < s.length(); i += 1) {
            char c = s.charAt(i);
            if (c == 'w') {
                player.noDrawMoveUp(world);
            }
            if (c == 's') {
                player.noDrawMoveDown(world);
            }
            if (c == 'a') {
                player.noDrawMoveLeft(world);
            }

            if (c == 'd') {
                player.noDrawMoveRight(world);
            }
            if (c == 'q'|| c == 'Q') {
                SaveLoad.saveGame(new SaveObject(world, player, key));
            }
        }
    }
    private String processString(String input) {
        String s = input;
        if ((s.charAt(0) == 'n' || s.charAt(0) == 'N')) {
            int end = input.toLowerCase().indexOf("s");
            s = s.substring(1 + end);
        } else if ((s.charAt(0) == 'l' || s.charAt(0) == 'L')) {
            SaveObject w = SaveLoad.loadWorld();
            world = w.world;
            player = w.player;
            key = w.key;
            s = s.substring(1);
        } else if ((s.charAt(0) == 'q' || s.charAt(0) == 'Q')) {
            SaveObject so = new SaveObject(world, player, key);
            SaveLoad.saveGame(so);
        }
        s = s.replaceAll("\\d", "");
        return s;
    }

    //UI:
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

    public char waitCommand() {
        while (!StdDraw.hasNextKeyTyped()) {
            StdDraw.pause(10);
            continue;
        }
        return StdDraw.nextKeyTyped();
    }
    public char waitCommand2() {
        while (!StdDraw.hasNextKeyTyped()) {
            StdDraw.pause(10);
            mouseTile();
            continue;
        }
        return StdDraw.nextKeyTyped();
    }
    public  long enterSeed() {
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
        while (c != 's' && c != 'S') {
            if (c == 'r' && c != 'R') {
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
    public void mouseTile() {
        double x = StdDraw.mouseX();
        double y = StdDraw.mouseY();
        int w = (int) Math.floorDiv((long) x, 1);
        int h = (int) Math.floorDiv((long) y, 1);
        TETile tile = world[w][h];
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.filledRectangle(WIDTH / 2, HEIGHT - 1, WIDTH / 2, 1);
        StdDraw.setPenColor(Color.PINK);
        StdDraw.textLeft(1, HEIGHT - 1, tile.description());
        if(player.gotKey == true){
            StdDraw.textRight(WIDTH - 1, HEIGHT - 1,
                    "Got it!! Let's get out of the maze!" );
        }
        else {
            StdDraw.textRight(WIDTH - 1, HEIGHT - 1,
                    "Find the key to get out of the maze!");
        }
        StdDraw.show(10);
    }


    public static void main(String[] args) {
        //System.out.println(processString("N999SDDSDWWWDDD"));
        Game game = new Game();
        game.playWithKeyboard();
    }

}

