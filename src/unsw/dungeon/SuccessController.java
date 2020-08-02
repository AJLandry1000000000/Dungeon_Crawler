package unsw.dungeon;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;


public class SuccessController {

    private Stage stage;
    
    @FXML
    private Button failMainMenu;
    
  
    public SuccessController(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void handleSuccessMainMenu(ActionEvent event) throws IOException {
        MainMenuScreen mms = new MainMenuScreen(this.stage);
        mms.start();
    }
}