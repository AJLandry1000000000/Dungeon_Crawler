package unsw.dungeon;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;


public class FailController {

    private Stage stage;
    
    @FXML
    private Button failMainMenu;
    
  
    public FailController(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void handleFailMainMenu(ActionEvent event) throws IOException {
        MainMenuScreen mms = new MainMenuScreen(this.stage);
        mms.start();
    }
}