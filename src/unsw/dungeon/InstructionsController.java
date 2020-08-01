package unsw.dungeon;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;


public class InstructionsController {

    private Stage stage;

    @FXML
    private Button BackToMainMenu;

    public InstructionsController(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void handleBackToMainMenu(ActionEvent event) throws IOException {
        MainMenuScreen mms = new MainMenuScreen(this.stage);
        mms.start();
    }

}