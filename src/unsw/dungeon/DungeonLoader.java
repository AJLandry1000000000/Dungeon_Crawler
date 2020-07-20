package unsw.dungeon;

import java.io.FileNotFoundException;
import java.io.FileReader;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import unsw.dungeon.entity.Entity;
import unsw.dungeon.entity.goals.Goal;
import unsw.dungeon.entity.goals.GoalAND;
import unsw.dungeon.entity.goals.GoalBoulders;
import unsw.dungeon.entity.goals.GoalComposite;
import unsw.dungeon.entity.goals.GoalEnemies;
import unsw.dungeon.entity.goals.GoalExit;
import unsw.dungeon.entity.goals.GoalOR;
import unsw.dungeon.entity.goals.GoalTreasure;
import unsw.dungeon.entity.model.Boulder;
import unsw.dungeon.entity.model.Door;
import unsw.dungeon.entity.model.Enemy;
import unsw.dungeon.entity.model.Exit;
import unsw.dungeon.entity.model.Key;
import unsw.dungeon.entity.model.Player;
import unsw.dungeon.entity.model.Portal;
import unsw.dungeon.entity.model.Potion;
import unsw.dungeon.entity.model.Switch;
import unsw.dungeon.entity.model.Sword;
import unsw.dungeon.entity.model.Treasure;
import unsw.dungeon.entity.model.Wall;

/**
 * Loads a dungeon from a .json file.
 *
 * By extending this class, a subclass can hook into entity creation. This is
 * useful for creating UI elements with corresponding entities.
 *
 * @author Robert Clifton-Everest
 *
 */
public abstract class DungeonLoader {

    private JSONObject json;

    public DungeonLoader(String filename) throws FileNotFoundException {
        json = new JSONObject(new JSONTokener(new FileReader("dungeons/" + filename)));
    }

    public DungeonLoader(JSONObject json) {
        this.json = json;
    }

    /**
     * Parses the JSON to create a dungeon.
     * @return
     */
    public Dungeon load() {
        int width = json.getInt("width");
        int height = json.getInt("height");

        Dungeon dungeon = new Dungeon(width, height);

        JSONArray jsonEntities = json.getJSONArray("entities");

        for (int i = 0; i < jsonEntities.length(); i++) {
            loadEntity(dungeon, jsonEntities.getJSONObject(i));
        }

        JSONObject jsonGoals = (JSONObject)json.get("goal-condition");
        dungeon.setGoal(processGoals(dungeon, jsonGoals));
        return dungeon;
    }

    public Goal processGoals(Dungeon dungeon, JSONObject jsonGoals) {
        String goalCondition = jsonGoals.getString("goal");
        GoalComposite goal = null;
        switch (goalCondition) {
        case "exit":
            return new GoalExit(dungeon);
        case "enemies":
            return new GoalEnemies(dungeon);
        case "boulders":
            return new GoalBoulders(dungeon);
        case "treasure":
            return new GoalTreasure(dungeon);
        case "AND":
            goal = new GoalAND(dungeon);
            break;
        case "OR":
            goal = new GoalOR(dungeon);
            break;
        }

        JSONArray subGoals = jsonGoals.getJSONArray("subgoals");
        for (Object sub : subGoals) {
            goal.add(processGoals(dungeon, (JSONObject)sub));
        }
        return ((Goal)goal);
    }

    private void loadEntity(Dungeon dungeon, JSONObject json) {
        String type = json.getString("type");
        int x = json.getInt("x");
        int y = json.getInt("y");
        int id = 0;

        Entity entity = null;
        switch (type) {
        case "player":
            Player player = new Player(dungeon, x, y);
            dungeon.setPlayer(player);
            onLoad(player);
            entity = player;
            break;
        case "wall":
            Wall wall = new Wall(x, y);
            onLoad(wall);
            entity = wall;
            break;
        // TODO Handle other possible entities
        case "boulder":
            Boulder boulder = new Boulder(dungeon, x, y);
            onLoad(boulder);
            entity = boulder;
            break;
        case "door":
            id = json.getInt("id");
            Door door = new Door(dungeon, x, y, id);
            onLoad(door);
            entity = door;
            break;
        case "enemy":
            Enemy enemy = new Enemy(dungeon, x, y);
            onLoad(enemy);
            entity = enemy;
            break;
        case "exit":
            Exit exit = new Exit(dungeon, x, y);
            onLoad(exit);
            entity = exit;
            break;
        case "key":
            id = json.getInt("id");
            Key key = new Key(dungeon, x, y, id);
            onLoad(key);
            entity = key;
            break;
        case "portal":
            id = json.getInt("id");
            Portal portal = new Portal(dungeon, x, y, id);
            onLoad(portal);
            entity = portal;
            break;
        case "invincibility":
            Potion potion = new Potion(x, y);
            onLoad(potion);
            entity = potion;
            break;
        case "switch":
            Switch floor = new Switch(dungeon, x, y);
            onLoad(floor);
            entity = floor;
            break;
        case "sword":
            Sword sword = new Sword(x, y);
            onLoad(sword);
            entity = sword;
            break;
        case "treasure":
            Treasure treasure = new Treasure(dungeon, x, y);
            onLoad(treasure);
            entity = treasure;
            break;
        default:
            break;
        }
        if (!checkEntityLocation(dungeon, entity, x, y)) {
            System.out.println("Too many entities on same position");
        }
        dungeon.addEntity(entity);
    }

    private Boolean checkEntityLocation(Dungeon dungeon, Entity entity, int x, int y) {
        if (dungeon.getEntities(x, y).size() > 0) {
            Entity check = dungeon.getEntity(x, y);
            if (check instanceof Boulder) {
                ((Switch)entity).activateSwitch(check);
            } else if (check instanceof Switch) {
                ((Switch)check).activateSwitch(entity);
            } else {
                return false;
            }
        }
        return true;
    }

    public abstract void onLoad(Entity player);
    public abstract void onLoad(Wall wall);
    // TODO Create additional abstract methods for the other entities
    public abstract void onLoad(Boulder boulder);
    public abstract void onLoad(Door door);
    public abstract void onLoad(Enemy enemy);
    public abstract void onLoad(Exit exit);
    public abstract void onLoad(Key key);
    public abstract void onLoad(Portal portal);
    public abstract void onLoad(Potion potion);
    public abstract void onLoad(Switch floor);
    public abstract void onLoad(Sword sword);
    public abstract void onLoad(Treasure treasure);

}
