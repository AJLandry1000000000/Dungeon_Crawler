package unsw.dungeon.entity.model;

import unsw.dungeon.entity.Direction;
import unsw.dungeon.entity.Entity;
import unsw.dungeon.entity.Interactable;
import unsw.dungeon.entity.Moveable;
import unsw.dungeon.entity.model.*;
import unsw.dungeon.Dungeon;

/**
 * The player entity
 * @author Sean Smith & Austin Landry
 *
 */
public class Player extends Entity implements Moveable, Interactable {

    private Dungeon dungeon;
    private Sword sword;
    private int amountTreasures;
    private Potion potion;
    private Key key;

    /**
     * Create a player positioned in square (x,y)
     * @param x
     * @param y
     */
    public Player(Dungeon dungeon, int x, int y) {
        super(x, y);
        this.dungeon = dungeon;
        this.sword = null;
        this.amountTreasures = 0;
        this.potion = null;
        this.key = null;
    }

    public Dungeon getDungeon() {
        return this.dungeon;
    }
    
    public void teleport(int x, int y) {
        x().set(x);
        y().set(y); 
    }
    
    public boolean hasSword() {
        return this.sword != null;
    }
    
    public void giveSword(Sword newSword) {
        this.sword = newSword;
        this.dungeon.removeEntity(newSword);
    }
    
    public boolean hasKey() {
        return this.key != null;
    }
    
    public void giveKey(Key newKey) {
        this.key = newKey;
        this.dungeon.removeEntity(newKey);
    }
    
    public Key getKey() {
        return this.key;
    }
    
    public void takeKey() {
        this.key = null;
    }
    
    public void giveTreasure(Treasure newTreasure) {
        this.amountTreasures++;
        this.dungeon.removeEntity(newTreasure);
    }
    
    public int getAmountTreasures() {
        return amountTreasures;
    }
    
    public boolean hasPotion() {
        return this.potion != null;
    }
    
    public void givePotion(Potion newPotion) {
        this.potion = newPotion;
    }

    public Boolean move(Direction direction) {
        int newX = getX() + direction.getX();
        int newY = getY() + direction.getY();

        // Get the entity at the new X & Y coordinates.
        Entity checkEntity = this.getDungeon().getEntity(newX, newY);

        // Assume that the player cannot interact with the new entity.
        Boolean canInteract = false;
        if (checkEntity instanceof Interactable) {
            System.out.println(checkEntity);
            // The interact method will return true if the player can interact with it.
            canInteract = ((Interactable)checkEntity).interact(this);
        }

        if (checkEntity instanceof Portal) {
            return true;
        }
        // If the player can interact with an entity, it can move onto the entities coordinates, so allow the player to change its coordinates to the new X & Y.
        // If the player cannot interact with an entity, maybe its because there is no entity to interact with, and the player is moving to an empty space. If the space is empty, allow the player to move their.
        // Always check that the new spot is within the dungeon boundaries.
        else if ((canInteract || checkEntity == null) && this.dungeon.checkBoundaries(newX, newY)) {
            x().set(newX);
            y().set(newY);
            return true;
        }
        return false;
    }

    // This method is used by enemy to interact with the player (Enemy attacks the Player).
    // Note that we only have to return false if the Enemy cannot attack the Player because the Player has an Invincibility potion or if they have a Sword. 
    @Override
    public Boolean interact(Entity entity) {
        Enemy attackingEnemy = (Enemy) entity;

        // If the Player has an Invincibility potion, destroy the Enemy, and return false.

        // If the Player has a Sword, destroy the Enemy, change the Sword hits, and return false.

        // Otherwise, destroy the Player, and return true.

        return false;
    }
}
