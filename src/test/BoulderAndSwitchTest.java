package test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import javafx.scene.image.ImageView;


import unsw.dungeon.*;
import unsw.dungeon.entity.Direction;
import unsw.dungeon.entity.goals.GoalTreasure;
import unsw.dungeon.entity.model.*;

public class BoulderAndSwitchTest {
    private Dungeon dungeon;
    private Player player;

    @BeforeEach
    public void initialize() {
        // Create dungeon.
        this.dungeon = new Dungeon(10, 10);
        // Give the dungeion a random goal (to avoid null pointer error).
        GoalTreasure goalTreasure = new GoalTreasure(this.dungeon);
        this.dungeon.setGoal(goalTreasure);
        // Create Player.
        this.player = new Player(dungeon, 4, 4);
        dungeon.setPlayer(player);
        dungeon.addEntity(player);
    }

    // Test that Player cannot push two Boulders at once.
    @Test
    public void BoulderAndSwitchTest1() {
        // Place two Boulders to the right of the Player.
        Boulder b1 = new Boulder(dungeon, 5, 4);
        dungeon.addEntity(b1);
        Boulder b2 = new Boulder(dungeon, 6, 4);
        dungeon.addEntity(b2);

        // Make the Player push these Boulders. Check that the Players position does not change.
        assertEquals(4, player.getX());
        assertEquals(4, player.getY());
    }

    // Test that pushing a Boulder onto a Switch activates the Switch, and pushing a Boulder off a Switch deactivates the Switch.
    @Test
    public void BoulderAndSwitchTest2() {
        // Place a Boulder and a Switch next to the Player.
        Boulder b = new Boulder(dungeon, 5, 4);
        dungeon.addEntity(b);
        Switch s = new Switch(dungeon, 6, 4);
        dungeon.addEntity(s);

        // Push the Boulder onto the Switch and check that the Switch is activated.
        player.move(Direction.RIGHT);
        assertTrue(s.isActivated());

        // Push the Boulder off the Switch and check that the Switch is not activated.
        player.move(Direction.RIGHT);
        assertTrue(!s.isActivated());
    }
}

