package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.scene.control.CheckBox;
import unsw.dungeon.Boulder;
import unsw.dungeon.Direction;
import unsw.dungeon.Dungeon;
import unsw.dungeon.GoalTreasure;
import unsw.dungeon.Player;
import unsw.dungeon.Switch;
import unsw.dungeon.Wall;

/**
 * Testing functionality of Player interacting with Boulders and Floor Switches
 * 
 * @author Sean Smith
 * @author Austin Landry
 */
public class BoulderAndSwitchTest {
    private Dungeon dungeon;
    private Player player;

    @BeforeEach
    public void initialize() {
        // Create dungeon.
        this.dungeon = new Dungeon(10, 10);
        // Give the dungeon a random goal (to avoid null pointer error).
        CheckBox placeholder = new CheckBox();
        GoalTreasure goalTreasure = new GoalTreasure(placeholder);
        this.dungeon.setGoal(goalTreasure);
        // Create Player.
        this.player = new Player(4, 4, dungeon);
        dungeon.setPlayer(player);
        dungeon.addEntity(player);
    }

    // Test that Player cannot push two Boulders at once.
    @Test
    public void BoulderAndSwitchTest1() {
        // Place two Boulders to the right of the Player.
        Boulder b1 = new Boulder(5, 4, dungeon);
        dungeon.addEntity(b1);
        Boulder b2 = new Boulder(6, 4, dungeon);
        dungeon.addEntity(b2);

        // Make the Player push these Boulders. Check that the Players position does not
        // change.
        player.move(Direction.RIGHT);
        assertEquals(4, player.getX());
        assertEquals(4, player.getY());
    }

    // Test that pushing a Boulder onto a Switch activates the Switch, and pushing a
    // Boulder off a Switch deactivates the Switch.
    @Test
    public void BoulderAndSwitchTest2() {
        // Place a Boulder and a Switch next to the Player.
        Boulder b = new Boulder(5, 4, dungeon);
        dungeon.addEntity(b);
        Switch s = new Switch(6, 4, dungeon);
        dungeon.addEntity(s);

        // Push the Boulder onto the Switch and check that the Switch is activated.
        player.move(Direction.RIGHT);
        assertTrue(s.isActivated());

        // Push the Boulder off the Switch and check that the Switch is not activated.
        player.move(Direction.RIGHT);
        assertTrue(!s.isActivated());
    }

    // Test that Boulder can not be pushed into a wall.
    // Note: In other tests we already try and push a boulder onto interactable
    // items, and prove that it is not allowed. Wall is the only other
    // non-interactable entity, so we test that here.
    @Test
    public void BoulderAndSwitchTest3() {
        // Place a Boulder and a Wall next to the Player.
        Boulder b = new Boulder(5, 4, dungeon);
        dungeon.addEntity(b);
        Wall w = new Wall(6, 4, dungeon);
        dungeon.addEntity(w);

        // Try and push the Boulder into the Wall.
        player.move(Direction.RIGHT);
        assertEquals(4, player.getX());
        assertEquals(4, player.getY());

    }
}
