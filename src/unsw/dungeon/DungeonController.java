package unsw.dungeon;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;

import unsw.dungeon.entity.Direction;
import unsw.dungeon.entity.model.Enemy;
import unsw.dungeon.entity.model.Player;

/**
 * A JavaFX controller for the dungeon.
 * @author Robert Clifton-Everest
 *
 */
public class DungeonController {

    @FXML
    private GridPane squares;

    private List<ImageView> initialEntities;

    private Player player;

    private Dungeon dungeon;

    private static int moves;

    private ArrayList<Enemy> enemies;

    public DungeonController(Dungeon dungeon, List<ImageView> initialEntities) {
        this.dungeon = dungeon;
        this.player = dungeon.getPlayer();
        this.enemies = dungeon.getEnemies();
        this.initialEntities = new ArrayList<>(initialEntities);
    }

    @FXML
    public void initialize() {
        Image ground = new Image((new File("images/dirt_0_new.png")).toURI().toString());

        // Add the ground first so it is below all other entities
        for (int x = 0; x < dungeon.getWidth(); x++) {
            for (int y = 0; y < dungeon.getHeight(); y++) {
                squares.add(new ImageView(ground), x, y);
            }
        }

        for (ImageView entity : initialEntities)
            squares.getChildren().add(entity);

    }

    private void addMove() {
        this.moves++;
    }

    private void notifyObservers() {
        // Notify Player with the current move total.

        // Move each enemy closer to the Player.

    }

    @FXML
    public void handleKeyPress(KeyEvent event) {
        switch (event.getCode()) {
        case UP:
            player.move(Direction.UP);
            break;
        case DOWN:
            player.move(Direction.DOWN);
            break;
        case LEFT:
            player.move(Direction.LEFT);
            break;
        case RIGHT:
            player.move(Direction.RIGHT);
            break;
        // WASD Controls
        case W:
            player.move(Direction.UP);
            break;
        case S:
            player.move(Direction.DOWN);
            break;
        case A:
            player.move(Direction.LEFT);
            break;
        case D:
            player.move(Direction.RIGHT);
            break;
        default:
            break;
        }
        // Update all enemies.
        notifyObservers();
    }

}

