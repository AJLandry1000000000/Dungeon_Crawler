package unsw.dungeon;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * A JavaFX controller for the dungeon.
 * @author Sean Smith
 * @author Austin Landry
 */
public class DungeonController {

    @FXML
    private GridPane squares;

    private List<ImageView> initialEntities;

    private Player player;

    private Dungeon dungeon;

    private ArrayList<Entity> enemies;

    private int steps;

    private Stage stage;

    public DungeonController(Dungeon dungeon, List<ImageView> initialEntities) {
        this.dungeon = dungeon;
        this.player = dungeon.getPlayer();
        this.enemies = dungeon.getEnemies();
        this.initialEntities = new ArrayList<>(initialEntities);
        this.steps = 0; 
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Player getPlayer() {
        return this.player;
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

        for (ImageView entity : initialEntities) {
            squares.getChildren().add(entity);
        }
    }

    /**
     * Returns the Euclidean distance from the Player to the Enemy's new coordinates.
     * @param newX - The Enemy's new X coordinate
     * @param newY - The Enemy's new Y coordinate
     */ 
    private double distanceToPlayer(int newX, int newY) {
        return Math.sqrt( Math.pow((player.getX() - newX), 2) + Math.pow((player.getY() - newY), 2) );
    }

    /**
     * Makes the best possible move depending on if we minimise or maximise the distance between Player and Enemy.
     * @param en - The enemy to be moved
     * @param minOrMax - Either "minimise" or "maximise". This will change which of the moves we choose.
     * @param left - the Euclidean distance between Enemy and Player if the Enemy moves left.
     * @param right - the Euclidean distance between Enemy and Player if the Enemy moves right.
     * @param up - the Euclidean distance between Enemy and Player if the Enemy moves up.
     * @param down - the Euclidean distance between Enemy and Player if the Enemy moves down.
     * @param stay - the Euclidean distance between Enemy and Player if the Enemy stays in its current position.
     */
    private void optimalMove(Enemy en, String minOrMax, double left, double right, double up, double down, double stay) {
        ArrayList<Double> moves = new ArrayList<Double>();
        moves.add(left);
        moves.add(right);
        moves.add(up);
        moves.add(down);
        moves.add(stay);
        
        if (minOrMax.equals("minimise")) {
            while (!moves.isEmpty()) {
                double min = Collections.min(moves);
                if (min == left) {
                    if (en.move(Direction.LEFT)) { return; }
                } else if (min == right) {
                    if (en.move(Direction.RIGHT)) { return; }
                } else if (min == up) {
                    if (en.move(Direction.UP)) { return; }
                } else if (min == down) {
                    if (en.move(Direction.DOWN)) { return; }
                } else {
                    // The minimum distance must be from staying. Don't move.
                    return;
                }
                moves.remove(min);
            }
        } else {
            while (!moves.isEmpty()) {
                double max = Collections.max(moves);
                if (max == left) {
                    if (en.move(Direction.LEFT)) { return; }
                } else if (max == right) {
                    if (en.move(Direction.RIGHT)) { return; }
                } else if (max == up) {
                    if (en.move(Direction.UP)) { return; }
                } else if (max == down) {
                    if (en.move(Direction.DOWN)) { return; }
                } else {
                    // The minimum distance must be from staying. Don't move.
                    return;
                }
                moves.remove(max);
            }
        }
    }

    /**
     * Notifies Player and Enemies that the Player has made another step. This will decrease Potion steps left (if a potion is active), 
     * and all Enemy will move. The Enemy movement is dependent on Player having a Potion.
     */
    public void notifyObservers() {
        this.steps++;
        // Notify Player with the current move total.
        player.decrementPotionSteps();

        // If the Goal condition has been completed, do not allow the Enemy to move.
        if (this.dungeon.goalsCompleted()) {
            return;
        }   
        // Consider all the possible moves for each enemy, and make the move which 
        // minimises or maximises the distance between Player and Enemy depending on if the Player has a potion.
        for (Entity e : this.enemies) {
            Enemy en = (Enemy) e;
            // Move left.
            double left = distanceToPlayer(en.getX() - 1, en.getY());
            // Move right.
            double right = distanceToPlayer(en.getX() + 1, en.getY());
            // Move up.
            double up = distanceToPlayer(en.getX(), en.getY() - 1);
            // Move down.
            double down = distanceToPlayer(en.getX(), en.getY() + 1);
            // Move stay.
            double stay = distanceToPlayer(en.getX(), en.getY());

            if (!player.hasPotion()) {
                // Play does not have a potion. So move the enemies closer to the player.
                if (e instanceof Ghost) {
                    if (this.steps % 3 == 0) optimalMove(en, "minimise", left, right, up, down, stay);
                } else {
                    optimalMove(en, "minimise", left, right, up, down, stay);
                }
            } else {
                // Play does have a potion. So move the enemies further from the player.
                if (e instanceof Ghost) {
                    if (this.steps % 3 == 0) optimalMove(en, "maximise", left, right, up, down, stay);
                } else if (this.steps % 2 == 0) {
                    optimalMove(en, "maximise", left, right, up, down, stay);
                }
            }
            this.enemies = dungeon.getEnemies();
        }
    }

    @FXML
    public void handleKeyPress(KeyEvent event) throws IOException {
        // If the Player has already completed all Goals, disallow movement
        checkGoals();
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
        // Check if the players last move completed the goals.
        checkGoals();
        // Update all enemies and potion.
        notifyObservers();
    }

    public void checkGoals() throws IOException {
        if (this.dungeon.goalsCompleted()) {
            System.out.println("All Goals Completed, Level is Complete");
            successScreen();
            return;
        }         
        // If the Player has lost the game, disallow movement
        else if (this.dungeon.isGameOver()) {
            System.out.println("Player has been Defeated");
            failureScreen();
            return;
        }
    }

    public void successScreen() throws IOException {
        SuccessScreen ss = new SuccessScreen(this.stage);
        ss.start();
    }

    public void failureScreen() throws IOException {
        FailScreen fc = new FailScreen(this.stage);
        fc.start();
    }
}

