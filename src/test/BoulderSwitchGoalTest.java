package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;

import javafx.scene.image.ImageView;
import unsw.dungeon.Dungeon;
import unsw.dungeon.DungeonController;
import unsw.dungeon.DungeonMockLoader;
import unsw.dungeon.entity.Direction;
import unsw.dungeon.entity.Entity;
import unsw.dungeon.entity.model.Boulder;
import unsw.dungeon.entity.model.Player;
import unsw.dungeon.entity.model.Switch;


@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
public class BoulderSwitchGoalTest {
    
    private Dungeon dungeon;
    private Player player;
    private DungeonController controller;

    @BeforeAll
    public void initialise() {
        DungeonMockLoader dungeonLoader = new DungeonMockLoader(JSONDungeons.boulders());
        this.dungeon = dungeonLoader.load();
        this.controller = new DungeonController(dungeon, new ArrayList<ImageView>());
        this.player = dungeon.getPlayer();
    }

    @Test
    @Order(1)
    public void firstSwitch() {
        // Player moves Boulder onto a Switch
        player.move(Direction.RIGHT);
        player.move(Direction.UP);
        player.move(Direction.RIGHT);
        player.move(Direction.RIGHT);
        player.move(Direction.DOWN);
        player.move(Direction.LEFT);
        player.move(Direction.LEFT);
        player.move(Direction.LEFT);
        // Using the player's Location and Direction, get the Switch
        int positionX = player.getX() + Direction.LEFT.getX();
        int positionY = player.getY() + Direction.LEFT.getY();
        Switch floor = ((Switch)dungeon.getEntity(positionX, positionY));
        // Check that the switch is activated
        assertTrue(floor.isActivated());
        // Check that the goal condition has not been completed
        assertFalse(dungeon.goalsCompleted());
    }

    @Test
    @Order(2)
    public void secondSwitch() {
        player.move(Direction.RIGHT);
        player.move(Direction.RIGHT);
        player.move(Direction.RIGHT);
        player.move(Direction.DOWN);
        player.move(Direction.DOWN);
        player.move(Direction.DOWN);
        player.move(Direction.LEFT);
        player.move(Direction.DOWN);
        // Using the player's Location and Direction, get the Switch
        int positionX = player.getX() + Direction.DOWN.getX();
        int positionY = player.getY() + Direction.DOWN.getY();
        Switch floor = ((Switch)dungeon.getEntity(positionX, positionY));
        // Check that the switch is activated
        assertTrue(floor.isActivated());
        // Check that the goal condition has not been completed
        assertFalse(dungeon.goalsCompleted());
    }

    @Test
    @Order(3)
    public void thirdSwitch() {
        player.move(Direction.LEFT);
        player.move(Direction.DOWN);
        player.move(Direction.LEFT);
        player.move(Direction.LEFT);
        player.move(Direction.UP);
        player.move(Direction.UP);
        // Using the player's Location and Direction, get the Switch
        int positionX = player.getX() + Direction.UP.getX();
        int positionY = player.getY() + Direction.UP.getY();
        Switch floor = ((Switch)dungeon.getEntity(positionX, positionY));
        // Check that the switch is activated
        assertTrue(floor.isActivated());
        // Check that the goal condition has not been completed
        assertFalse(dungeon.goalsCompleted());        
    }

    @Test
    @Order(4)
    public void fourthSwitch() {
        player.move(Direction.DOWN);
        player.move(Direction.DOWN);
        player.move(Direction.RIGHT);
        player.move(Direction.RIGHT);
        player.move(Direction.UP);
        player.move(Direction.RIGHT);
        player.move(Direction.RIGHT);
        // Using the player's Location and Direction, get the Switch
        int positionX = player.getX() + Direction.RIGHT.getX();
        int positionY = player.getY() + Direction.RIGHT.getY();
        Switch floor = ((Switch)dungeon.getEntity(positionX, positionY));
        // Check that the switch is activated
        assertTrue(floor.isActivated());
        // Check that the goal condition has not been completed
        assertFalse(dungeon.goalsCompleted());          
    }

    @Test
    @Order(5)
    public void fifthSwitch() {
        player.move(Direction.UP);
        player.move(Direction.UP);
        player.move(Direction.UP);
        player.move(Direction.UP);
        player.move(Direction.LEFT);
        player.move(Direction.LEFT);
        player.move(Direction.DOWN);
        player.move(Direction.RIGHT);
        // Using the player's Location and Direction, get the Switch
        int positionX = player.getX() + Direction.RIGHT.getX();
        int positionY = player.getY() + Direction.RIGHT.getY();
        Switch floor = ((Switch)dungeon.getEntity(positionX, positionY));
        // Check that the switch is activated
        assertTrue(floor.isActivated());
        // Check that the goal condition has not been completed
        assertFalse(dungeon.goalsCompleted());     
    }

    @Test
    @Order(6)
    public void sixthSwitch() {
        player.move(Direction.DOWN);
        // Using the player's Location and Direction, get the Switch
        int positionX = player.getX() + Direction.DOWN.getX();
        int positionY = player.getY() + Direction.DOWN.getY();
        Switch floor = ((Switch)dungeon.getEntity(positionX, positionY));
        // Check that the switch is activated
        assertTrue(floor.isActivated());
        // Check that the goal condition has not been completed
        assertFalse(dungeon.goalsCompleted());     
    }

    @Test
    @Order(7)
    public void seventhSwitch() {
        player.move(Direction.RIGHT);
        player.move(Direction.DOWN);
        player.move(Direction.DOWN);
        player.move(Direction.LEFT);
        player.move(Direction.LEFT);
        player.move(Direction.DOWN);
        player.move(Direction.LEFT);
        player.move(Direction.LEFT);
        player.move(Direction.UP);
        player.move(Direction.RIGHT);
        // Using the player's Location and Direction, get the Switch
        int positionX = player.getX() + Direction.RIGHT.getX();
        int positionY = player.getY() + Direction.RIGHT.getY();
        Switch floor = ((Switch)dungeon.getEntity(positionX, positionY));
        // Check that the switch is activated
        assertTrue(floor.isActivated());
    }

    @Test
    @Order(8)
    public void goalsTest() {
        assertTrue(dungeon.goalsCompleted());
    }

    @Test
    @Order(9)
    public void layeringTest() {
        // Using the player's Location and Direction, get the Switch
        int positionX = player.getX() + Direction.RIGHT.getX();
        int positionY = player.getY() + Direction.RIGHT.getY();
        // Check that there are two entities (Switch & Boulder) at that position
        ArrayList<Entity> entities = dungeon.getEntities(positionX, positionY);
        assertEquals(2, entities.size());
        // Check that there is only one switch at the location
        long switchCount = entities
            .stream()
            .filter(x -> x instanceof Switch)
            .count();
        assertEquals(1, switchCount);
        // Check that there is only one switch at the location
        long boulderCount = entities
            .stream()
            .filter(x -> x instanceof Boulder)
            .count();
        assertEquals(1, boulderCount);
    }
}

