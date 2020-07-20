package unsw.dungeon;

import unsw.dungeon.Dungeon;
import unsw.dungeon.Entity;
import unsw.dungeon.Interactable;

/**
 * Door can be opened by a Player with a Key that has matching ID
 */
public class Door extends Entity implements Interactable {

    private Dungeon dungeon;
    private int id;
    private boolean open;
    
    public Door(Dungeon dungeon, int x, int y, int id) {
        super(x, y);
        this.dungeon = dungeon;
        this.open = false;
        this.id = id;
    }

    public Dungeon getDungeon() {
        return this.dungeon;
    }

    public boolean isOpen() {
        return open;
    }

    public void openDoor() {
        this.open = true;
    }

    public int getID() {
        return this.id;
    }
    
    /**
     * If the Door is open, let the Player move through the Door.
     * If the Door is closed, it tests whether the Player has the corresponding key. If the Player does, then open the Door and take the Key from the Player. Otherwise don't open.
     * @param entity - This is a Player. If it is anything else, don't let it interact with the Door.
     */
    @Override
    public Boolean interact(Entity entity) {
        // Only a Player is allowed to Interact with a Door
        if (!(entity instanceof Player)) {
            return false;
        }
        Player player = (Player) entity;
        // If the door is closed
        if (!isOpen()) {
            // Check if the Player is holding a Key
            Key playersKey = player.getKey();
            if (playersKey == null) {
                return false;
            }
            
            // Check if Player has its key.
            if(this.id == playersKey.getId()) {
                // If we do have this doors key, open the door, remove the key from the player, and return true.
                openDoor();
                player.takeKey();
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