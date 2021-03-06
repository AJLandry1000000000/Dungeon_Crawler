package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.scene.control.CheckBox;

import unsw.dungeon.*;

/**
 * Testing functionality of Player interacting Keys and their corresponding Doors
 * @author Sean Smith
 * @author Austin Landry
 */
public class KeyAndDoorTest {
    private Dungeon dungeon;
    private Player player;

    @BeforeEach
    public void initialize() {
        // Create dungeon.
        this.dungeon = new Dungeon(10, 10);
        CheckBox placeholder = new CheckBox();
        // Give the dungeon a random goal (to avoid null pointer error).
        GoalEnemies ge = new GoalEnemies(placeholder);
        this.dungeon.setGoal(ge);
        // Create Player.
        this.player = new Player(4, 4, dungeon);
        dungeon.setPlayer(player);
        dungeon.addEntity(player);
    }

    // Check that the Player can only hold one Key at a time.
    @Test
    public void KeyAndDoorTest1() {
        // Place two keys to the right of the Player.
        Key k1 = new Key(5, 4, dungeon, 1);
        dungeon.addEntity(k1);
        Key k2 = new Key(6, 4, dungeon, 2);
        dungeon.addEntity(k2);

        // Check that the Player has no keys initially.
        assertTrue(!player.hasKey());

        // Make Player pick up the first Key. Check that the Player now has a key.
        player.move(Direction.RIGHT);
        assertTrue(player.hasKey());

        // Now try to move over the next key. 
        player.move(Direction.RIGHT);

        // The Player already holds a Key, so the Players position should not have changed since picking up the last key.
        assertEquals(5, player.getX());
        assertEquals(4, player.getY());
    }

    // Holding a Key with the same ID as a Door, will allow the Player to open, and walk through the door.
    @Test
    public void KeyAndDoorTest2() {
        // Place two keys to the right of the Player.
        Key k = new Key(5, 4, dungeon, 1);
        dungeon.addEntity(k);
        Door d = new Door(6, 4, dungeon, 1);
        dungeon.addEntity(d);

        // Make Player pick up the Key. Check that the Player now has a key.
        player.move(Direction.RIGHT);
        assertTrue(player.hasKey());

        // Now move the player through the Door.
        player.move(Direction.RIGHT);
        player.move(Direction.RIGHT);

        // Check that the door is open, and that the Player is on the other side of the Door.
        assertTrue(d.isOpen());
        assertEquals(7, player.getX());
        assertEquals(4, player.getY());

        // Check that the Player now has no key.
        assertTrue(player.getKey() == null);
    }

    // Holding a Key with a different ID as a Door, will not allow the Player to open, and walk through the door.
    @Test
    public void KeyAndDoorTest3() {
        // Place a key and a door to the right of the Player.
        Key k = new Key(5, 4, dungeon, 1);
        dungeon.addEntity(k);
        Door d = new Door(6, 4, dungeon, 2);
        dungeon.addEntity(d);

        // Make Player pick up the Key. Check that the Player now has a key.
        player.move(Direction.RIGHT);
        assertTrue(player.hasKey());

        // Now try to move the player through the Door.
        player.move(Direction.RIGHT);
        player.move(Direction.RIGHT);

        // Check that the door is closed, and that the Player is stuck in front of the Door.
        assertTrue(!d.isOpen());
        assertEquals(5, player.getX());
        assertEquals(4, player.getY());


        // Check that the Player still has a key.
        assertTrue(player.getKey() != null);
    }

    // An unopened Door should not let a Player interact with it if the Player has no key.
    @Test
    public void KeyAndDoorTest4() {
        // Place an closed Door to the right of the Player.
        Door d = new Door(5, 4, dungeon, 2);
        dungeon.addEntity(d);

        // Try to move the Player through the door. Confirm that the Player didn't make it through.
        player.move(Direction.RIGHT);
        player.move(Direction.RIGHT);
        assertEquals(4, player.getX());
        assertEquals(4, player.getY());
    }

    // An opened Door should remain open.
    @Test
    public void KeyAndDoorTest5() {
        // Place an open Door to the right of the Player.
        Door d = new Door(5, 4, dungeon, 2);
        dungeon.addEntity(d);
        d.openDoor();

        // Move the Player through the door. Confirm that the Player made it through.
        player.move(Direction.RIGHT);
        player.move(Direction.RIGHT);
        assertEquals(6, player.getX());
        assertEquals(4, player.getY());

        // Move the Player back through the door. Confirm that the Player made it through.
        player.move(Direction.LEFT);
        player.move(Direction.LEFT);
        assertEquals(4, player.getX());
        assertEquals(4, player.getY());
    }

    // A Boulder cannot be pushed through an open door.
    @Test
    public void KeyAndDoorTest6() {
        // Place a Boulder and an open Door to the right of the Player.
        Boulder b = new Boulder(5, 4, dungeon);
        dungeon.addEntity(b);
        Door d = new Door(6, 4, dungeon, 2);
        dungeon.addEntity(d);
        d.openDoor();

        // Try to push the Boulder through the open Door. Check that the Player and the Boulder have not moved.
        player.move(Direction.RIGHT);
        assertEquals(5, b.getX());
        assertEquals(4, b.getY());
        assertEquals(4, player.getX());
        assertEquals(4, player.getY());
    }

}

