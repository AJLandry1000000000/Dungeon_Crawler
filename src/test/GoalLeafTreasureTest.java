package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unsw.dungeon.*;


public class GoalLeafTreasureTest {
    
    private Dungeon dungeon;
    private Player player;

    @BeforeEach
    public void initialise() {
        DungeonMockLoader dungeonLoader = new DungeonMockLoader(JSONDungeons.simpleTreasure());
        this.dungeon = dungeonLoader.load();
        this.player = dungeon.getPlayer();
    }

    @Test
    public void collectTreasures() {
        player.move(Direction.RIGHT);
        player.move(Direction.RIGHT);

        // Using the player's Location and Direction, get the Entity to the Right of Player
        int positionX = player.getX() + Direction.RIGHT.getX();
        int positionY = player.getY() + Direction.RIGHT.getY();
        Entity key = dungeon.getEntity(positionX, positionY);
        // Check that the Entity is a key
        assertTrue(key instanceof Key);
        player.move(Direction.RIGHT);
        // Check that the Player has picked up the Key
        assertNotNull(player.getKey());

        // Using the player's Location and Direction, get the Entity is below the Player
        positionX = player.getX() + Direction.DOWN.getX();
        positionY = player.getY() + Direction.DOWN.getY();
        Entity door = dungeon.getEntity(positionX, positionY);
        // Check that the Entity is a Door
        assertTrue(door instanceof Door);
        assertEquals(((Door)door).getID(), ((Key)key).getId());

        player.move(Direction.DOWN);
        // Check that the player has successfully walked through a Door
        assertEquals(player.getX(), positionX);
        assertEquals(player.getY(), positionY);

        // Using the player's Location and Direction, get the Entity is below the Player
        positionX = player.getX() + Direction.DOWN.getX();
        positionY = player.getY() + Direction.DOWN.getY();
        Entity treasure = dungeon.getEntity(positionX, positionY);
        // Check that the Entity is a Treasure
        assertTrue(treasure instanceof Treasure);

        // Player Collects the Treasure
        player.move(Direction.DOWN);
        assertFalse(dungeon.findEntity(treasure));
        // Check that the goal condition has not been completed
        assertFalse(dungeon.goalsCompleted());

        // Using the player's Location and Direction, get the Entity is to the left of the Player
        positionX = player.getX() + Direction.LEFT.getX();
        positionY = player.getY() + Direction.LEFT.getY();
        treasure = dungeon.getEntity(positionX, positionY);
        // Check that the Entity is a Treasure
        assertTrue(treasure instanceof Treasure);

        player.move(Direction.LEFT);
        assertFalse(dungeon.findEntity(treasure));
        // Check that the goal condition has not been completed
        assertTrue(dungeon.goalsCompleted());
    }
}