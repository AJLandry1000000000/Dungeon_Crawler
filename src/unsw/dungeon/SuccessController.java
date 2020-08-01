package unsw.dungeon;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.geometry.Insets;


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