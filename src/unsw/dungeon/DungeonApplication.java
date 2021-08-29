package unsw.dungeon;

import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Dungeon Application that reads from a JSON file to create and populate a
 * Dungeon
 *
 * @author Sean Smith
 * @author Austin Landry
 *
 */
public class DungeonApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        MainMenuScreen mainMenu = new MainMenuScreen(primaryStage);
        mainMenu.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
