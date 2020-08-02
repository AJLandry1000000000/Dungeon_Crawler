package unsw.dungeon;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainMenuScreen {
    private Stage stage;
    private String title;
    private MainMenuController controller;
    private Scene scene;
    

    public MainMenuScreen(Stage stage) throws IOException {
        this.stage = stage;
        title = "Main Menu";
        this.controller = new MainMenuController(stage);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
        loader.setController(controller);
        Parent root = loader.load();
        this.scene = new Scene(root);

    }

    public void start() {
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }

    public MainMenuController getController() {
        return controller;
    }

    public Stage getStage() {
        return this.stage;
    }

}