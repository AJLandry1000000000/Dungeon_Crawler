package unsw.dungeon;

/**
 * The player entity moves around the map interacting with other Entities. 
 * The Enemy can interact with the Player to destroy it and end the game. 
 * Most of the Players code is to support entity interaction.
 * @author Sean Smith
 * @author Austin Landry
 *
 */
public class Player extends Entity implements Moveable, Interactable {

    private Sword sword;
    private int potion;
    private Key key;

    /**
     * Create a player positioned in square (x,y)
     * @param x
     * @param y
     */
    public Player(int x, int y, Dungeon dungeon) {
        super(x, y, dungeon);
        this.sword = null;
        this.potion = 0;
        this.key = null;
    }

    // Getters

    public Sword getSword() {
        return this.sword;
    }

    public Key getKey() {
        return this.key;
    }

    public int potionStepsLeft() {
        return this.potion;
    }

    // Setters
    /**
     * Allow the Player Entity to equip a Sword
     * @param newSword the Sword to be equipped by the Player
     */
    public void giveSword(Sword newSword) {
        this.sword = newSword;
        getDungeon().removeEntity(newSword);
    }

    /**
     * Allow the Player Entity to hold a key
     * @param newKey the key a Player holds until the Player comes in contact with corresponding Door
     */
    public void giveKey(Key newKey) {
        this.key = newKey;
        getDungeon().removeEntity(newKey);
    }

    /**
     * Remove a Key from the Player's inventory
     */
    public void takeKey() {
        this.key = null;
    }

    /**
     * Allow the Player to activate a Potion for 10 steps of invincibility
     * @param potion the Potion to give to the Player
     */
    public void givePotion(Potion potion) {
        getDungeon().removeEntity(potion);
        this.potion = 11;
    }

    /**
     * Each step the Player takes, decrement the number of Invincible steps the Player can make
     */
    public void decrementPotionSteps() {
        if (hasPotion()) {
            System.out.println("Player has " + (this.potion - 1) + " steps of potion left");
            this.potion--;
        }
    }

    /**
     * Move the Player to another location 
     */
    public void teleport(int x, int y) {
        x().set(x);
        y().set(y); 
    }

    // Checkers
    /**
     * Check if the Player is currently wielding a Sword
     * @return true if the player has a Sword already, otherwise false
     */
    public boolean hasSword() {
        return this.sword != null;
    }

    /**
     * Check if the Sword has exceeded the number of uses it has
     */
    public void checkSword() {
        // If we have a Sword and it has zero or less hits left, remove this Sword from the Player.
        if (hasSword() && getSword().getHits() <= 0) {
            this.sword = null;
        }
    }

    /**
     * @return true if player is holding a Key, otherwise false
     */
    public boolean hasKey() {
        return this.key != null;
    }

    /**
     * @return true if the Player has leftover invincible steps
     */
    public boolean hasPotion() {
        return this.potion > 0 ? true : false;
    }

    /**
     * Process the Player's movement given a Direction and check if the new location can be accessed
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
        if (checkEntity.getClass().equals(Interactable.class)) {
            // Check if the Player is allowed to interact with the entity at the new position
            canInteract = ((Interactable)checkEntity).interact(this);
        }

        // If the entity at the new position is a Portal, allow the Player to access it
        if (checkEntity.getClass().equals(Portal.class)) {
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

    /**
     * Note that we only have to return false if the Enemy cannot attack the Player because the Player has an Invincibility potion or if they have a Sword. 
     * This method is used by enemy to interact with the player (Enemy attacks the Player).
     */
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
