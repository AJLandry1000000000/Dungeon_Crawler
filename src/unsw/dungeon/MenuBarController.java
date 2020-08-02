package unsw.dungeon;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

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
        Stage s = new Stage();
        s.setTitle("Goals");
        if (file.equals("boulders.json")) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("bouldersGoals.fxml"));
            Parent root = loader.load();
            Scene sc = new Scene(root);

            if (this.dungeon.goalsCompleted()) {
                Image im = new Image((new File("images/success.png")).toURI().toString());
                ImageView i = (ImageView) sc.lookup("#image1");
                i.setImage(im);
            }
            root.requestFocus();
            s.setScene(sc);
            s.show();

        } else if (file.equals("advanced.json")) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("bouldersGoals.fxml"));
            Parent root = loader.load();
            Scene sc = new Scene(root);

            if (this.dungeon.goalsCompleted()) {
                Image im = new Image((new File("images/success.png")).toURI().toString());
                ImageView i = (ImageView) sc.lookup("#image1");
                i.setImage(im);
            }
            root.requestFocus();
            s.setScene(sc);
            s.show();
            
        }
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