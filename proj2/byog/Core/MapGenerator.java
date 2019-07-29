package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import java.util.ArrayList;
import java.util.Random;

public class MapGenerator {
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;
    private static final long SEED = 2873123;
    private static final RandomUtils RANDOM = new RandomUtils();

    //a nested class: position
    static class Position {
        int x;
        int y;

        Position(int posX, int posY) {
            x = posX;
            y = posY;
        }

    }

    //a nested class: room
    static class Room {
        public static int width;
        public static int height;
        public static Position position;

        Room(int w, int h, Position p) {
            width = w;
            height = h;
            position = p;
        }

        public static Position getCenter() {
            int x = (position.x + 1 + width) / 2;
            int y = (position.y + 1 + height) / 2;
            Position center = new Position(x, y);
            return center;
        }
    }

    /**
     * Gets a Random according to the seed that player sets.
     */
    private static Random getRandom(long seed) {
        Random RANDOM = new Random(seed);
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



    public static ArrayList<Room> drawRooms(TETile[][] world) {
        // get the number of rooms in the world randomly
        int n = 20 + getRandom(SEED).nextInt(10);
        // generate an room arrayList
        ArrayList<Room> rooms = new ArrayList<Room>(n);
        //draw rooms respectively
        for (int i = 0; i < n; i++) {
            // the minimum value of width and length are both 2
            int w = 3 + getRandom(SEED).nextInt(6);
            int l = 3 + getRandom(SEED).nextInt(6);
            Position p = new Position(getRandom(SEED).nextInt(WIDTH - 10), getRandom(SEED).nextInt(HEIGHT - 10));
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
                    world[move][start.x - 1] = Tileset.WALL;
                    world[move][start.x + 1] = Tileset.WALL;
                    move++;
                }
                else if (checkWall(world[move][start.y])) {
                    world[move][start.y] = Tileset.FLOOR;
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

        public static void connectRooms (TETile[][]world,Room room1, Room room2) {
            ArrayList<Room> rooms = drawRooms(world);
            for(int i = 0; i < rooms.size(); i++) {
                //Room room2 = rooms.get(getRandom(SEED).nextInt(rooms.size()));
                //Room room1 = rooms.get(i);
                Position start = room1.getCenter();
                Position end = room2.getCenter();
                if(start.x == end.x) {
                    drawHorizontalHallway(world, start, end);
                }
                else if(start.y == end.y) {
                    drawVerticalHallway(world, start, end);
                }
                else {
                    int choice = getRandom(SEED).nextInt(2);
                    Position p1 = new Position(start.x, end.y);
                    Position p2 = new Position(start.y, end.x);
                    switch(choice) {
                        case 0: drawCorner(world,p1);
                                drawVerticalHallway(world, start, p1);
                                drawHorizontalHallway(world, start, p1);
                        case 1: drawCorner(world,p2);
                                drawVerticalHallway(world, start, p2);
                                drawHorizontalHallway(world, start, p2);
                    }
                }
            }
        }
            // check
            /**
             if ((p2.x - p1.x > 0) && (p2.y - p1.y > 0)) {
             if (world[p1.x][p1.y + 1].equals(Tileset.WALL) && world[p1.x + 1][p1.y].equals(Tileset.NOTHING)) {
             world[p1.x + 1][p1.y - 1] = Tileset.WALL;
             world[p1.x + 2][p1.y - 1] = Tileset.WALL;
             world[p1.x + 2][p1.y] = Tileset.WALL;
             } else if (world[p1.x][p1.y + 1].equals(Tileset.NOTHING) && world[p1.x + 1][p1.y].equals(Tileset.WALL)) {
             world[p1.x - 1][p1.y + 1] = Tileset.WALL;
             world[p1.x - 1][p1.y + 2] = Tileset.WALL;
             world[p1.x][p1.y + 2] = Tileset.WALL;
             } else {
             int tileNum = getRandom(SEED).nextInt(2);
             switch (tileNum) {
             case 0:
             world[p1.x + 1][p1.y - 1] = Tileset.WALL;
             world[p1.x + 2][p1.y - 1] = Tileset.WALL;
             world[p1.x + 2][p1.y] = Tileset.WALL;
             ;
             case 1:
             world[p1.x - 1][p1.y + 1] = Tileset.WALL;
             world[p1.x - 1][p1.y + 2] = Tileset.WALL;
             world[p1.x][p1.y + 2] = Tileset.WALL;
             }
             }
             }

             if ((p2.x - p1.x < 0) && (p2.y - p1.y < 0)) {
             if (world[p2.x][p2.y + 1].equals(Tileset.WALL) && world[p2.x + 1][p2.y].equals(Tileset.NOTHING)) {
             world[p2.x + 1][p2.y - 1] = Tileset.WALL;
             world[p2.x + 2][p2.y - 1] = Tileset.WALL;
             world[p2.x + 2][p2.y] = Tileset.WALL;
             } else if (world[p2.x][p2.y + 1].equals(Tileset.NOTHING) && world[p2.x + 1][p2.y].equals(Tileset.WALL)) {
             world[p2.x - 1][p2.y + 1] = Tileset.WALL;
             world[p2.x - 1][p2.y + 2] = Tileset.WALL;
             world[p2.x][p2.y + 2] = Tileset.WALL;
             } else {
             int tileNum = getRandom(SEED).nextInt(2);
             switch (tileNum) {
             case 0:
             world[p2.x + 1][p2.y - 1] = Tileset.WALL;
             world[p2.x + 2][p2.y - 1] = Tileset.WALL;
             world[p2.x + 2][p2.y] = Tileset.WALL;
             ;
             case 1:
             world[p2.x - 1][p2.y + 1] = Tileset.WALL;
             world[p2.x - 1][p2.y + 2] = Tileset.WALL;
             world[p2.x][p2.y + 2] = Tileset.WALL;
             }
             }
             }
             **/



}
