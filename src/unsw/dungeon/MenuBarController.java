package unsw.dungeon;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONObject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.json.JSONTokener;


public class MenuBarController {
    @FXML
    private MenuItem Restart;

    @FXML
    private MenuItem Menu;

    @FXML
    private MenuItem Goals;

    @FXML
    private MenuItem Theme;    
    
    private Stage stage;
    
    private String file;

    private Dungeon dungeon;

    private JSONObject jsonGoals;

    private DungeonControllerLoader dungeonLoader;


    public MenuBarController(Stage stage, String file, DungeonControllerLoader dungeonLoader) throws FileNotFoundException {
        this.stage = stage;
        this.file = file;
        this.dungeonLoader = dungeonLoader;

        // Get the goals JSONObject for the 'Goals' button
        JSONObject json = new JSONObject(new JSONTokener(new FileReader("dungeons/" + file)));
        this.jsonGoals = (JSONObject)json.get("goal-condition");
    }

    public void setDungeon(Dungeon dungeon) {
        this.dungeon = dungeon;
    }

    @FXML
    public void HandleRestartButton(ActionEvent event) throws IOException {
        LevelLoader levelScreen = new LevelLoader(this.stage, this.file);
        levelScreen.load();
    }

    @FXML
    public void HandleMenuButton(ActionEvent event) throws IOException {
        MainMenuScreen mms = new MainMenuScreen(this.stage);
        mms.start();
    }

    @FXML
    public void HandleGoalButton(ActionEvent event) throws IOException {
        if (file.equals("level1.json")) {
            level1GoalScreen();
        } else if (file.equals("level2.json")) {
            level2GoalScreen();
        } else if (file.equals("level3.json")) {
            
            
        } else if (file.equals("level4.json")) {

        } else if (file.equals("level5.json")) {

        } else if (file.equals("level6.json")) {

        }
    }

    public void level1GoalScreen() throws IOException {
        // Create a new stage.
        Stage s = new Stage();
        s.setTitle("Goals");

        // Load the goals for this level.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("level1Goals.fxml"));
        Parent root = loader.load();
        Scene sc = new Scene(root);

        ArrayList<Goal> level1Goals = this.dungeon.getGoalTypes();
        for (Goal g : level1Goals) {
            // Get each specific goal.
            if (g.getClass().equals(GoalBoulders.class)) {
                GoalBoulders gb = (GoalBoulders) g;
                if (gb.isCompleted(this.dungeon)) {
                    Image im = new Image((new File("images/success.png")).toURI().toString());
                    ImageView i = (ImageView) sc.lookup("#boulderGoalMark");
                    i.setImage(im);
                }
            }
        }

        // Show the stage.
        root.requestFocus();
        s.setScene(sc);
        s.show();
    }

    public void level2GoalScreen() throws IOException {
        // Create a new stage.
        Stage s = new Stage();
        s.setTitle("Goals");

        // Load the goals for this level.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("level2Goals.fxml"));
        Parent root = loader.load();
        Scene sc = new Scene(root);

        ArrayList<Goal> level2Goals = this.dungeon.getGoalTypes();
        for (Goal g : level2Goals) {
            // Get each specific goal.
            if (g.getClass().equals(GoalTreasure.class)) {
                GoalTreasure gt = (GoalTreasure) g;
                if (gt.isCompleted(this.dungeon)) {
                    Image im = new Image((new File("images/success.png")).toURI().toString());
                    ImageView i = (ImageView) sc.lookup("#treasureGoalMark");
                    i.setImage(im);
                }
            } else if (g.getClass().equals(GoalEnemies.class)) {
                GoalEnemies ge = (GoalEnemies) g;
                if (ge.isCompleted(this.dungeon)) {
                    Image im = new Image((new File("images/success.png")).toURI().toString());
                    ImageView i = (ImageView) sc.lookup("#enemyGoalMark");
                    i.setImage(im);
                }
            }
        }

        // Show the stage.
        root.requestFocus();
        s.setScene(sc);
        s.show();
    }
    /*
    pass dungeon to menuBarController via DungeonController
    write a isGoalPassed(String) function
    create 6 dungeons
    */

    /*public String getGoals(JSONObject jsonGoal) {
        String goalCondition = jsonGoal.getString("goal");
        // Determine the type of Goal Condition
        String goal = "";
        switch (goalCondition) {
        case "exit":
            return "exit";
        case "enemies":
            return "enemies";
        case "boulders":
            return "boulders";
        case "treasure":
            return "treasure";
        case "AND":
            String goal = "AND {\n";
            JSONArray subGoals = jsonGoal.getJSONArray("subgoals");
            //System.out.println(subGoals.length());
            int iters = 0;
            for (Object sub : subGoals) {
                //System.out.println(((JSONObject)sub).getString("goal"));
                goal += "     "+getGoals((JSONObject)sub);
                if (iters != subGoals.length()-1) {
                    goal += ",\n";
                }
                iters++;
            }
            return goal + "}";

        case "OR":
            goal = "OR\n";
            subGoals = jsonGoals.getJSONArray("subgoals");
            for (Object sub : subGoals) {
                goal.concat(getGoals((JSONObject)sub));
            }
            return goal;
        default:
            return "";
        }
        return "";
    }*/
    
    @FXML
    public void HandleNormalButton(ActionEvent event) throws IOException {
        dungeonLoader.changeTheme("normal");
    }

    @FXML
    public void HandleIceButton(ActionEvent event) throws IOException {
        dungeonLoader.changeTheme("ice");
    }

    @FXML
    public void HandleLavaButton(ActionEvent event) throws IOException {
        dungeonLoader.changeTheme("lava");
    }

    @FXML
    public void HandleMossButton(ActionEvent event) throws IOException {
        dungeonLoader.changeTheme("moss");
    }

    @FXML
    public void HandleWaterButton(ActionEvent event) throws IOException {
        dungeonLoader.changeTheme("water");
    }
}