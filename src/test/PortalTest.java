package test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javafx.scene.image.ImageView;

import unsw.dungeon.*;

public class PortalTest {
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

    // Test that the first portal teleports the Player to the second corresponding Portal. Then check that the Player can come back to the initial Portal through the second Portal.
    @Test
    public void PortalTest1() {
        // Create a Portal in front of the Player and another one elsewhere.
        Portal p1 = new Portal(dungeon, 5, 4, 1);
        dungeon.addEntity(p1);
        Portal p2 = new Portal(dungeon, 1, 1, 1);
        dungeon.addEntity(p2);
        
        //Create Another Portal pair to test that the original pair sends to each other and only to each other.
        Portal p3 = new Portal(dungeon, 5, 5, 2);
        dungeon.addEntity(p3);
        Portal p4 = new Portal(dungeon, 9, 9, 2);
        dungeon.addEntity(p4);

        // Move the Player into the first Portal and check their new coordinates.
        player.move(Direction.RIGHT);
        assertEquals(1, player.getX());
        assertEquals(1, player.getY());

        // Move the Player off the Portal, then back on the Portal. Then check its new coordinates.
        player.move(Direction.RIGHT);
        player.move(Direction.LEFT);
        assertEquals(5, player.getX());
        assertEquals(4, player.getY());
    }

    // Test that a Boulder cannot be pushed through a Portal.
    @Test
    public void PortalTest2() {
        // Create a Boulder and Portal in front of the Player and another Portal elsewhere.
        Boulder b = new Boulder(dungeon, 5, 4);
        dungeon.addEntity(b);
        Portal p1 = new Portal(dungeon, 6, 4, 1);
        dungeon.addEntity(p1);
        Portal p2 = new Portal(dungeon, 1, 1, 1);
        dungeon.addEntity(p2);

        // Get the Player to push the Boulder into the Portal. Check that the Boulder and the Player have not moved.
        player.move(Direction.RIGHT);
        assertEquals(5, b.getX());
        assertEquals(4, b.getY());
        assertEquals(4, player.getX());
        assertEquals(4, player.getY());
    }

    // Test that an Enemy cannot move through a Portal.
    @Test
    public void PortalTest3() {
        // Create Portal and an Enemy in front of the Player and another Portal elsewhere.
        Enemy en = new Enemy(dungeon, 6, 4);
        dungeon.addEntity(en);
        Portal p1 = new Portal(dungeon, 5, 4, 1);
        dungeon.addEntity(p1);
        Portal p2 = new Portal(dungeon, 1, 1, 1);
        dungeon.addEntity(p2);

        // To attack the Player, the Enemy has to move forward. But the Portal is blocking the Enemy, so it should stay in its spot. Check that the Enemy cannot move into the Portal.
        DungeonController dc = new DungeonController(dungeon, new ArrayList<ImageView>());
        dc.notifyObservers();
        assertEquals(6, en.getX());
        assertEquals(4, en.getY());
    }
}

