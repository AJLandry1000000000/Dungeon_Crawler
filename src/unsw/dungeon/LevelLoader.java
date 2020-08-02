package unsw.dungeon;

import java.io.File;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class LevelLoader {

    Stage stage;
    String file;

    //@FXML
    //private Menu Restart;

    public LevelLoader(Stage stage, String file) throws IOException {
        this.stage = stage;
        this.file = file;
    }

    public void load() throws IOException {
        // Overall container
        BorderPane root = new BorderPane();

        // Top Menu
        FXMLLoader loader2 = new FXMLLoader(getClass().getResource("MenuBar.fxml"));
        //loader2.setController(this);
        root.setTop(loader2.load());

        // Inventory
        BorderPane inventory = new BorderPane();
        GridPane menuBar2 = new GridPane();
        menuBar2.setPadding(new Insets(10, 10, 10, 10));
        menuBar2.setHgap(5);
        Label test = new Label("Inventory:");
        ImageView l1 = new ImageView(new Image((new File("images/ghost_new.png")).toURI().toString()));
        ImageView l2 = new ImageView(new Image((new File("images/ghost_new.png")).toURI().toString()));
        menuBar2.add(l1, 0, 0, 2, 1);
        menuBar2.add(l2, 3, 0, 2, 1);
        inventory.setTop(test);
        inventory.setBottom(menuBar2);
        root.setBottom(inventory);

        // Game Window
        DungeonControllerLoader dungeonLoader = new DungeonControllerLoader(this.file, menuBar2);
        DungeonController controller = dungeonLoader.loadController();
        controller.setStage(this.stage);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("DungeonView.fxml"));
        loader.setController(controller);
        Node level = loader.load();
        root.setCenter(level);

        // Show Scene
        Scene scene = new Scene(root);
        level.requestFocus();
        this.stage.setTitle("Dungeon");
        this.stage.setScene(scene);
        this.stage.show();
    }

    /*
    @FXML
    public void handleRestartButton(ActionEvent event) throws IOException {
        System.out.println("Found restart button!!!");
    }
    */

}