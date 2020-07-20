package unsw.dungeon;

import org.json.JSONObject;

/**
 * A Mock Dungeon Loader that allows for a JSONObject to read in instead of a file
 * @author Sean Smith
 * @author Austin Landry
 *
 */
public class DungeonMockLoader extends DungeonLoader {

    public DungeonMockLoader(JSONObject json) {
         super(json);
    }

    public void onLoad(Entity player) {
        return;
    }
    public void onLoad(Wall wall) {
        return;

    }
    public void onLoad(Boulder boulder) {
        return;

    }
    public void onLoad(Door door) {
        return;

    }
    public void onLoad(Enemy enemy) {
        return;

    }
    public void onLoad(Exit exit) {
        return;

    }
    public void onLoad(Key key) {
        return;

    }
    public void onLoad(Portal portal) {
        return;

    }
    public void onLoad(Potion potion) {
        return;

    }
    public void onLoad(Switch floor) {
        return;

    }
    public void onLoad(Sword sword) {
        return;

    }
    public void onLoad(Treasure treasure) {
        return;

    }
}
