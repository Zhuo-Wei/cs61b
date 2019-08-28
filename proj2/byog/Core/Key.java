package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

public class Key {
    public Position position;
    Key(Position p, TETile world[][]) {
        this.position = p;
        world[position.x][position.y] = Tileset.KEY;
    }
    public void setTile(TETile world[][]){
        world[position.x][position.y] = Tileset.KEY;
    }
}
