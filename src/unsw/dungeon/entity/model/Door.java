package unsw.dungeon.entity.model;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entity.Entity;
import unsw.dungeon.entity.Interactable;

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
    
    public Boolean isOpen() {
        return this.open;
    }

    public void openDoor() {
        this.open = true;
    }

    public int getID() {
        return this.id;
    }

    /**
     * 
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
            // Determine if the Key ID that the Player is holding, matches the Door's ID
            if(getID() == playersKey.getId()) {
                // The Door is opened and the key is then removed from the Player's possession
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