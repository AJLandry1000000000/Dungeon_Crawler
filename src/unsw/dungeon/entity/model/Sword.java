package unsw.dungeon.entity.model;

import unsw.dungeon.entity.Entity;
import unsw.dungeon.entity.Interactable;

public class Sword extends Entity implements Interactable {

    private int hits;

    public Sword(int x, int y) {
        super(x, y);
        this.hits = 5;
    }

    public int getHits() {
        return this.hits;
    }

    public void decrementHits() {
        this.hits--;
    }

    /**
     * 
     */
    @Override
    public Boolean interact(Entity entity) {
        // Only a Player is allowed to interact with a Sword
        if (!(entity instanceof Player)) {
            return false;
        }

        Player player = (Player) entity;
        // Determine if the Player does not have a current Sword
        if (!player.hasSword()) {
            // Player acquires the Sword which is then removed from the level
            player.giveSword(this);
            return true;
        } 
        // Otherwise disallow the interaction as the Player has a current Sword
        else {
            return false;
        }  
    }
}