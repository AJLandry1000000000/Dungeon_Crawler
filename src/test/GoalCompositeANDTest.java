package test;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import javafx.scene.image.ImageView;
import unsw.dungeon.*;


public class GoalCompositeANDTest {
    
    private Dungeon dungeon;
    private Player player;
    private DungeonController controller;

    @BeforeEach
    public void initialise() {
        DungeonMockLoader dungeonLoader = new DungeonMockLoader(JSONDungeons.advanced());
        this.dungeon = dungeonLoader.load();
        this.controller = new DungeonController(dungeon, new ArrayList<ImageView>());
        this.player = dungeon.getPlayer();
    }

    public void moveAndNotify(Direction direction) {
        player.move(direction);
        controller.notifyObservers();
    }
    @Test
    public void enemyTreasureOrder() {
        // Player moves onto Sword
        moveAndNotify(Direction.RIGHT);
        moveAndNotify(Direction.RIGHT);
        moveAndNotify(Direction.RIGHT);
        moveAndNotify(Direction.RIGHT);
        moveAndNotify(Direction.RIGHT);

        // Check Sword Picked Up
        assertTrue(player.hasSword());
        Sword sword = player.getSword();
        assertEquals(5, sword.getHits());

        moveAndNotify(Direction.LEFT);
        // Using the player's Location and Direction, get the Entity below the Player
        int positionX = player.getX() + Direction.DOWN.getX();
        int positionY = player.getY() + Direction.DOWN.getY();
        Entity entity = dungeon.getEntity(positionX, positionY);
        // Check that entity below player is an Enemy
        assertTrue(entity instanceof Enemy);
        
        player.move(Direction.DOWN);
        // Test if Enemy is dead
        assertFalse(dungeon.findEntity(entity));
        // Test for Sword decrement
        assertEquals(4, sword.getHits());
        // Check that Goal is incomplete
        assertFalse(dungeon.goalsCompleted());
        
        player.move(Direction.DOWN);
        player.move(Direction.DOWN);
        player.move(Direction.DOWN);
        player.move(Direction.RIGHT);
        player.move(Direction.RIGHT);
        player.move(Direction.DOWN);
        player.move(Direction.DOWN);
        player.move(Direction.DOWN);
        player.move(Direction.DOWN);
        
        // Using the player's Location and Direction, get the Entity below the Player
        positionX = player.getX() + Direction.DOWN.getX();
        positionY = player.getY() + Direction.DOWN.getY();
        entity = dungeon.getEntity(positionX, positionY);
        // Check that Entity below player is a Treasure
        assertTrue(entity instanceof Treasure);
        
        player.move(Direction.DOWN);
        // Test that treasure is picked up
        assertFalse(dungeon.findEntity(entity));
        // Check that the goal is complete
        assertTrue(dungeon.goalsCompleted());
    }
    
    @Test
    public void treasureEnemyOrder() {
        moveAndNotify(Direction.RIGHT);
        moveAndNotify(Direction.RIGHT);
        moveAndNotify(Direction.RIGHT);
        moveAndNotify(Direction.RIGHT);
        moveAndNotify(Direction.UP);
        moveAndNotify(Direction.RIGHT);
        // Check Sword Picked Up
        assertTrue(player.hasSword());
        Sword sword = player.getSword();
        assertEquals(5, sword.getHits());
        
        moveAndNotify(Direction.RIGHT);
        moveAndNotify(Direction.RIGHT);
        moveAndNotify(Direction.RIGHT);
        moveAndNotify(Direction.RIGHT);
        moveAndNotify(Direction.RIGHT);
        moveAndNotify(Direction.DOWN);
        moveAndNotify(Direction.DOWN);
        moveAndNotify(Direction.DOWN);
        moveAndNotify(Direction.DOWN);
        moveAndNotify(Direction.DOWN);
        moveAndNotify(Direction.DOWN);
        moveAndNotify(Direction.DOWN);
        moveAndNotify(Direction.LEFT);
        moveAndNotify(Direction.DOWN);
        moveAndNotify(Direction.DOWN);
        // Check That the Potion is Picked Up
        assertTrue(player.hasPotion());
        assertEquals(10, player.potionStepsLeft());
        
        moveAndNotify(Direction.LEFT);
        assertEquals(9, player.potionStepsLeft());
        moveAndNotify(Direction.LEFT);
        assertEquals(8, player.potionStepsLeft());
        moveAndNotify(Direction.LEFT);
        assertEquals(7, player.potionStepsLeft());
        // Using the player's Location and Direction, get the Entity to the left of the Player
        int positionX = player.getX() + Direction.LEFT.getX();
        int positionY = player.getY() + Direction.LEFT.getY();
        Entity entity = dungeon.getEntity(positionX, positionY);
        // Check that Entity to the left of the Player is a Treasure
        assertTrue(entity instanceof Treasure);

        moveAndNotify(Direction.LEFT);
        assertEquals(6, player.potionStepsLeft());
        // Test that treasure is picked up
        assertFalse(dungeon.findEntity(entity));
        // Check that Goal is incomplete
        assertFalse(dungeon.goalsCompleted());

        moveAndNotify(Direction.UP);
        assertEquals(5, player.potionStepsLeft());
        moveAndNotify(Direction.UP);
        assertEquals(4, player.potionStepsLeft());
        moveAndNotify(Direction.UP);
        assertEquals(3, player.potionStepsLeft());
        moveAndNotify(Direction.UP);
        assertEquals(2, player.potionStepsLeft());
        moveAndNotify(Direction.UP);
        assertEquals(1, player.potionStepsLeft());
        moveAndNotify(Direction.UP);
        moveAndNotify(Direction.UP);
        moveAndNotify(Direction.RIGHT);
        moveAndNotify(Direction.RIGHT);
        moveAndNotify(Direction.RIGHT);
        
        // Using the player's Location and Direction, get the Entity above the Player
        positionX = player.getX() + Direction.UP.getX();
        positionY = player.getY() + Direction.UP.getY();
        entity = dungeon.getEntity(positionX, positionY);
        // Check that Entity above the Player is an Enemy
        assertTrue(entity instanceof Enemy);

        moveAndNotify(Direction.UP);
        // Test if Enemy is dead
        assertFalse(dungeon.findEntity(entity));
        // Test for Sword decrement
        assertEquals(4, sword.getHits());
        // Check that Goal is complete
        assertTrue(dungeon.goalsCompleted());
        
    }
}

