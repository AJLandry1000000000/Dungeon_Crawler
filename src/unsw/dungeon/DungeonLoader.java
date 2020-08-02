package unsw.dungeon;

import java.io.FileNotFoundException;
import java.io.FileReader;

import java.util.List;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Loads a dungeon from a .json file.
 *
 * By extending this class, a subclass can hook into entity creation. This is
 * useful for creating UI elements with corresponding entities.
 *
 * @author Sean Smith
 * @author Austin Landry
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

        System.out.println("WDITH:" +width);

        Dungeon dungeon = new Dungeon(width, height);

        JSONArray jsonEntities = json.getJSONArray("entities");

        List<JSONObject> jsonValues = new ArrayList<JSONObject>();
        for (int i = 0; i < jsonEntities.length(); i++) {
            jsonValues.add(jsonEntities.getJSONObject(i));
        }


        for (int i = 0; i < jsonEntities.length(); i++) {
            loadEntity(dungeon, jsonEntities.getJSONObject(i));
        }

        // Extract the goal and process it
        JSONObject jsonGoals = (JSONObject)json.get("goal-condition");
        dungeon.setGoal(processGoals(jsonGoals));
        return dungeon;
    }

    /**
     * Process Goals turning them into potential Leaf and Composite Goals
     * @param dungeon the given dungeon level
     * @param jsonGoals the JSONObject that is holding 1 or more Goals
     * @return the overall Goal that will hold potential further Goals
     */
    public Goal processGoals(JSONObject jsonGoals) {
        String goalCondition = jsonGoals.getString("goal");
        GoalComposite goal = null;
        // Determine the type of Goal Condition
        switch (goalCondition) {
        case "exit":
            return new GoalExit();
        case "enemies":
            return new GoalEnemies();
        case "boulders":
            return new GoalBoulders();
        case "treasure":
            return new GoalTreasure();
        case "AND":
            goal = new GoalAND();
            break;
        case "OR":
            goal = new GoalOR();
            break;
        }
        // If the Goal is composite, add subgoals to it
        JSONArray subGoals = jsonGoals.getJSONArray("subgoals");
        for (Object sub : subGoals) {
            goal.add(processGoals((JSONObject)sub));
        }
        return ((Goal)goal);
    }

    /**
     * Load, process and add each Entity to the Dungeon whilst checking for potential multi layering
     * @param dungeon the given Dungeon level to house all entities
     * @param json data carrying all Entity information and their positions
     */
    private void loadEntity(Dungeon dungeon, JSONObject json) {
        String type = json.getString("type");
        int x = json.getInt("x");
        int y = json.getInt("y");
        int id = 0;

        Entity entity = null;
        switch (type) {
        case "player":
            Player player = new Player(x, y, dungeon);
            dungeon.setPlayer(player);
            onLoad(player);
            entity = player;
            break;
        case "wall":
            Wall wall = new Wall(x, y, dungeon);
            onLoad(wall);
            entity = wall;
            break;
        // Addition Entity cases are added here
        case "boulder":
            Boulder boulder = new Boulder(x, y, dungeon);
            onLoad(boulder);
            entity = boulder;
            break;
        case "door":
            id = json.getInt("id");
            Door door = new Door(x, y, dungeon, id);
            onLoad(door);
            entity = door;
            break;
        case "enemy":
            Enemy enemy = new Enemy(x, y, dungeon);
            onLoad(enemy);
            entity = enemy;
            break;
        case "exit":
            Exit exit = new Exit(x, y, dungeon);
            onLoad(exit);
            entity = exit;
            break;
        case "key":
            id = json.getInt("id");
            Key key = new Key(x, y, dungeon, id);
            onLoad(key);
            entity = key;
            break;
        case "portal":
            id = json.getInt("id");
            Portal portal = new Portal(x, y, dungeon, id);
            onLoad(portal);
            entity = portal;
            break;
        case "invincibility":
            Potion potion = new Potion(x, y, dungeon);
            onLoad(potion);
            entity = potion;
            break;
        case "switch":
            Switch floor = new Switch(x, y, dungeon);
            onLoad(floor);
            entity = floor;
            break;
        case "sword":
            Sword sword = new Sword(x, y, dungeon);
            onLoad(sword);
            entity = sword;
            break;
        case "treasure":
            Treasure treasure = new Treasure(x, y, dungeon);
            onLoad(treasure);
            entity = treasure;
            break;
        case "hammer":
            Hammer hammer = new Hammer(x, y, dungeon);
            onLoad(hammer);
            entity = hammer;
            break;
        case "ghost":
            Ghost ghost = new Ghost(x, y, dungeon);
            onLoad(ghost);
            entity = ghost;
            break;
        default:
            break;
        }
        if (entity == null) {
            return;
        }
        // Check and process if Entities are on the same location
        if (!checkEntityLocation(dungeon, entity, x, y)) {
            System.out.println("Too many entities on same position");
        }
        dungeon.addEntity(entity);
    }

    /**
     * 
     * @param dungeon
     * @param entity
     * @param x
     * @param y
     * @return
     */
    private Boolean checkEntityLocation(Dungeon dungeon, Entity entity, int x, int y) {
        if (dungeon.getEntities(x, y).size() > 0) {
            Entity check = dungeon.getEntity(x, y);
            if (check.getClass().equals(Boulder.class)) {
                ((Switch)entity).activateSwitch(check);
            } else if (check.getClass().equals(Switch.class)) {
                ((Switch)check).activateSwitch(entity);
            } else {
                return false;
            }
        }
        return true;
    }

    public abstract void onLoad(Entity player);
    public abstract void onLoad(Wall wall);
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
    public abstract void onLoad(Hammer hammer);
    public abstract void onLoad(Ghost ghost);

}
