package unsw.dungeon;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import org.json.JSONTokener;


public class MenuBarController {
    @FXML
    private MenuItem Restart;

    @FXML
    private MenuItem Menu;

    @FXML
    private MenuItem Goals;
    
    private Stage stage;
    
    private String file;

    private JSONObject jsonGoals;


    public MenuBarController(Stage stage, String file) throws FileNotFoundException {
        this.stage = stage;
        this.file = file;

        // Get the goals JSONObject for the 'Goals' button
        JSONObject json = new JSONObject(new JSONTokener(new FileReader("dungeons/" + file)));
        this.jsonGoals = (JSONObject)json.get("goal-condition");
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
        //String goalCondition = this.jsonGoals.getString("goal");
        System.out.println(getGoals(this.jsonGoals));
    }

    public String getGoals(JSONObject jsonGoal) {
        String goalCondition = jsonGoal.getString("goal");
        // Determine the type of Goal Condition
        String goal = "";
        switch (goalCondition) {
        case "exit":
            return "exit\n";
        case "enemies":
            return "enemies\n";
        case "boulders":
            return "boulders\n";
        case "treasure":
            return "treasure\n";
        case "AND":
            goal = "AND\n";
            JSONArray subGoals = jsonGoals.getJSONArray("subgoals");
            for (Object sub : subGoals) {
                try {
                    goal.concat(getGoals((JSONObject)sub));
                } finally {
                    return "";
                }
            }
            return goal;
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
    }
    
}