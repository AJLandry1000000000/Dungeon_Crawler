package unsw.dungeon;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainMenuScreen {
    private Stage stage;
    private String title;
    private MainMenuController controller;
    private Scene scene;
    

    public MainMenuScreen(Stage stage) {
        this.stage = stage;
        this.title = "Main Menu";
        this.controller = new MainMenuController(stage);
    }

    public void start() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
        loader.setController(controller);
        Parent root = loader.load();
        this.scene = new Scene(root);
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