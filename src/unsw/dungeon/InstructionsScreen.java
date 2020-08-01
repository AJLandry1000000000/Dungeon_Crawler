package unsw.dungeon;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class InstructionsScreen {
    private Stage stage;
    private String title;
    private InstructionsController controller;
    private Scene scene;

    private Image playerImage;

    
    public InstructionsScreen(Stage stage) throws IOException {
        this.stage = stage;
        title = "Instructions";
        this.controller = new InstructionsController(stage);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Instructions.fxml"));
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