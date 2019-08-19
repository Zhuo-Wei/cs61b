package byog.Core;
import byog.TileEngine.TETile;

import java.io.Serializable;

public class SaveObject implements Serializable{
    public Player player;
    public TETile[][] world;
    public SaveObject(TETile[][] w, Player p){
        this.player = p;
        this.world = w;
    }
    public Player player() {
        return this.player;
    }

    public TETile[][] world() {
        return this.world;
    }
}
