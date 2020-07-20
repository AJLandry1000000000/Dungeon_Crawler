package unsw.dungeon.entity.model;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entity.Entity;
import unsw.dungeon.entity.Interactable;

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
    
    /**
     * If the Door is open, let the Player move through the Door.
     * If the Door is closed, it tests whether the Player has the corresponding key. If the Player does, then open the Door and take the Key from the Player. Otherwise don't open.
     * @param entity - This is a Player. If it is anything else, don't let it interact with the Door.
     */
    @Override
    public Boolean interact(Entity entity) {
        // If the entity interacting with the key is not the player, return false.
        if (!(entity instanceof Player)) {
            return false;
        }
        Player player = (Player) entity;
        
        // If the door is closed...
        if (!this.open) {
            // Get the players key.
            Key playersKey = player.getKey();
            
            // If the player holds no key, return null
            if (playersKey == null) {
                return false;
            }
            
            // Check if we have its key.
            if(this.id == playersKey.getId()) {
                // If we do have this doors key, open the door, remove the key from the player, and return true.
                openDoor();
                player.takeKey();
                return true;
            } else {
                // If we don't have this doors key, return false.
                return false;
            }
        } else {
            // If the door is already open, return true.
            return true;
        }
    }
}