package unsw.dungeon.entity.model;

import unsw.dungeon.entity.Direction;
import unsw.dungeon.entity.Entity;
import unsw.dungeon.entity.Interactable;
import unsw.dungeon.entity.Moveable;
import unsw.dungeon.Dungeon;

/**
 * The player entity
 * @author Sean Smith & Austin Landry
 *
 */
public class Player extends Entity implements Moveable, Interactable {

    private Dungeon dungeon;
    private Sword sword;
    private int potion;
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
        this.potion = 0;
        this.key = null;
    }

    // Getters
    public Dungeon getDungeon() {
        return this.dungeon;
    }

    public Sword getSword() {
        return this.sword;
    }

    public Key getKey() {
        return this.key;
    }

    // Setters
    /**
     * 
     * @param newSword
     */
    public void giveSword(Sword newSword) {
        this.sword = newSword;
        getDungeon().removeEntity(newSword);
    }

    /**
     * 
     * @param newKey
     */
    public void giveKey(Key newKey) {
        this.key = newKey;
        getDungeon().removeEntity(newKey);
    }

    /**
     * 
     */
    public void takeKey() {
        this.key = null;
    }

    /**
     * 
     * @param potion
     */
    public void givePotion(Potion potion) {
        getDungeon().removeEntity(potion);
        this.potion = 10;
    }

    /**
     * 
     */
    public void decrementPotionSteps() {
        if (hasPotion()) {
            System.out.println("Player has " + this.potion + " steps of potion left");
            this.potion--;
        }
    }

    /**
     * 
     */
    public void teleport(int x, int y) {
        x().set(x);
        y().set(y); 
    }

    // Checkers
    /**
     * 
     * @return
     */
    public boolean hasSword() {
        return this.sword != null;
    }

    /**
     * 
     */
    public void checkSword() {
        // If we have a Sword and it has zero or less hits left, remove this Sword from the Player.
        if (hasSword() && getSword().getHits() <= 0) {
            this.sword = null;
        }
    }

    /**
     * 
     * @return
     */
    public boolean hasKey() {
        return this.key != null;
    }

    /**
     * 
     * @return
     */
    public boolean hasPotion() {
        return this.potion > 0 ? true : false;
    }

    /**
     * 
     */
    @Override
    public Boolean move(Direction direction) {
        Dungeon dungeon = getDungeon();
        // Calculate the new position coordinates
        int newX = getX() + direction.getX();
        int newY = getY() + direction.getY();

        // Determine if there is a current entity at the new position
        Entity checkEntity = dungeon.getEntity(newX, newY);

        // Assume that the player cannot interact with the new entity.
        Boolean canInteract = false;
        if (checkEntity instanceof Interactable) {
            // Check if the Player is allowed to interact with the entity at the new position
            canInteract = ((Interactable)checkEntity).interact(this);
        }

        // If the entity at the new position is a Portal, allow the Player to access it
        if (checkEntity instanceof Portal) {
            return true;
        }

        // If the Player can attack the Enemy, is allowed to interact with a different Entity or if the Player is moving onto an empty space
        // And check that the new position must be within the dungeon boundaries
        if ((canInteract || checkEntity == null) && dungeon.checkBoundaries(newX, newY)) {
            // Allow the Player to change its coordinates to the new position coordinates
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
        Dungeon dungeon = getDungeon();
        // If the enemy does not exist 
        if (!dungeon.findEntity(entity)) {
            return true;
        }
        Enemy attackingEnemy = (Enemy) entity;
        // If the Player has an Invincibility potion
        if (hasPotion()) {
            // The Enemy is destroyed and goal conditions are checked
            System.out.println("Player has killed an Enemy");
            dungeon.removeEntity(attackingEnemy);
            dungeon.goalsCompleted();
        }
        // If the Player has a Sword
        else if (hasSword()) {
            // The Enemy is destroyed, the Sword hits are decremented and goal conditions are checked
            System.out.println("Player has killed an Enemy");
            dungeon.removeEntity(attackingEnemy);
            this.sword.decrementHits();
            checkSword();
            dungeon.goalsCompleted();
        }
        // Otherwise, the Player is killed and game is set to game over
        else {
            System.out.println("Player has been killed by an Enemy");
            dungeon.removeEntity(this);
            dungeon.setGameOver();
        }
        return true;
    }
}
