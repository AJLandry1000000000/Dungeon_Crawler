package unsw.dungeon.entity.model;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entity.Collectable;
import unsw.dungeon.entity.Entity;
import unsw.dungeon.entity.Interactable;

public class Key extends Entity implements Interactable, Collectable {

    private Dungeon dungeon;
    private int id;
    
    public Key(Dungeon dungeon, int x, int y, int id) {
        super(x, y);
        this.dungeon = dungeon;
        this.id = id;
    }

    @Override
    public Boolean interact(Entity entity) {
        if (!(entity instanceof Player)) {
            return false;
        }
        Player player = (Player) entity;
        // If a player does not have a key, give this key to the player and remove it from the dungeon list entities. Then return true.
        if (!player.hasKey()) {
            player.giveKey(this);
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