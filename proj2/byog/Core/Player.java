package byog.Core;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import byog.TileEngine.TERenderer;
import edu.princeton.cs.introcs.StdDraw;


public class Player {
    public  Position p;

    Player(Position position, TETile world[][]) {
        this.p = position;
        world[p.x][p.y] = Tileset.PLAYER;
    }
    public void setTile(TETile world[][]){
        world[p.x][p.y] = Tileset.PLAYER;
    }

    public  boolean movable(TETile world[][], Position p) {
        if(world[p.x][p.y].equals(Tileset.FLOOR)){
            return true;
        }
        return false;
    }

    public void moveUp(TETile world[][]) {


        Position newP = new Position(p.x,p.y + 1);
        if(p.y + 1 < Game.HEIGHT && movable(world, newP)){
            world[p.x][p.y] = Tileset.FLOOR;
            world[p.x][p.y].draw(p.x, p.y);
            p = newP;
            setTile(world);
            world[newP.x][newP.y] = Tileset.PLAYER;
            world[newP.x][newP.y].draw(newP.x, newP.y);

            System.out.println("move up");
        }
    }

    public void moveDown(TETile world[][]) {
        Position newP = new Position(p.x,p.y - 1);
        if(p.y > 1 && movable(world, newP)) {
            world[p.x][p.y] = Tileset.FLOOR;
            world[p.x][p.y].draw(p.x, p.y);
            p = newP;
            setTile(world);
            world[newP.x][newP.y] = Tileset.PLAYER;
            world[newP.x][newP.y].draw(newP.x, newP.y);
            System.out.println("move down");
        }
    }

    public void moveLeft(TETile world[][]) {
        Position newP = new Position(p.x - 1,p.y);
        if(p.x > 1 && movable(world, newP)) {
            world[p.x][p.y] = Tileset.FLOOR;
            world[p.x][p.y].draw(p.x, p.y);
            p = newP;
            setTile(world);
            world[newP.x][newP.y] = Tileset.PLAYER;
            world[newP.x][newP.y].draw(newP.x, newP.y);
            System.out.println("move left");
        }
    }

    public void moveRight(TETile world[][]) {
        Position newP = new Position(p.x + 1,p.y);
        if(p.x +1 < Game.WIDTH && movable(world, newP)) {
            world[p.x][p.y] = Tileset.FLOOR;
            world[p.x][p.y].draw(p.x, p.y);
            p = newP;
            setTile(world);
            world[newP.x][newP.y] = Tileset.PLAYER;
            world[newP.x][newP.y].draw(newP.x, newP.y);
            System.out.println("move right");
        }
    }
}
