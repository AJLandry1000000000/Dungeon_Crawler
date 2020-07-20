package test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;

import unsw.dungeon.*;


@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
public class GoalLeafExitTest {
    
    private Dungeon dungeon;
    private Player player;

    @BeforeAll
    public void initialise() {
        DungeonMockLoader dungeonLoader = new DungeonMockLoader(JSONDungeons.maze());
        this.dungeon = dungeonLoader.load();
        this.player = dungeon.getPlayer();
    }

    @Test
    @Order(1)
    public void movement() {
        // Shortcut ~ move the player one move off the exit
        player.teleport(18, 15);
    }

    @Test
    @Order(2)
    public void testBehind() {
        // Gather the position behind the player and determine the entity located there
        int behindX = player.getX() + Direction.UP.getX();
        int behindY = player.getY() + Direction.UP.getY();
        Entity entity = dungeon.getEntity(behindX, behindY);
        // Test that there is only an empty spot behind the player
        assertNull(entity);
    }

    @Test
    @Order(3)
    public void testInFront() {
        // Gather the position in front of the player and determine the entity located there
        int frontX = player.getX() + Direction.DOWN.getX();
        int frontY = player.getY() + Direction.DOWN.getY();
        Entity entity = dungeon.getEntity(frontX, frontY);
        // Test that there is only an exit in front of the player
        assertTrue(entity instanceof Exit);
    }

    @Test
    @Order(4)
    public void testToTheRight() {
        // Gather the position at the right of the player and determine the entity located there
        int rightX = player.getX() + Direction.RIGHT.getX();
        int rightY = player.getY() + Direction.RIGHT.getY();
        Entity entity = dungeon.getEntity(rightX, rightY);
        // Test that there is only a wall to the right of the player
        assertTrue(entity instanceof Wall);
    }

    @Test
    @Order(5)
    public void testToTheLeft() {
        // Gather the position at the left of the player and determine the entity located there
        int leftX = player.getX() + Direction.LEFT.getX();
        int leftY = player.getY() + Direction.LEFT.getY();
        Entity entity = dungeon.getEntity(leftX, leftY);
        // Test that there is only a wall to the left of the player
        assertTrue(entity instanceof Wall);
    }

    @Test
    @Order(6)
    public void reachExit() {
        // Move the Player onto the Exit
        player.move(Direction.DOWN);
        // Check that there are two entities (Player & Exit) at the player's position
        ArrayList<Entity> entities = dungeon.getEntities(player.getX(), player.getY());
        assertEquals(2, entities.size());
        // Check that there is only one Player at the location
        long playerCount = entities
            .stream()
            .filter(x -> x instanceof Player)
            .count();
        assertEquals(1, playerCount);
        // Check that there is only one Exit at the location
        long exitCount = entities
            .stream()
            .filter(x -> x instanceof Exit)
            .count();
        assertEquals(1, exitCount);
        // Check that the goal of reaching the exit has been complete
        assertTrue(dungeon.checkExitReached());
        assertTrue(dungeon.goalsCompleted());
    }

}

