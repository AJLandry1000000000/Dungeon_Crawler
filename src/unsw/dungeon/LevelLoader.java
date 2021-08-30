package unsw.dungeon;

import java.io.File;
import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LevelLoader {

    private Stage stage;
    private String file;
    DungeonControllerLoader dungeonLoader;
    MenuBarController menuBarController;

    public LevelLoader(Stage stage, String file) throws IOException {
        this.stage = stage;
        this.file = file;
    }

    public void load() throws IOException {
        // Top for Menu Bar, Center for Game Window & Goal Overlay, Bottom for HUD
        BorderPane root = new BorderPane();

        // Add an overall background to the HUD
        Image inventBG = new Image((new File("images/invent_bg.png")).toURI().toString());

        BackgroundPosition backgroundPosition = new BackgroundPosition(Side.LEFT, 0, false, Side.TOP, 0, false);
        BackgroundImage backgroundImage = new BackgroundImage(inventBG, BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT, backgroundPosition, BackgroundSize.DEFAULT);
        Background background = new Background(backgroundImage);
        root.setBackground(background);

        // Create a HUD Inventory display
        FXMLLoader HUDLoader = new FXMLLoader(getClass().getResource("HUD.fxml"));
        Node HUD = HUDLoader.load();
        root.setBottom(HUD);

        // StackPane allows for the Goals to be over-layed on top of the game window
        StackPane container = new StackPane();
        // Create a Goal Overlay
        VBox goals = new VBox();
        goals.setVisible(false);
        goals.setPadding(new Insets(5, 0, 0, 5));
        goals.setSpacing(2);

        // Game Window
        DungeonControllerLoader dungeonLoader = new DungeonControllerLoader(this.file, ((VBox) HUD));
        this.dungeonLoader = dungeonLoader;
        // Feed the console label into the dungeon controller
        Node console = ((VBox) HUD).lookup("#console");
        DungeonController gameController = dungeonLoader.loadController(goals, ((Label) console));
        gameController.setStage(this.stage);
        FXMLLoader game = new FXMLLoader(getClass().getResource("DungeonView.fxml"));
        game.setController(gameController);
        Node gameRoot = game.load();

        // Set the Top Menu and it's buttons
        MenuBarController menuBarController = new MenuBarController(this.stage, this.file, dungeonLoader, goals);
        this.menuBarController = menuBarController;
        FXMLLoader menuBar = new FXMLLoader(getClass().getResource("MenuBar.fxml"));
        menuBar.setController(menuBarController);
        Node menu = menuBar.load();
        root.setTop(menu);
        menuBarController.setDungeon(gameController.getDungeon());

        // Add the container of the goals and game window to the center of the
        // BorderPane
        container.getChildren().addAll(gameRoot, goals);
        root.setCenter(container);

        // Show Scene
        Scene scene = new Scene(root);
        gameRoot.requestFocus();
        this.stage.setTitle("Dungeon");
        this.stage.setScene(scene);
        this.stage.show();
        this.stage.setMaxWidth(122 * 32);
    }

    public void loadTheme(String theme) {
        this.dungeonLoader.changeTheme(theme);
        this.menuBarController.setTheme(theme);
    }
}