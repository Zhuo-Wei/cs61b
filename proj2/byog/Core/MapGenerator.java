package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import java.util.ArrayList;
import java.util.Random;

public class MapGenerator {
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;
    private static long SEED;
    private static Random RANDOM;



    //a nested class: room
    static class Room {
        public int width;
        public int height;
        public Position position;

        Room(int w, int h, Position p) {
            this.width = w;
            this.height = h;
            this.position = p;
        }

        public  Position getCenter() {
            int x = this.position.x + (-1 + width) / 2;
            int y = this.position.y + (-1 + height) / 2;
            Position center = new Position(x, y);
            return center;
        }
    }

    /**
     * Gets a Random according to the seed that player sets.
     */
    private static Random getRandom() {
        Random RANDOM = new Random(SEED);
        return RANDOM;
    }


    public static void drawRoom(TETile[][] world, Room r) {
        // flooring
        for (int i = 1; i < r.width - 1; i++) {
            for (int j = 1; j < r.height - 1; j++) {
                //if(world[r.position.x + i][r.position.y + j].equals(Tileset.NOTHING)) {
                    world[r.position.x + i][r.position.y + j] = Tileset.FLOOR;
                //}
            }
        }

        //build walls
        for (int i = 0; i < r.width; i++) {
            if(checkNothing(world[r.position.x + i][r.position.y])) {
                world[r.position.x + i][r.position.y] = Tileset.WALL;
            }
            if(checkNothing(world[r.position.x + i][r.position.y + r.height - 1])) {
                world[r.position.x + i][r.position.y + r.height - 1] = Tileset.WALL;
            }

        }
        for (int j = 0; j < r.height; j++) {
            if(checkNothing(world[r.position.x][r.position.y + j])) {
                world[r.position.x][r.position.y + j] = Tileset.WALL;
            }
            if(checkNothing(world[r.position.x + r.width - 1][r.position.y + j])) {
                world[r.position.x + r.width - 1][r.position.y + j] = Tileset.WALL;
            }

        }
    }



    public static ArrayList<Room> drawRooms(TETile[][] world, Random r) {
        // get the number of rooms in the world randomly
        int n = 15 + r.nextInt(7);
        // generate an room arrayList
        ArrayList<Room> rooms = new ArrayList<Room>(n);
        //draw rooms respectively
        for (int i = 0; i < n; i++) {
            // the minimum value of width and length are both 2
            int w = 4 + r.nextInt(6);
            int l = 4 + r.nextInt(6);
            Position p = new Position(r.nextInt(WIDTH - 10), r.nextInt(HEIGHT - 10));
            Room newRoom = new Room(w, l, p);
            rooms.add(newRoom);
            drawRoom(world, newRoom);
        }
        return rooms;
    }

    public static boolean checkNothing (TETile world){
        return world.equals(Tileset.NOTHING);
    }

    public static boolean checkFloor (TETile world){
        return world.equals(Tileset.FLOOR);
    }

    public static boolean checkWall (TETile world){
        return world.equals(Tileset.WALL);
    }



    public static void drawVerticalHallway(TETile[][] world, Position start, Position end) {
        if (start.x >= 1 && end.x == start.x) {
            int move = Math.min(start.y, end.y);
            while (move <= Math.max(start.y, end.y)) {
                if (checkNothing(world[start.x][move])) {
                    world[start.x][move] = Tileset.FLOOR;
                    world[start.x - 1][move] = Tileset.WALL;
                    world[start.x + 1][move] = Tileset.WALL;
                    move++;
                }
                else if (checkWall(world[start.x][move])) {
                    world[start.x][move] = Tileset.FLOOR;
                    if(checkNothing(world[start.x - 1][move])) {
                        world[start.x - 1][move] = Tileset.WALL;
                    }
                    if(checkNothing(world[start.x + 1][move])) {
                        world[start.x + 1][move] = Tileset.WALL;
                    }
                    move++;
                }
                else {
                    move++;
                }
            }
        }
    }

    public static void drawHorizontalHallway(TETile[][] world, Position start, Position end) {
        if (start.y >= 1 && end.y == start.y) {
            int move = Math.min(start.x, end.x);
            while (move <= Math.max(start.x, end.x)) {
                if (checkNothing(world[move][start.y])) {
                    world[move][start.y] = Tileset.FLOOR;
                    world[move][start.y - 1] = Tileset.WALL;
                    world[move][start.y + 1] = Tileset.WALL;
                    move++;
                }
                else if (checkWall(world[move][start.y])) {
                    world[move][start.y] = Tileset.FLOOR;
                    if(checkNothing(world[move][start.y - 1])) {
                        world[move][start.y - 1] = Tileset.WALL;
                    }
                    if(checkNothing(world[move][start.y + 1])) {
                        world[move][start.y + 1] = Tileset.WALL;
                    }
                    move++;
                }
                else{
                    move++;
                }
            }
        }
    }

        //public static boolean checkCornerInp (Position p1, Position p2){
            //return Math.abs(p1.x - p2.x) == 1 && Math.abs(p1.y - p2.y) == 1;
       // }

        public static void drawCorner (TETile[][]world, Position corner) {
            Position p = new Position(corner.x - 1, corner.y - 1);
            Room r = new Room(3,3, p);
            drawRoom(world, r);
        }

        public static void connectRooms (TETile[][]world, Random r) {
            ArrayList<Room> rooms = drawRooms(world, r);
            rooms.remove(0);
            for(int i = 0; i < rooms.size(); i++) {
                Room room1 = rooms.get(i);
                Position start = room1.getCenter();
                int x = r.nextInt(rooms.size());
                Room room2 = rooms.get(x);
                while ((x == i) || Math.abs(room2.position.x - room1.position.x) > WIDTH/2 || Math.abs(room2.position.y - room1.position.y) > HEIGHT/2) {
                    x = r.nextInt(rooms.size());
                    room2 = rooms.get(x);
                }
                Position end = room2.getCenter();
                if(start.y == end.y) {
                    drawHorizontalHallway(world, start, end);
                }
                else if(start.x == end.x) {
                    drawVerticalHallway(world, start, end);
                }
                else {
                    int choice = r.nextInt(2);
                    Position p1 = new Position(start.x, end.y);
                    Position p2 = new Position(end.x, start.y);
                    switch(choice) {
                        case 0:{
                            drawCorner(world,p1);
                            drawVerticalHallway(world, start, p1);
                            drawHorizontalHallway(world, end, p1);
                        }
                        case 1: {
                            drawCorner(world, p2);
                            drawVerticalHallway(world, end, p2);
                            drawHorizontalHallway(world, start, p2);
                        }
                    }
                }
            }

        }
        public static void drawDoor(TETile[][]world) {
            here:
            for(int j = 1; j < HEIGHT; j++){
                for(int i = 1; i < WIDTH; i++){
                    if (checkWall(world[i][j]) && checkWall(world[i - 1][j]) && checkWall(world[i + 1][j]) && checkNothing(world[i][j-1])) {
                        world[i][j] = Tileset.LOCKED_DOOR;
                        break here;
                    }

                }
            }
        }



}
