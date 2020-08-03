package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.scene.control.CheckBox;
import unsw.dungeon.*;

/**
 * Testing functionality of Player interacting and utilising a Sword
 * @author Sean Smith
 * @author Austin Landry
 */
public class SwordTest {
    private Dungeon dungeon;
    private Player player;

    @BeforeEach
    public void initialize() {
        // Create dungeon.
        this.dungeon = new Dungeon(10, 10);
        CheckBox placeholder = new CheckBox();
        // Give the dungeon a random goal (to avoid null pointer error).
        GoalTreasure goalTreasure = new GoalTreasure(placeholder);
        this.dungeon.setGoal(goalTreasure);
        // Create Player.
        this.player = new Player(1, 4, dungeon);
        dungeon.setPlayer(player);
        dungeon.addEntity(player);
    }

    // Test that when the Sword is picked up, it is not in the dungeon and is in the Players possession.
    @Test
    public void SwordTest1() {
        // Place a Sword in front of the Player and make them pick it up
        Sword s = new Sword(2, 4, dungeon);
        dungeon.addEntity(s);
        assertTrue(!player.hasSword());
        assertTrue(dungeon.findEntity(s));
        player.move(Direction.RIGHT);

        // Check that the Sword is off the map and in the Players possession.
        assertTrue(player.hasSword());
        assertTrue(!dungeon.findEntity(s));
    }

    // Test that a Player can only hold one Sword at a time.
    @Test
    public void SwordTest2() {
        // Place two Swords in front of the Player and make them pick up one.
        Sword s1 = new Sword(2, 4, dungeon);
        dungeon.addEntity(s1);
        Sword s2 = new Sword(3, 4, dungeon);
        dungeon.addEntity(s2);
        player.move(Direction.RIGHT);
        assertTrue(player.hasSword());

        // Now try and pick up the second Sword. Check that the Players coordinates are the same (because they cannot interact with a second Sword once Player already has one).
        player.move(Direction.RIGHT);
        assertEquals(2, player.getX());
        assertEquals(4, player.getY());
    }

    // Test that Enemies get destroyed in one hit from a Sword. i.e. When the Player has a Sword and stands on an Enemy, the Enemy is no longer in the dungeon.
    @Test
    public void SwordTest3() {
        // Place a Sword in front of the Player and make them pick it up.
        Sword s = new Sword(2, 4, dungeon);
        dungeon.addEntity(s);
        player.move(Direction.RIGHT);

        // Place an Enemy in front of the Player.
        Enemy en = new Enemy(3, 4, dungeon);
        dungeon.addEntity(en);

        // Make the Player attack the enemy and check that the enemy has been removed from the dungeon, while the Player is not.
        assertTrue(dungeon.findEntity(en));
        player.move(Direction.RIGHT);
        assertTrue(!dungeon.findEntity(en));
        assertTrue(dungeon.findEntity(player));
    }

    // Test that a Sword is only capable of 5 hits. After the 5th hit the Sword should disappear.
    @Test
    public void SwordTest4() {
        // Place a Sword in front of the Player and make them pick it up.
        Sword s = new Sword(2, 4, dungeon);
        dungeon.addEntity(s);
        player.move(Direction.RIGHT);

        // Place 5 Enemy in front of the Player.
        Enemy en1 = new Enemy(3, 4, dungeon);
        dungeon.addEntity(en1);
        Enemy en2 = new Enemy(4, 4, dungeon);
        dungeon.addEntity(en2);
        Enemy en3 = new Enemy(5, 4, dungeon);
        dungeon.addEntity(en3);
        Enemy en4 = new Enemy(6, 4, dungeon);
        dungeon.addEntity(en4);
        Enemy en5 = new Enemy(7, 4, dungeon);
        dungeon.addEntity(en5);

        // Make the Player defeat 5 of them. Check that the Sword only disappears after the 5th Enemy.
        player.move(Direction.RIGHT);
        assertTrue(!dungeon.findEntity(en1));
        assertTrue(dungeon.findEntity(player));
        assertTrue(player.hasSword());

        player.move(Direction.RIGHT);
        assertTrue(!dungeon.findEntity(en2));
        assertTrue(dungeon.findEntity(player));
        assertTrue(player.hasSword());

        player.move(Direction.RIGHT);
        assertTrue(!dungeon.findEntity(en3));
        assertTrue(dungeon.findEntity(player));
        assertTrue(player.hasSword());

        player.move(Direction.RIGHT);
        assertTrue(!dungeon.findEntity(en4));
        assertTrue(dungeon.findEntity(player));
        assertTrue(player.hasSword());

        player.move(Direction.RIGHT);
        assertTrue(!dungeon.findEntity(en5));
        assertTrue(dungeon.findEntity(player));
        assertTrue(!player.hasSword());

    }

}

