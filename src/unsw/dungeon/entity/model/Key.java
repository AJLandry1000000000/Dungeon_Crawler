package unsw.dungeon.entity.model;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entity.Collectable;
import unsw.dungeon.entity.Entity;
import unsw.dungeon.entity.Interactable;


/**
 * Key is held by the Player to open a Door. Key is essentially just an ID to be compared to Door IDs.
 * @author Sean Smith & Austin Landry
 */
public class Key extends Entity implements Interactable, Collectable {

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
     * If the Player doesn't have a Key, give the Player the Key. Otherwise don't let the Player interact with the Key.
     * @param entity - This is a Player. If it is anything else, don't let it interact with the Door.
     */
    @Override
    public Boolean interact(Entity entity) {
        if (!(entity instanceof Player)) {
            return false;
        }
        Player player = (Player) entity;
        // If a player does not have a key, give this key to the player and remove it from the dungeon list entities. Then return true.
        if (!player.hasKey()) {
            player.giveKey(this);
            System.out.println("Player has picked up a key of ID: " + this.id);
            return true;
        } else {
            // If a player already has a key. Do nothing with this key. Return false.
            return false;
        }  
    }

    public int getId() {
        return id;
    }

    
}