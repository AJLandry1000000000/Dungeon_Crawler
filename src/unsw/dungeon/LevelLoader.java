package unsw.dungeon;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LevelLoader {

    Stage stage;
    String file;


    public LevelLoader(Stage stage, String file) throws IOException {
        this.stage = stage;
        this.file = file;
    }

    public void load() throws IOException {
        // Overall container
        BorderPane root = new BorderPane();

        
        // Top Menu
        MenuBarController menuBarController = new MenuBarController(this.stage, this.file);
        FXMLLoader menuBar = new FXMLLoader(getClass().getResource("MenuBar.fxml"));
        menuBar.setController(menuBarController);
        Node menu = menuBar.load();
        root.setTop(menu);

        // Inventory
        InventoryController inventoryController = new InventoryController();
        FXMLLoader inventoryBar = new FXMLLoader(getClass().getResource("Inventory.fxml"));
        inventoryBar.setController(inventoryController);
        Node inventory = inventoryBar.load();
        root.setBottom(inventory);
        
        /*
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
        */

        // Game Window
        DungeonControllerLoader dungeonLoader = new DungeonControllerLoader(this.file, ((MenuBar)menu), ((VBox)inventory));
        DungeonController gameController = dungeonLoader.loadController();
        gameController.setStage(this.stage);
        FXMLLoader game = new FXMLLoader(getClass().getResource("DungeonView.fxml"));
        game.setController(gameController);
        Node gameRoot = game.load();
        root.setCenter(gameRoot);        
        

        // Show Scene
        Scene scene = new Scene(root);
        gameRoot.requestFocus();
        this.stage.setTitle("Dungeon");
        this.stage.setScene(scene);
        this.stage.show();
    }


}