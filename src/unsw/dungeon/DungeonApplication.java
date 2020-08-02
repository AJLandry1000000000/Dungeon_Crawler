package unsw.dungeon;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Dungeon Application that reads from a JSON file to create and populate a Dungeon
 *
 * @author Sean Smith
 * @author Austin Landry
 *
 */
public class DungeonApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        // MainMenuScreen mm = new MainMenuScreen(primaryStage);
        // mm.start();

        primaryStage.setTitle("Main Menu");
        MainMenuController mainMenuController = new MainMenuController(primaryStage);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
        loader.setController(mainMenuController);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        root.requestFocus();
        primaryStage.setScene(scene);
        primaryStage.show();

        
        /*
        primaryStage.setTitle("Dungeon");
        // Read from a JSON file
        DungeonControllerLoader dungeonLoader = new DungeonControllerLoader("advanced.json");
        DungeonController controller = dungeonLoader.loadController();
        // JavaFX processing
        FXMLLoader loader = new FXMLLoader(getClass().getResource("DungeonView.fxml"));
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        root.requestFocus();
        primaryStage.setScene(scene);
        primaryStage.show();
        */
    }

    public static void main(String[] args) {
        launch(args);
    }
}
