package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import javafx.scene.image.ImageView;
import unsw.dungeon.entity.Entity;
import unsw.dungeon.entity.model.Boulder;
import unsw.dungeon.entity.model.Door;
import unsw.dungeon.entity.model.Enemy;
import unsw.dungeon.entity.model.Exit;
import unsw.dungeon.entity.model.Key;
import unsw.dungeon.entity.model.Portal;
import unsw.dungeon.entity.model.Potion;
import unsw.dungeon.entity.model.Switch;
import unsw.dungeon.entity.model.Sword;
import unsw.dungeon.entity.model.Treasure;
import unsw.dungeon.entity.model.Wall;

public class DungeonMockLoader extends DungeonLoader {

    private List<ImageView> entities;

    public DungeonMockLoader(JSONObject json) {
        super(json);
        entities = new ArrayList<>();
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

    public DungeonController loadController() {
        return new DungeonController(load(), entities);
    }


}
