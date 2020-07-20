package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unsw.dungeon.*;

/**
 * Testing functionality of Player interacting with Treasure(s)
 * @author Sean Smith
 * @author Austin Landry
 */
public class TreasureTest {
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

    // Test that when the player picks up treasure, it is removed from the map.
    @Test
    public void TreasureTest1(){

        // Create Treasure.
        Treasure t1 = new Treasure(dungeon, 6, 4);
        Treasure t2 = new Treasure(dungeon, 6, 3);
        dungeon.addEntity(t1);
        dungeon.addEntity(t2);
        
        // Move player towards Treasure, make sure Treasure isn't picked up prematurely.
        player.move(Direction.RIGHT);
        assertTrue(dungeon.findEntity(t1));
        assertTrue(dungeon.findEntity(t2));

        // Pick up treasure and make sure it's been removed from the map.
        player.move(Direction.RIGHT);
        assertTrue(!dungeon.findEntity(t1));
        assertTrue(dungeon.findEntity(t2));

        player.move(Direction.UP);
        assertTrue(!dungeon.findEntity(t1));
        assertTrue(!dungeon.findEntity(t2));
    }    
}

