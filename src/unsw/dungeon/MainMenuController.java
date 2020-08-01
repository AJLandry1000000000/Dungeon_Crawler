package unsw.dungeon;

import java.io.FileNotFoundException;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
        Stage primaryStage = this.stage;
        primaryStage.setTitle("Dungeon");
        try {
            // Read from a JSON file
            DungeonControllerLoader dungeonLoader = new DungeonControllerLoader(file);
            DungeonController controller = dungeonLoader.loadController();

            // JavaFX processing
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DungeonView.fxml"));
            loader.setController(controller);

            // Show Scene
            Parent root = loader.load();
            Scene scene = new Scene(root);
            root.requestFocus();
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (FileNotFoundException f) {
            System.out.println("We dont have the files for "+file);
        } catch (IOException io) {
            System.out.println("Input/Output problem with "+file);
        }

    }

        
    @FXML
    public void handleInstructionsButton(ActionEvent event) throws IOException {
        InstructionsScreen in = new InstructionsScreen(this.stage);
        in.start();

    }

    @FXML
    public void handleLevel1Button(ActionEvent event) {
        changeStage("boulders.json");
    }

    @FXML
    public void handleLevel2Button(ActionEvent event) {
        changeStage("advanced.json");
    }

    @FXML
    public void handleLevel3Button(ActionEvent event) {
        changeStage("maze.json");
    }

    @FXML
    public void handleLevel4Button(ActionEvent event) {
        changeStage("boulders.json");
    }

    @FXML
    public void handleLevel5Button(ActionEvent event) {
        changeStage("boulders.json");
    }

    @FXML
    public void handleLevel6Button(ActionEvent event) {
        changeStage("boulders.json");
    }

    

}





