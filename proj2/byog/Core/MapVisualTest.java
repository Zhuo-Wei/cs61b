package byog.Core;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;



import java.util.Random;


public class MapVisualTest {
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;
    private static final long SEED = 2873123;
    private static final Random RANDOM = new Random(SEED);

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        Random r = new Random(SEED);
        //MapGenerator.Position p = new MapGenerator.Position(20, 20);
        //MapGenerator.Room test = new MapGenerator.Room(10,10, p);
        //MapGenerator.drawRoom(world, test);
        //MapGenerator.drawRooms(world,r);
        // MapGenerator.Position p2 = new MapGenerator.Position(10, 20);
        // MapGenerator.Position p1 = new MapGenerator.Position(50, 10);
        //MapGenerator.drawVerticalHallway(world,p, p2);
        //MapGenerator.drawHorizontalHallway(world,p, p1);
        //MapGenerator.drawCorner(world,p2);
        //MapGenerator.Room room1 = new MapGenerator.Room(5,5, p1);
        //MapGenerator.drawRoom(world,room1);
        //MapGenerator.Room room2 = new MapGenerator.Room(5,5, p);

        MapGenerator.connectRooms(world,r,MapGenerator.drawRooms(world,r));
       // MapGenerator.drawDoor(world);
        //Player p = Game.setPlayer(world, r);
        //Game game = new Game();
        //world = game.playWithInputString("N999S");
        //world = game.playWithInputString("Lass");
        ter.renderFrame(world);

    }
}