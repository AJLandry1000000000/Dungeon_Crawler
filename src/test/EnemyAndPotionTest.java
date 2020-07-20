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

public class EnemyAndPotionTest {
    private Dungeon dungeon;
    private Player player;

    @BeforeEach
    public void initialize() {
        // Create dungeon.
        this.dungeon = new Dungeon(10, 10);
        // Give the dungeion a random goal (to avoid null pointer error).
        GoalEnemies ge = new GoalEnemies(this.dungeon);
        this.dungeon.setGoal(ge);
        // Create Player.
        this.player = new Player(dungeon, 4, 4);
        dungeon.setPlayer(player);
        dungeon.addEntity(player);
    }

    // Test that a Player cannot pick up another Potion if they already have a Potion activated. 
    // When a Potion is picked up, it should be removed from the dungeon.
    @Test
    public void EnemyAndPotionTest1() {
        // Place two Potions in front of the Player.
        Potion p1 = new Potion(5, 4);
        dungeon.addEntity(p1);
        Potion p2 = new Potion(6, 4);
        dungeon.addEntity(p2);

        // Make the Player pick up one. Check that the Player has the potion and that the Potion has been removed from the map.
        assertTrue(dungeon.findEntity(p1));
        player.move(Direction.RIGHT);
        assertTrue(player.hasPotion());
        assertTrue(!dungeon.findEntity(p1));

        // Check that the Player cannot step on the second potion because they already have a Potion activated.
        player.move(Direction.RIGHT);
        assertEquals(5, player.getX());
        assertEquals(4, player.getY());
    }

    // Test that the potion only lasts 10 moves.
    @Test
    public void EnemyAndPotionTest2() {
        // Place one Potions in front of the Player and make the Player pick it up.
        Potion p1 = new Potion(5, 4);
        dungeon.addEntity(p1);
        player.move(Direction.RIGHT);
        
        // Make 11 updates to observer. These updates are done in DungeonController after every move. Check each time that the player still has a potion. On the 11th move check that the Player no longer has a potion.
        DungeonController dc = new DungeonController(dungeon, new ArrayList<ImageView>());
        dc.notifyObservers();// Because normally notifyObservers() is run on the move to collecting the potion
    
        // Move 1.
        assertTrue(player.hasPotion());
        dc.notifyObservers();

        // Move 2.
        assertTrue(player.hasPotion());
        dc.notifyObservers();

        // Move 3.
        assertTrue(player.hasPotion());
        dc.notifyObservers();
        
        // Move 4.
        assertTrue(player.hasPotion());
        dc.notifyObservers();
        
        // Move 5.
        assertTrue(player.hasPotion());
        dc.notifyObservers();
        
        // Move 6.
        assertTrue(player.hasPotion());
        dc.notifyObservers();
        
        // Move 7.
        assertTrue(player.hasPotion());
        dc.notifyObservers();
        
        // Move 8.
        assertTrue(player.hasPotion());
        dc.notifyObservers();
        
        // Move 9.
        assertTrue(player.hasPotion());
        dc.notifyObservers();
        
        // Move 10.
        assertTrue(player.hasPotion());
        dc.notifyObservers();
        
        // Move 11.
        assertTrue(!player.hasPotion());
        dc.notifyObservers();
    }

    // Test that when Player doesn't have a Potion, the Enemy move closer to the Player.
    // When the Player takes a potion, the Enemy move further from the Player.
    @Test
    public void EnemyAndPotionTest3() {
        // Reposition the Player at one end of the Dungeon, and an Enemy at the opposite end.
        dungeon.removeEntity(player);
        this.player = new Player(dungeon, 0, 4);
        dungeon.setPlayer(player);
        dungeon.addEntity(player);
        Enemy en = new Enemy(dungeon, 9, 4);
        dungeon.addEntity(en);

        // Check that the Enemy is moving towards the Player after every call to notifyObeservers().
        // Note: notifyObservers() is called after every Player move in DungeonController to indicate that the Player moved. It allows Enemies to make a move. (It also changes how long the potion remains active for.)
        DungeonController dc = new DungeonController(dungeon, new ArrayList<ImageView>());
        dc.notifyObservers();
        assertEquals(8, en.getX());
        assertEquals(4, en.getY());
        
        dc.notifyObservers();
        assertEquals(7, en.getX());
        assertEquals(4, en.getY());

        dc.notifyObservers();
        assertEquals(6, en.getX());
        assertEquals(4, en.getY());

        dc.notifyObservers();
        assertEquals(5, en.getX());
        assertEquals(4, en.getY());

        dc.notifyObservers();
        assertEquals(4, en.getX());
        assertEquals(4, en.getY());

        dc.notifyObservers();
        assertEquals(3, en.getX());
        assertEquals(4, en.getY());

        // Place a Potion in front of the Player. Make the Player take it.
        Potion p = new Potion(1, 4);
        dungeon.addEntity(p);
        player.move(Direction.RIGHT);

        // Check that the Enemy now moves away from the Player after every call to notifyObservers().
        dc.notifyObservers();
        assertEquals(4, en.getX());
        assertEquals(4, en.getY());

        dc.notifyObservers();
        assertEquals(5, en.getX());
        assertEquals(4, en.getY());

        dc.notifyObservers();
        assertEquals(6, en.getX());
        assertEquals(4, en.getY());

        dc.notifyObservers();
        assertEquals(7, en.getX());
        assertEquals(4, en.getY());

        dc.notifyObservers();
        assertEquals(8, en.getX());
        assertEquals(4, en.getY());
    }

    // If an Enemy interacts with Player and Player doesn't have a Sword or a Potion, the Player dies and is removed from the dungeon.
    @Test
    public void EnemyAndPotionTest4() {
        // Put an Enemy in from of the Player and call to notifyObservers() to indicate that the Enemy can move.
        // It then moves on the Player and interacts with the Player. Player has no Sword or Potion. So Player dies and is removed from the dungeon.
        Enemy en = new Enemy(dungeon, 5, 4);
        dungeon.addEntity(en);
        DungeonController dc = new DungeonController(dungeon, new ArrayList<ImageView>());
        dc.notifyObservers();

        // Check that the Player has been removed from the dungeon, and that the game is over.
        assertTrue(!dungeon.findEntity(player));
        assertTrue(dungeon.getGameOver());
    }

    // When the Player has a Potion activated, stepping on an Enemy destroys them, and doesn't change the amount of hits left on any Sword that they might have.
    @Test 
    public void EnemyAndPotionTest5() {
        // Place a Potion, Sword and Enemy next to the Player.
        Potion p = new Potion(5, 4);
        dungeon.addEntity(p);
        Sword s = new Sword(6, 4);
        dungeon.addEntity(s);
        Enemy en = new Enemy(dungeon, 7, 4);
        dungeon.addEntity(en);

        // Make the Player pick up the Potion and Sword. Check that the Player has these items. 
        player.move(Direction.RIGHT);
        player.move(Direction.RIGHT);
        assert(player.hasPotion() && player.hasSword());

        // Move onto the Enemy. Check that Sword hits is still 5, and that the Enemy has been removed from the dungeon.
        assertTrue(dungeon.findEntity(en));
        player.move(Direction.RIGHT);
        assertTrue(!dungeon.findEntity(en));
    }



}

