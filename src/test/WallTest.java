package test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.scene.image.ImageView;

import unsw.dungeon.*;
/**
 * Testing functionality of Player movement and Wall Behaviour
 * @author Sean Smith
 * @author Austin Landry
 */
public class WallTest {
    private Dungeon dungeon;
    private Player player;

    @BeforeEach
    public void initialize() {
        // Create dungeon.
        this.dungeon = new Dungeon(10, 10);
        // Give the dungeon a random goal (to avoid null pointer error).
        GoalTreasure goalTreasure = new GoalTreasure(this.dungeon);
        this.dungeon.setGoal(goalTreasure);
        // Create Player.
        this.player = new Player(dungeon, 4, 4);
        dungeon.setPlayer(player);
        dungeon.addEntity(player);
    }

    // Test that Walls stop the Player from moving through them.
    @Test
    public void WallTest1() {
        // Create a Wall in front of the Player.
        Wall w1 = new Wall(5, 4);
        dungeon.addEntity(w1);
        
        // Repeatedly move the Player into the Wall and make sure that their coordinates have not changed.
        player.move(Direction.RIGHT);
        assertEquals(4, player.getX());
        assertEquals(4, player.getY());
        player.move(Direction.RIGHT);
        assertEquals(4, player.getX());
        assertEquals(4, player.getY());
        player.move(Direction.RIGHT);
        assertEquals(4, player.getX());
        assertEquals(4, player.getY());
    }

    // Test that Walls stop the Enemy from moving through them.
    @Test
    public void WallTest2() {
        // Create an Enemy.
        Enemy enemy = new Enemy(dungeon, 6, 4);

        // Surround the Enemy with walls.
        // Wall above.
        Wall w1 = new Wall(6, 3);
        dungeon.addEntity(w1);
        // Wall below.
        Wall w2 = new Wall(6, 5);
        dungeon.addEntity(w2);
        // Wall in front.
        Wall w3 = new Wall(5, 4);
        dungeon.addEntity(w3);
        // wall behind.
        Wall w4 = new Wall(7, 4);
        dungeon.addEntity(w4);

        // Check that the Enemy hasn't moved (because it's surrounded by walls).
        DungeonController dc = new DungeonController(dungeon, new ArrayList<ImageView>());
        dc.notifyObservers();
        assertEquals(6, enemy.getX());
        assertEquals(4, enemy.getY());
    }

    // Test that Walls stop boulders from moving through them.
    @Test
    public void WallTest3() {
        // Create a Boulder and a Wall to the right of the player.
        Boulder b = new Boulder(dungeon, 5, 4);
        dungeon.addEntity(b);
        Wall w = new Wall(6, 4);
        dungeon.addEntity(w);

        // Try and make the Player push the Boulder into the Wall.
        player.move(Direction.RIGHT);

        // Check that the Boulder remains in the same spot.
        assertEquals(5, b.getX());
        assertEquals(4, b.getY());

        // Check that the Player remains in the same spot.
        assertEquals(4, player.getX());
        assertEquals(4, player.getY());

    }
}

