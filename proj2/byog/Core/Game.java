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
    private TETile[][] world;
    private Player player;
    private Key key;


    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
        UI.drawMainMenu();
        char command = UI.waitCommand();
        if (command == 'n') {
            long seed = UI.enterSeed();
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
        MapGenerator.connectRooms(world, r);
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
            char command = UI.waitCommand();

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

    public static void main(String[] args) {
        //System.out.println(processString("N999SDDSDWWWDDD"));
        Game game = new Game();
        game.playWithKeyboard();
    }

}

