package unsw.dungeon;


import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

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
    private Hammer hammer;
    private Potion potion;
    private Key key;

    // JavaFX Properties
    private BooleanProperty swordEquipped;
    private BooleanProperty hammerEquipped;
    private IntegerProperty potionSteps;
    private BooleanProperty holdingKey;
    private IntegerProperty numTreasures;
    private StringProperty action;

    /**
     * Create a player positioned in square (x,y)
     * @param x
     * @param y
     */
    public Player(int x, int y, Dungeon dungeon) {
        super(x, y, dungeon);

        this.sword = null;
        this.hammer = null;
        this.potion = null;
        this.key = null;

        this.swordEquipped = new SimpleBooleanProperty(false);
        this.hammerEquipped = new SimpleBooleanProperty(false);
        this.holdingKey = new SimpleBooleanProperty(false);
        this.potionSteps = new SimpleIntegerProperty(0);
        this.numTreasures = new SimpleIntegerProperty(0);
        this.numTreasures = new SimpleIntegerProperty(0);
        this.action = new SimpleStringProperty("");
    }

    /* ----------------------------- JAVAFX --------------------------------- */
    public BooleanProperty isSwordEquipped() {
        return this.swordEquipped;
    }

    public BooleanProperty isHammerEquipped() {
        return this.hammerEquipped;
    }

    public BooleanProperty isHoldingKey() {
        return this.holdingKey;
    }

    public IntegerProperty potionStepsLeft() {
        return this.potionSteps;
    }

    public IntegerProperty numTreasures() {
        return this.numTreasures;
    }

    public StringProperty actionTaken() {
        return this.action;
    }

    /* ----------------------------- GETTERS -------------------------------- */
    public Sword getSword() {
        return this.sword;
    }

    public Hammer getHammer() {
        return this.hammer;
    }

    public Key getKey() {
        return this.key;
    }

    public Potion getPotion() {
        return this.potion;
    }

    /**
     * @return the number of Treasures the Player has collected
     */
    public int getNumTreasures() {
        return numTreasures().get();
    }

    /* ----------------------------- SETTERS -------------------------------- */
    /**
     * Move the Player to another location 
     */
    public void teleport(int x, int y) {
        x().set(x);
        y().set(y); 
    }

    /**
     * Allow the Player to equip the given Sword
     * @param sword
     */
    public void equipSword(Sword sword) {
        this.sword = sword;
        isSwordEquipped().set(true);
    }

    /**
     * Allow the Player to use the Sword on an enemy, decrementing its hits
     */
    public void useSword() {
        this.sword.decrementHits();
        // If we have a hammer and no more hits, the Hammer is destroyed
        if (this.sword.getHits() < 1) {
            this.swordEquipped.set(false);
            this.sword = null;
        }
    }

    /**
     * Allow the Player to equip a Hammer
     * @param hammer
     */
    public void equipHammer(Hammer hammer) {
        this.hammer = hammer;
        isHammerEquipped().set(true);
    }

    /**
     * Allow the Player to use the Hammer on a wall, decrementing its hits
     */
    public void useHammer() {
        this.hammer.decrementHits();
        // If we have a hammer and no more hits, the Hammer is destroyed
        if (this.hammer.getHits() < 1) {
            this.hammerEquipped.set(false);
            this.hammer = null;
        }
    }

    /**
     * Allow the Player to activate a Potion for 10 steps of invincibility
     * @param potion the Potion to give to the Player
     */
    public void drinkPotion(Potion potion) {
        this.potion = potion;
        potionStepsLeft().set(11);
    }

    /**
     * Decrement the amount of Invincibility steps the Player has left
     */
    public void decrementPotionSteps() {
        if (hasPotion()) {
            potionStepsLeft().set(potionStepsLeft().get() - 1);
            dungeon.setConsoleText("Player has " + (potionStepsLeft().get() - 1) + " steps of potion left");
        }
    }

    /**
     * Collect the key and transition the corresponding Door to have a visual alert
     * @param key
     */
    public void acquireKey(Key key) {
        this.key = key;
        dungeon.alertDoor(key.getId());
        isHoldingKey().set(true);
    }

    /**
     * Use the key on the corresponding Door removing it from the Player's inventory
     */
    public void useKey() {
        isHoldingKey().set(false);
        this.key = null;
        dungeon.setConsoleText("Player has used a Key to open a Door");
    }

    /**
     * Increment the treasure count of the Player
     */
    public void addTreasure() {
        numTreasures().set(getNumTreasures() + 1);
    }


    /* ----------------------------- CHECKERS ------------------------------- */
    /**
     * @return true if the Player has leftover invincible steps
     */
    public boolean hasPotion() {
        return potionStepsLeft().get() > 0 ? true : false;
    }

    /**
     * Check if the Player is currently wielding a hammer
     * @return true if the player has a hammer already, otherwise false
     */
    public boolean hasHammer() {
        return isHammerEquipped().get();
    }

    /**
     * Check if the Player is currently wielding a Sword
     * @return true if the player has a Sword already, otherwise false
     */
    public boolean hasSword() {
        return isSwordEquipped().get();
    }

    /**
     * Check if the Player is currently holding a Key
     * @return true if the player has a Key already, otherwise false
     */
    public boolean hasKey() {
        return isHoldingKey().get();
    }


    /**
     * Process the Player's movement given a Direction and check if the new location can be accessed
     */
    @Override
    public Boolean move(Direction direction) {
        // Calculate the new position coordinates
        int newX = getX() + direction.getX();
        int newY = getY() + direction.getY();

        // Determine if there is a current entity at the new position
        Entity checkEntity = dungeon.getEntity(newX, newY);

        // Assume that the player cannot interact with the new entity.
        Boolean canInteract = false;
        if (checkEntity != null && checkEntity instanceof Interactable) {
            // Check if the Player is allowed to interact with the entity at the new position
            canInteract = ((Interactable)checkEntity).interact(this);
        }

        // If the Player has no potion and is walking on an empty square, reset the console display
        if (checkEntity == null && !hasPotion()) {
            dungeon.clearConsoleText();

        }
        // If the entity at the new position is a Portal, allow the Player to access it
        if (checkEntity != null && checkEntity.getClass().equals(Portal.class)) {
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
        Enemy attackingEnemy = (Enemy)entity;
        // If the Player has an Invincibility potion
        if (hasPotion()) {
            // The Enemy is destroyed and goal conditions are checked
            dungeon.setConsoleText("Player has killed an Enemy");
            dungeon.removeEntity(attackingEnemy);
            dungeon.goalsCompleted();
        }
        // If the Player has a Sword
        else if (hasSword()) {
            // The Enemy is destroyed, the Sword hits are decremented and goal conditions are checked
            dungeon.setConsoleText("Player has killed an Enemy");
            dungeon.removeEntity(attackingEnemy);
            useSword();
            dungeon.goalsCompleted();
        }
        // Otherwise, the Player is killed and game is set to game over
        else {
            dungeon.setConsoleText("Player has been killed by an Enemy");
            dungeon.removeEntity(this);
            dungeon.setGameOver();
        }
        return true;
    }
}
