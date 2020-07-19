package unsw.dungeon.entity.model;

import unsw.dungeon.entity.Entity;
import unsw.dungeon.entity.Collectable;
import unsw.dungeon.entity.Interactable;

public class Sword extends Entity implements Interactable, Collectable {

    private int hits;

    public Sword(int x, int y) {
        super(x, y);
        this.hits = 5;
    }

    @Override
    public Boolean interact(Entity entity) {
        // If the entity interacting with the key is not the player, return false.
        if (!(entity instanceof Player)) {
            return false;
        }
        Player player = (Player) entity;
        
        // If the player has no sword, give it to the player and remove this sword from the dungeons list of entities. Then return true.
        if (!player.hasSword()) {
            player.giveSword(this);
            return true;
        } else {
            // If the player has a sword, do not let them interact with the sword. Just return false.
            return false;
        }  
    }

    public void decrementHits() {
        hits--;
    }

    public int getHits() {
        return hits;
    }
    

}