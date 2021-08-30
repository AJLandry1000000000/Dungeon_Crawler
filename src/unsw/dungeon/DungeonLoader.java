package unsw.dungeon;

import javafx.event.EventHandler;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

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

    private Dungeon dungeon;

    public DungeonLoader(String filename) throws FileNotFoundException {

        JSONTokener token = new JSONTokener(new FileReader(filename));
        json = new JSONObject(token);
    }

    public DungeonLoader(JSONObject json) {
        this.json = json;
    }

    /**
     * Parses the JSON to create a dungeon.
     * 
     * @return the create Dungeon
     */
    public Dungeon load(VBox goals) {
        // Get the size of the dungeon and set it
        int width = json.getInt("width");
        int height = json.getInt("height");
        this.dungeon = new Dungeon(width, height);

        // Parse the JSON file for the Entities
        JSONArray jsonEntities = json.getJSONArray("entities");
        List<JSONObject> jsonValues = new ArrayList<JSONObject>();
        for (int i = 0; i < jsonEntities.length(); i++) {
            jsonValues.add(jsonEntities.getJSONObject(i));
        }

        // Load each entity into the Dungeon
        for (int i = 0; i < jsonEntities.length(); i++) {
            loadEntity(dungeon, jsonEntities.getJSONObject(i));
        }

        // Extract the goal and process it
        JSONObject jsonGoals = (JSONObject) json.get("goal-condition");
        dungeon.setGoal(processGoals(jsonGoals, goals, 0));
        return dungeon;
    }

    /**
     * Process Goals turning them into potential Leaf and Composite Goals
     * 
     * @param dungeon   the given dungeon level
     * @param jsonGoals the JSONObject that is holding 1 or more Goals
     * @return the overall Goal that will hold potential further Goals
     */
    public Goal processGoals(JSONObject jsonGoals, VBox goals, int level) {
        // Extract the Goal
        String goalCondition = jsonGoals.getString("goal");
        GoalComposite goal = null;
        Goal newLeafGoal = null;
        // Create a checkbox and label display for the goals
        CheckBox check = new CheckBox();
        check.setDisable(true);
        HBox container = new HBox();
        container.setSpacing(10);
        VBox.setMargin(container, new Insets(0, 0, 0, level));
        Label goalLabel = new Label();
        goalLabel.setStyle("-fx-text-fill: white;" + "-fx-font-size: 14px;" + "-fx-opacity: 0.75;"
                + "-fx-background-color: rgba(0, 0, 0, .25);");
        // Add a listener that handles a hover over effect to gain opacity
        goalLabel.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                goalLabel.setStyle("-fx-text-fill: white;" + "-fx-font-size: 14px;" + "-fx-opacity: 1;"
                        + "-fx-background-color: rgba(0, 0, 0, .5);");
                check.setStyle("-fx-opacity: 8;");
            }
        });
        // If mouse does not hover over goals, then opacity of goals are lowered
        goalLabel.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
            public void handle(MouseEvent e) {
                goalLabel.setStyle("-fx-text-fill: white;" + "-fx-font-size: 14px;" + "-fx-opacity: 0.75;"
                        + "-fx-background-color: rgba(0, 0, 0, .22);");
                check.setStyle("-fx-opacity: 0.5;");
            }
        });
        // Add goal to overall VBox container
        container.getChildren().addAll(check, goalLabel);
        goals.getChildren().add(container);

        // Determine the type of Goal Condition and set the label accordingly
        switch (goalCondition) {
            case "exit":
                goalLabel.setText("Reach Exit");
                newLeafGoal = new GoalExit(check);
                this.dungeon.addGoalType(newLeafGoal);
                return newLeafGoal;
            case "enemies":
                goalLabel.setText("Kill Enemies");
                newLeafGoal = new GoalEnemies(check);
                this.dungeon.addGoalType(newLeafGoal);
                return newLeafGoal;
            case "boulders":
                goalLabel.setText("Activate Floor Switches");
                newLeafGoal = new GoalBoulders(check);
                this.dungeon.addGoalType(newLeafGoal);
                return newLeafGoal;
            case "treasure":
                goalLabel.setText("Collect Treasures");
                newLeafGoal = new GoalTreasure(check);
                this.dungeon.addGoalType(newLeafGoal);
                return newLeafGoal;
            case "AND":
                goalLabel.setText("AND Composite");
                goal = new GoalAND(check);
                this.dungeon.addGoalType(goal);
                break;
            case "OR":
                goalLabel.setText("OR Composite");
                goal = new GoalOR(check);
                this.dungeon.addGoalType(goal);
                break;
        }
        // If the Goal is composite, add subgoals to it
        JSONArray subGoals = jsonGoals.getJSONArray("subgoals");
        for (Object sub : subGoals) {
            goal.add(processGoals((JSONObject) sub, goals, level + 15));
        }
        return ((Goal) goal);
    }

    /**
     * Load, process and add each Entity to the Dungeon whilst checking for
     * potential multi layering
     * 
     * @param dungeon the given Dungeon level to house all entities
     * @param json    data carrying all Entity information and their positions
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
            System.out.println("Warning: Too many entities on same position");
        }
        dungeon.addEntity(entity);
    }

    /**
     * If a boulder is originally loaded onto a switch, the the switch will be
     * activated
     * 
     * @param dungeon The Dungeon that holds the entities
     * @param entity  Entity processed will either be a Boulder or Switch
     * @param x       Coordinate
     * @param y       Coordinate
     * @return False if too many entities at given Coordinate
     */
    private Boolean checkEntityLocation(Dungeon dungeon, Entity entity, int x, int y) {
        if (dungeon.getEntities(x, y).size() > 0) {
            Entity check = dungeon.getEntity(x, y);
            if (check.getClass().equals(Boulder.class)) {
                ((Switch) entity).activateSwitch((Boulder) check);
            } else if (check.getClass().equals(Switch.class)) {
                ((Switch) check).activateSwitch((Boulder) entity);
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
