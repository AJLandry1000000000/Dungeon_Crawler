package unsw.dungeon.entity.model;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entity.Entity;
import unsw.dungeon.entity.Interactable;

public class Key extends Entity implements Interactable {

    private Dungeon dungeon;
    private int id;
    
    public Key(Dungeon dungeon, int x, int y, int id) {
        super(x, y);
        this.dungeon = dungeon;
        this.id = id;
    }

    public Dungeon getDungeon() {
        return this.dungeon;
    }
    
    /**
     * 
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * 
     */
    @Override
    public Boolean interact(Entity entity) {
        // Only a Player is allowed to interact with a Key
        if (!(entity instanceof Player)) {
            return false;
        }
        Player player = (Player) entity;
        // If a player is not currently holding a Key
        if (!player.hasKey()) {
            // Player is given the Key which is also removed from the level
            player.giveKey(this);
            System.out.println("Player has picked up a key of ID: " + this.id);
            return true;
        }
        // Otherwise disallow the Player from acquiring the key as the player already is holding a current key
        else {
            return false;
        }  
    }
}