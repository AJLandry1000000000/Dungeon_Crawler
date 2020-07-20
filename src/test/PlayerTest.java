package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unsw.dungeon.*;

/**
 * NOTE: Player interacts with other entities, gets interacted with by Enemy, and moves around the dungeon. 
 * Player interactions and getting interacted with by Enemy are covered in other test files, so here only movement is tested.
 * @author Austin Landry
 * @author Sean Smith
 */
public class PlayerTest {
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

    // Test Player's general movement.
    @Test
    public void PlayerTest1() {
        player.move(Direction.RIGHT);
        assertEquals(5, player.getX());

        player.move(Direction.LEFT);
        assertEquals(4, player.getX());

        player.move(Direction.UP);
        assertEquals(3, player.getY());

        player.move(Direction.DOWN);
        assertEquals(4, player.getY());        
    }

    // Test can't move off the dungeon map.
    @Test
    public void PlayerTest2() {
        // Reposition the Player to be on the edge of the dungeon.
        dungeon.removeEntity(player);
        this.player = new Player(dungeon, 0, 0);
        dungeon.setPlayer(player);
        dungeon.addEntity(player);

        // Try to move the Player off the dungeon map. Check that the Players position does not change.
        player.move(Direction.LEFT);
        assertEquals(0, player.getX());
        assertEquals(0, player.getY());
        player.move(Direction.UP);
        assertEquals(0, player.getX());
        assertEquals(0, player.getY());
    }

    // Test can't move into walls.
    @Test
    public void PlayerTest3() {
        // Place a Wall next to the Player.
        Wall w = new Wall(5, 4);
        dungeon.addEntity(w);

        // Try to make the Player move through the Wall. Check that the Player does not move.
        player.move(Direction.RIGHT);
        assertEquals(4, player.getX());
        assertEquals(4, player.getY());
    }
}

