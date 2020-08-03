package unsw.dungeon;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class MenuBarController {
    @FXML
    private MenuItem Restart;

    @FXML
    private MenuItem Menu;

    @FXML
    private MenuItem Goals;

    @FXML
    private MenuItem Theme;    
    
    private Stage stage;
    
    private String file;

    private Dungeon dungeon;

    private DungeonControllerLoader dungeonLoader;

    private VBox goals;


    public MenuBarController(Stage stage, String file, DungeonControllerLoader dungeonLoader, VBox goals) throws FileNotFoundException {
        this.goals = goals;
        this.stage = stage;
        this.file = file;
        this.dungeonLoader = dungeonLoader;
    }

    public void setDungeon(Dungeon dungeon) {
        this.dungeon = dungeon;
    }

    @FXML
    public void HandleRestartButton(ActionEvent event) throws IOException {
        LevelLoader levelScreen = new LevelLoader(this.stage, this.file);
        levelScreen.load();
    }

    @FXML
    public void HandleMenuButton(ActionEvent event) throws IOException {
        MainMenuScreen mms = new MainMenuScreen(this.stage);
        mms.start();
    }

    @FXML
    public void HandleGoalButton(ActionEvent event) throws IOException {
        if (file.equals("level1.json")) {
            level1GoalScreen();
        } else if (file.equals("level2.json")) {
            level2GoalScreen();
        } else if (file.equals("level3.json")) {
            level3GoalScreen();
        } else if (file.equals("level4.json")) {
            level4GoalScreen();
        } else if (file.equals("level5.json")) {
            level5GoalScreen();
        } else if (file.equals("level6.json")) {
            level6GoalScreen();
        }
    }

    public void level1GoalScreen() throws IOException {
        // Create a new stage.
        Stage s = new Stage();
        s.setTitle("Goals");

        // Load the goals for this level.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("level1Goals.fxml"));
        Parent root = loader.load();
        Scene sc = new Scene(root);

        ArrayList<Goal> level1Goals = this.dungeon.getGoalTypes();
        for (Goal g : level1Goals) {
            // Get each specific goal.
            if (g.getClass().equals(GoalBoulders.class)) {
                GoalBoulders gb = (GoalBoulders) g;
                if (gb.isCompleted(this.dungeon)) {
                    Image im = new Image((new File("images/success.png")).toURI().toString());
                    ImageView i = (ImageView) sc.lookup("#boulderGoalMark");
                    i.setImage(im);
                }
            }
        }

        // Show the stage.
        root.requestFocus();
        s.setScene(sc);
        s.show();
    }

    public void level2GoalScreen() throws IOException {
        // Create a new stage.
        Stage s = new Stage();
        s.setTitle("Goals");

        // Load the goals for this level.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("level2Goals.fxml"));
        Parent root = loader.load();
        Scene sc = new Scene(root);

        ArrayList<Goal> level2Goals = this.dungeon.getGoalTypes();
        for (Goal g : level2Goals) {
            // Get each specific goal.
            if (g.getClass().equals(GoalTreasure.class)) {
                GoalTreasure gt = (GoalTreasure) g;
                if (gt.isCompleted(this.dungeon)) {
                    Image im = new Image((new File("images/success.png")).toURI().toString());
                    ImageView i = (ImageView) sc.lookup("#treasureGoalMark");
                    i.setImage(im);
                }
            } else if (g.getClass().equals(GoalEnemies.class)) {
                GoalEnemies ge = (GoalEnemies) g;
                if (ge.isCompleted(this.dungeon)) {
                    Image im = new Image((new File("images/success.png")).toURI().toString());
                    ImageView i = (ImageView) sc.lookup("#enemyGoalMark");
                    i.setImage(im);
                }
            }
        }

        // Show the stage.
        root.requestFocus();
        s.setScene(sc);
        s.show();
    }


    public void level3GoalScreen() throws IOException {
        // Create a new stage.
        Stage s = new Stage();
        s.setTitle("Goals");

        // Load the goals for this level.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("level3Goals.fxml"));
        Parent root = loader.load();
        Scene sc = new Scene(root);

        ArrayList<Goal> level2Goals = this.dungeon.getGoalTypes();
        for (Goal g : level2Goals) {
            // Get each specific goal.
            if (g.getClass().equals(GoalTreasure.class)) {
                GoalTreasure gt = (GoalTreasure) g;
                if (gt.isCompleted(this.dungeon)) {
                    Image im = new Image((new File("images/success.png")).toURI().toString());
                    ImageView i = (ImageView) sc.lookup("#treasureGoalMark");
                    i.setImage(im);
                }
            } else if (g.getClass().equals(GoalEnemies.class)) {
                GoalEnemies ge = (GoalEnemies) g;
                if (ge.isCompleted(this.dungeon)) {
                    Image im = new Image((new File("images/success.png")).toURI().toString());
                    ImageView i = (ImageView) sc.lookup("#enemyGoalMark");
                    i.setImage(im);
                }
            } else if (g.getClass().equals(GoalExit.class)) {
                GoalExit gex = (GoalExit) g;
                if (gex.isCompleted(this.dungeon)) {
                    Image im = new Image((new File("images/success.png")).toURI().toString());
                    ImageView i = (ImageView) sc.lookup("#exitGoalMark");
                    i.setImage(im);
                }
            } 
        }

        // Show the stage.
        root.requestFocus();
        s.setScene(sc);
        s.show();
    }

    public void level4GoalScreen() throws IOException {
        // Create a new stage.
        Stage s = new Stage();
        s.setTitle("Goals");

        // Load the goals for this level.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("level4Goals.fxml"));
        Parent root = loader.load();
        Scene sc = new Scene(root);

        ArrayList<Goal> level2Goals = this.dungeon.getGoalTypes();
        for (Goal g : level2Goals) {
            // Get each specific goal.
            if (g.getClass().equals(GoalExit.class)) {
                GoalExit gex = (GoalExit) g;
                if (gex.isCompleted(this.dungeon)) {
                    Image im = new Image((new File("images/success.png")).toURI().toString());
                    ImageView i = (ImageView) sc.lookup("#exitGoalMark");
                    i.setImage(im);
                }
            } 
        }

        // Show the stage.
        root.requestFocus();
        s.setScene(sc);
        s.show();
    }

    public void level5GoalScreen() throws IOException {
        // Create a new stage.
        Stage s = new Stage();
        s.setTitle("Goals");

        // Load the goals for this level.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("level5Goals.fxml"));
        Parent root = loader.load();
        Scene sc = new Scene(root);

        ArrayList<Goal> level2Goals = this.dungeon.getGoalTypes();
        for (Goal g : level2Goals) {
            // Get each specific goal.
            if (g.getClass().equals(GoalTreasure.class)) {
                GoalTreasure gt = (GoalTreasure) g;
                if (gt.isCompleted(this.dungeon)) {
                    Image im = new Image((new File("images/success.png")).toURI().toString());
                    ImageView i = (ImageView) sc.lookup("#treasureGoalMark");
                    i.setImage(im);
                }
            } else if (g.getClass().equals(GoalBoulders.class)) {
                GoalBoulders gb = (GoalBoulders) g;
                if (gb.isCompleted(this.dungeon)) {
                    Image im = new Image((new File("images/success.png")).toURI().toString());
                    ImageView i = (ImageView) sc.lookup("#boulderGoalMark");
                    i.setImage(im);
                }
            }
        }

        // Show the stage.
        root.requestFocus();
        s.setScene(sc);
        s.show();
    }

    public void level6GoalScreen() throws IOException {
        // Create a new stage.
        Stage s = new Stage();
        s.setTitle("Goals");

        // Load the goals for this level.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("level6Goals.fxml"));
        Parent root = loader.load();
        Scene sc = new Scene(root);

        ArrayList<Goal> level2Goals = this.dungeon.getGoalTypes();
        for (Goal g : level2Goals) {
            // Get each specific goal.
            if (g.getClass().equals(GoalTreasure.class)) {
                GoalTreasure gt = (GoalTreasure) g;
                if (gt.isCompleted(this.dungeon)) {
                    Image im = new Image((new File("images/success.png")).toURI().toString());
                    ImageView i1 = (ImageView) sc.lookup("#treasureGoalMark1");
                    i1.setImage(im);
                    ImageView i2 = (ImageView) sc.lookup("#treasureGoalMark2");
                    i2.setImage(im);
                }
            } else if (g.getClass().equals(GoalBoulders.class)) {
                GoalBoulders gb = (GoalBoulders) g;
                if (gb.isCompleted(this.dungeon)) {
                    Image im = new Image((new File("images/success.png")).toURI().toString());
                    ImageView i1 = (ImageView) sc.lookup("#boulderGoalMark1");
                    i1.setImage(im);
                    ImageView i2 = (ImageView) sc.lookup("#boulderGoalMark2");
                    i2.setImage(im);
                }
            } else if (g.getClass().equals(GoalExit.class)) {
                GoalExit gex = (GoalExit) g;
                if (gex.isCompleted(this.dungeon)) {
                    Image im = new Image((new File("images/success.png")).toURI().toString());
                    ImageView i1 = (ImageView) sc.lookup("#exitGoalMark1");
                    i1.setImage(im);
                    ImageView i2 = (ImageView) sc.lookup("#exitGoalMark2");
                    i2.setImage(im);
                    ImageView i3 = (ImageView) sc.lookup("#exitGoalMark3");
                    i3.setImage(im);
                }
            } if (g.getClass().equals(GoalEnemies.class)) {
                GoalEnemies ge = (GoalEnemies) g;
                if (ge.isCompleted(this.dungeon)) {
                    Image im = new Image((new File("images/success.png")).toURI().toString());
                    ImageView i1 = (ImageView) sc.lookup("#enemyGoalMark1");
                    i1.setImage(im);
                    ImageView i2 = (ImageView) sc.lookup("#enemyGoalMark2");
                    i2.setImage(im);
                }
            }
        }

        // Show the stage.
        root.requestFocus();
        s.setScene(sc);
        s.show();
    }


    @FXML
    public void HandleGoalOverlay(ActionEvent event) throws IOException {
        goals.setVisible(!goals.isVisible());
    }

    @FXML
    public void HandleNormalButton(ActionEvent event) throws IOException {
        dungeonLoader.changeTheme("normal");
    }

    @FXML
    public void HandleIceButton(ActionEvent event) throws IOException {
        dungeonLoader.changeTheme("ice");
    }

    @FXML
    public void HandleLavaButton(ActionEvent event) throws IOException {
        dungeonLoader.changeTheme("lava");
    }

    @FXML
    public void HandleMossButton(ActionEvent event) throws IOException {
        dungeonLoader.changeTheme("moss");
    }

    @FXML
    public void HandleWaterButton(ActionEvent event) throws IOException {
        dungeonLoader.changeTheme("water");
    }
}