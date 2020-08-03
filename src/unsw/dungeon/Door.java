package unsw.dungeon;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * Door entity, which corresponds to a Key entity, that locks part of the level
 * @author Sean Smith
 * @author Austin Landry
 */
public class Door extends Entity implements Interactable {

    private int id;
    private BooleanProperty open;
    private BooleanProperty key;
    
    public Door(int x, int y, Dungeon dungeon, int id) {
        super(x, y, dungeon);
        this.open = new SimpleBooleanProperty(false);
        this.key = new SimpleBooleanProperty(false);
        this.id = id;
    }

    public int getID() {
        return this.id;
    }

    public BooleanProperty doorStatus() {
        return this.open;
    }

    public BooleanProperty keyStatus() {
        return this.key;
    }

    public Boolean isOpen() {
        return this.open.get();
    }

    /**
     * Change the Door's state to open
     */
    public void openDoor() {
        this.open.set(true);
    }
    
    /**
     * Change the Door's state to alert
     */
    public void alertDoor() {
        this.key.set(true);
    }

    /**
     * If the Door is open, let the Player move through the Door.
     * If the Door is closed, it tests whether the Player has the corresponding key. 
     * If the Player does, then open the Door and take the Key from the Player, otherwise don't open.
     * @param entity - This is a Player. If it is anything else, don't let it interact with the Door.
     */
    @Override
    public Boolean interact(Entity entity) {
        // Only a Player is allowed to Interact with a Door
        if (!(entity.getClass().equals(Player.class))) {
            return false;
        }
        Player player = (Player)entity;
        // If the door is closed
        if (!isOpen()) {
            // Check if the Player is holding a Key
            Key playersKey = player.getKey();
            if (playersKey == null) {
                dungeon.setConsoleText("Player must find a Key to open this Door");
                return false;
            }
            
            // Check if Player has its key.
            if(this.id == playersKey.getId()) {
                // If we do have this doors key, open the door, remove the key from the player, and return true.
                openDoor();
                player.useKey();
                return true;
            } 
            // Otherwise the Key does not match the Door
            else {
                return false;
            }
        } 
        // Otherwise the Door is already open
        else {
            return true;
        }
    }
}