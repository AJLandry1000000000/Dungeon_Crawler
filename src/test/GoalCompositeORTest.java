package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.scene.layout.VBox;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import unsw.dungeon.*;

/**
 * Testing Composite Goal complexity using a Dungeon Mock Loader accessing JSONObject parsed strings
 * Testing Goal completeness where a singular sub goal must be ascertained 
 * @author Sean Smith
 * @author Austin Landry
 */
public class GoalCompositeORTest {
    
    private Dungeon dungeon;
    private Player player;

    @BeforeEach
    public void initialise() {
        DungeonMockLoader dungeonLoader = new DungeonMockLoader(JSONDungeons.advancedOR());
        VBox placeholder = new VBox();
        this.dungeon = dungeonLoader.load(placeholder);
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

