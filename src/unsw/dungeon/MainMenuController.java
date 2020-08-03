package unsw.dungeon;

import java.io.FileNotFoundException;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;


public class MainMenuController {

    private Stage stage;
    
    @FXML
    private Button InstructionsButton;
    
    @FXML
    private Button level1;

    @FXML
    private Button level2;

    @FXML
    private Button level3;

    @FXML
    private Button level4;

    @FXML
    private Button level5;

    @FXML
    private Button level6;

  
    public MainMenuController(Stage stage) {
        this.stage = stage;
    }

    public void changeStage(String file) {
        try {
            LevelLoader levelScreen = new LevelLoader(this.stage, file);
            levelScreen.load();
        } catch (FileNotFoundException f) {
            System.out.println("We dont have the files for " + file);
        } catch (IOException io) {
            System.out.println("Input/Output problem with " + file);
            io.printStackTrace();
        }
    }

    @FXML
    public void handleInstructionsButton(ActionEvent event) throws IOException {
        InstructionsScreen in = new InstructionsScreen(this.stage);
        in.start();
    }

    @FXML
    public void handleLevel1Button(ActionEvent event) {
        changeStage("level1.json");
    }

    @FXML
    public void handleLevel2Button(ActionEvent event) {
        changeStage("level2.json");
    }

    @FXML
    public void handleLevel3Button(ActionEvent event) {
        changeStage("level3.json");
    }

    @FXML
    public void handleLevel4Button(ActionEvent event) {
        changeStage("level4.json");
    }

    @FXML
    public void handleLevel5Button(ActionEvent event) {
        changeStage("level5.json");
    }

    @FXML
    public void handleLevel6Button(ActionEvent event) {
        changeStage("level6.json");
    }

    

}





