package unsw.dungeon;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SuccessScreen {
    private Stage stage;
    private String title;
    private SuccessController controller;
    private Scene scene;

    public SuccessScreen(Stage stage) throws IOException {
        this.stage = stage;
        title = "SUCCESS";
        this.controller = new SuccessController(stage);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Succeeded.fxml"));
        loader.setController(controller);
        Parent root = loader.load();

        this.scene = new Scene(root);
        start();
        System.out.println("Started");
    }

    public void start() {
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }

}