package unsw.dungeon;

import java.io.File;
import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
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

        Image image1 = new Image((new File("images/invent_bg.png")).toURI().toString());
        // create a background fill for this node from the tile image
        BackgroundPosition backgroundPosition = new BackgroundPosition(
                Side.LEFT, 0, false, Side.TOP, 0, false);
        BackgroundImage backgroundImage = new BackgroundImage(image1,
                BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
                backgroundPosition, BackgroundSize.DEFAULT);
        Background background = new Background(backgroundImage);
        // apply that background fill
        root.setBackground(background);
        
        // Top Menu
        MenuBarController menuBarController = new MenuBarController(this.stage, this.file);
        FXMLLoader menuBar = new FXMLLoader(getClass().getResource("MenuBar.fxml"));
        menuBar.setController(menuBarController);
        Node menu = menuBar.load();
        root.setTop(menu);

        // Inventory
        // HUDController HUDController = new HUDController();
        FXMLLoader HUDLoader = new FXMLLoader(getClass().getResource("HUD.fxml"));
        // HUDLoader.setController(HUDController);
        Node HUD = HUDLoader.load();
        root.setBottom(HUD);
        
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
        DungeonControllerLoader dungeonLoader = new DungeonControllerLoader(this.file, ((MenuBar)menu), ((VBox)HUD));
        DungeonController gameController = dungeonLoader.loadController();
        gameController.setStage(this.stage);
        FXMLLoader game = new FXMLLoader(getClass().getResource("DungeonView.fxml"));
        game.setController(gameController);
        Node gameRoot = game.load();
        root.setCenter(gameRoot);        
        
        // Pass the dungeon to the MenuBarController.
        menuBarController.setDungeon(gameController.getDungeon());

        // Show Scene
        Scene scene = new Scene(root);
        gameRoot.requestFocus();
        this.stage.setTitle("Dungeon");
        this.stage.setScene(scene);
        this.stage.show();
        this.stage.setMaxWidth(122 * 32);
    }


}