package test;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;


import javafx.scene.image.ImageView;
import unsw.dungeon.Dungeon;
import unsw.dungeon.DungeonController;
import unsw.dungeon.DungeonMockLoader;
import unsw.dungeon.entity.Direction;
import unsw.dungeon.entity.Entity;
import unsw.dungeon.entity.model.Boulder;
import unsw.dungeon.entity.model.Enemy;
import unsw.dungeon.entity.model.Player;
import unsw.dungeon.entity.model.Switch;
import unsw.dungeon.entity.model.Sword;
import unsw.dungeon.entity.model.Treasure;


public class GoalCompositeORTest {
    
    private Dungeon dungeon;
    private Player player;
    private DungeonController controller;

    @BeforeEach
    public void initialise() {
        DungeonMockLoader dungeonLoader = new DungeonMockLoader(JSONDungeons.advancedOR());
        this.dungeon = dungeonLoader.load();
        this.controller = new DungeonController(dungeon, new ArrayList<ImageView>());
        this.player = dungeon.getPlayer();
    }

    @Test
    public void boulderGoal() {
        player.move(Direction.RIGHT);
        player.move(Direction.RIGHT);
        player.move(Direction.RIGHT);
        player.move(Direction.RIGHT);
        // Using the player's Location and Direction, get the Entity to the right of the Player
        int positionX = player.getX() + Direction.RIGHT.getX();
        int positionY = player.getY() + Direction.RIGHT.getY();
        Entity entity = dungeon.getEntity(positionX, positionY);
        // Check that entity to the right of the Player is a Boulder
        assertTrue(entity instanceof Boulder);

        player.move(Direction.RIGHT);
        Switch floor = ((Boulder)entity).getSwitch();
        // Check that the Boulder now has an accompanied switch
        assertNotNull(floor);
        // Check that the switch is activated
        assertTrue(floor.isActivated());

        player.move(Direction.LEFT);
        player.move(Direction.DOWN);        
        player.move(Direction.DOWN);        
        player.move(Direction.DOWN);
        player.move(Direction.LEFT);
        player.move(Direction.LEFT);
        // Using the player's Location and Direction, get the Entity to below the Player
        positionX = player.getX() + Direction.DOWN.getX();
        positionY = player.getY() + Direction.DOWN.getY();
        entity = dungeon.getEntity(positionX, positionY);
        // Check that entity below the Player is a Boulder
        assertTrue(entity instanceof Boulder);
        // Check Down
        player.move(Direction.DOWN);
        // Boulder Pushed
    }
    
    @Test
    public void exitGoal() {
        player.move(Direction.DOWN);
        player.move(Direction.DOWN);
        player.move(Direction.DOWN);
        player.move(Direction.DOWN);
        player.move(Direction.DOWN);
        player.move(Direction.DOWN);
        player.move(Direction.RIGHT);
        player.move(Direction.RIGHT);
        player.move(Direction.RIGHT);
        // check exit to the right
        player.move(Direction.RIGHT);
        // move onto exit
    }
}

