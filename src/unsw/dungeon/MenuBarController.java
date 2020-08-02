package unsw.dungeon;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

public class MenuBarController {
    @FXML
    private MenuItem Restart;

    @FXML
    private MenuItem Menu;

    @FXML
    private MenuItem Goals;
    
    private Stage stage;
    
    private String file;

    public MenuBarController(Stage stage, String file) {
        this.stage = stage;
        this.file = file;
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
        System.out.println("goal");

    }

    
    
}