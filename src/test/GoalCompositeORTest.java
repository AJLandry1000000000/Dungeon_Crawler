package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import unsw.dungeon.Dungeon;
import unsw.dungeon.DungeonMockLoader;
import unsw.dungeon.entity.Direction;
import unsw.dungeon.entity.Entity;
import unsw.dungeon.entity.model.Boulder;
import unsw.dungeon.entity.model.Exit;
import unsw.dungeon.entity.model.Player;
import unsw.dungeon.entity.model.Switch;


public class GoalCompositeORTest {
    
    private Dungeon dungeon;
    private Player player;

    @BeforeEach
    public void initialise() {
        DungeonMockLoader dungeonLoader = new DungeonMockLoader(JSONDungeons.advancedOR());
        this.dungeon = dungeonLoader.load();
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
        // Check that Goal is incomplete
        assertFalse(dungeon.goalsCompleted());

        player.move(Direction.LEFT);
        player.move(Direction.DOWN);        
        player.move(Direction.DOWN);        
        player.move(Direction.DOWN);
        player.move(Direction.LEFT);
        player.move(Direction.LEFT);
        // Using the player's Location and Direction, get the Entity below the Player
        positionX = player.getX() + Direction.DOWN.getX();
        positionY = player.getY() + Direction.DOWN.getY();
        entity = dungeon.getEntity(positionX, positionY);
        // Check that entity below the Player is a Boulder
        assertTrue(entity instanceof Boulder);

        player.move(Direction.DOWN);
        floor = ((Boulder)entity).getSwitch();
        // Check that the Boulder now has an accompanied switch
        assertNotNull(floor);
        // Check that the switch is activated
        assertTrue(floor.isActivated());
        // Check that Goal is complete
        assertTrue(dungeon.goalsCompleted());
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
        // Using the player's Location and Direction, get the Entity to the right of the Player
        int positionX = player.getX() + Direction.RIGHT.getX();
        int positionY = player.getY() + Direction.RIGHT.getY();
        Entity entity = dungeon.getEntity(positionX, positionY);
        // Check that entity to the right of the Player is a Exit
        assertTrue(entity instanceof Exit);
        // Check that Goal is incomplete
        assertFalse(dungeon.goalsCompleted());

        player.move(Direction.RIGHT);
        // Check Exit flag
        assertTrue(dungeon.checkExitReached());
        // Check that Goal is complete
        assertTrue(dungeon.goalsCompleted());
    }
}

