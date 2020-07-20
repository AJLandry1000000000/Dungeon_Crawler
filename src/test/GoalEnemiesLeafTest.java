package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import javafx.scene.image.ImageView;

import unsw.dungeon.*;

/**
 * Testing Leaf Goal complexity using a Dungeon Mock Loader accessing JSONObject parsed strings
 * Testing Goal completeness based on the Enemy interaction
 * @author Sean Smith
 * @author Austin Landry
 */
public class GoalEnemiesLeafTest {
    
    private Dungeon dungeon;
    private Player player;
    private DungeonController controller;

    @BeforeEach
    public void initialise() {
        DungeonMockLoader dungeonLoader = new DungeonMockLoader(JSONDungeons.simpleEnemies());
        this.dungeon = dungeonLoader.load();
        this.controller = new DungeonController(dungeon, new ArrayList<ImageView>());
        this.player = dungeon.getPlayer();
    }

    public void moveAndNotify(Direction direction) {
        player.move(direction);
        controller.notifyObservers();
    }

    @Test
    public void killEnemies() {
        moveAndNotify(Direction.RIGHT);

        // Using the player's Location and Direction, get the Entity to the Right of Player
        int positionX = player.getX() + Direction.RIGHT.getX();
        int positionY = player.getY() + Direction.RIGHT.getY();
        Entity entity = dungeon.getEntity(positionX, positionY);
        // Check that the Entity is a Potion
        assertTrue(entity instanceof Potion);
        moveAndNotify(Direction.RIGHT);
        // Check that the Player has picked up the Potion
        assertNotNull(player.hasPotion());

        // Using the player's Location and Direction, get the Entity to the Right of Player
        positionX = player.getX() + Direction.RIGHT.getX();
        positionY = player.getY() + Direction.RIGHT.getY();
        Entity key = dungeon.getEntity(positionX, positionY);
        // Check that the Entity is a key
        assertTrue(key instanceof Key);
        moveAndNotify(Direction.RIGHT);
        // Check that the Player has picked up the Key
        assertNotNull(player.getKey());

        // Using the player's Location and Direction, get the Entity is below the Player
        positionX = player.getX() + Direction.DOWN.getX();
        positionY = player.getY() + Direction.DOWN.getY();
        Entity door = dungeon.getEntity(positionX, positionY);
        // Check that the Entity is a Door
        assertTrue(door instanceof Door);
        assertEquals(((Door)door).getID(), ((Key)key).getId());

        moveAndNotify(Direction.DOWN);
        // Check that the player has successfully walked through a Door
        assertEquals(player.getX(), positionX);
        assertEquals(player.getY(), positionY);
        moveAndNotify(Direction.DOWN);

        // Using the player's Location and Direction, get the Entity is below the Player
        positionX = player.getX() + Direction.LEFT.getX();
        positionY = player.getY() + Direction.LEFT.getY();
        entity = dungeon.getEntity(positionX, positionY);
        // Check that the Entity is an Enemy
        assertTrue(entity instanceof Enemy);

        moveAndNotify(Direction.LEFT);
        // Check that the Player has killed an Enemy
        assertFalse(dungeon.findEntity(entity));
        // Check that the goal condition has not been completed
        assertFalse(dungeon.goalsCompleted());

        // Using the player's Location and Direction, get the Entity is below the Player
        positionX = player.getX() + Direction.LEFT.getX();
        positionY = player.getY() + Direction.LEFT.getY();
        entity = dungeon.getEntity(positionX, positionY);
        // Check that the Entity is an Enemy
        assertTrue(entity instanceof Enemy);

        moveAndNotify(Direction.LEFT);
        // Check that the Player has killed an Enemy
        assertFalse(dungeon.findEntity(entity));
        // Check that the goal condition has not been completed
        assertTrue(dungeon.goalsCompleted());
    }
}