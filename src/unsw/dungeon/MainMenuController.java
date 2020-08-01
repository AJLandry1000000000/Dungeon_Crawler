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


            BorderPane root = new BorderPane();

            //Create your menu
            final Menu main = new Menu("Menu");
            final Menu instructions = new Menu("Instructions");
            final Menu goals = new Menu("Goals");
            final Menu restart = new Menu("Restart");
            MenuBar menuBar = new MenuBar();
            menuBar.getMenus().addAll(main, instructions, goals, restart);
            root.setTop(menuBar);

            Node level = loader.load();
            root.setCenter(level);

            GridPane menuBar2 = new GridPane();
            menuBar2.setPadding(new Insets(10, 10, 10, 10));
            menuBar2.setHgap(5);

            Label test = new Label("Inventory:");
            ImageView l1 = new ImageView(new Image((new File("images/ghost_new.png")).toURI().toString()));
            ImageView l2 = new ImageView(new Image((new File("images/ghost_new.png")).toURI().toString()));

            menuBar2.add(l1, 0, 0, 2, 1);
            menuBar2.add(l2, 3, 0, 2, 1);
            root.setBottom(test);
            root.setBottom(menuBar2);

            // Show Scene
            // Parent game = loader.load();
            Scene scene = new Scene(root);
            level.requestFocus();
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
        changeStage("test.json");
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





