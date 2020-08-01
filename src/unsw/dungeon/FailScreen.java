package unsw.dungeon;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FailScreen {
    private Stage stage;
    private String title;
    private FailController controller;
    private Scene scene;

    public FailScreen(Stage stage) throws IOException {
        this.stage = stage;
        title = "FAILURE";
        this.controller = new FailController(stage);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Failed.fxml"));
        loader.setController(controller);
        Parent root = loader.load();

        this.scene = new Scene(root);
    }

    public void start() {
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }

}