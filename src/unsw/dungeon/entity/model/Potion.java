package unsw.dungeon.entity.model;

import unsw.dungeon.entity.Entity;
import unsw.dungeon.entity.Interactable;

public class Potion extends Entity implements Interactable {

    public Potion(int x, int y) {
        super(x, y);
    }

    /**
     * 
     */
    @Override
    public Boolean interact(Entity entity) {
        // Only a Player is allowed to interact with a Potion
        if (!(entity instanceof Player)) {
            return false;
        }

        Player player = (Player)entity;
        // If the Player does not have a current active Potion
        if (!player.hasPotion()) {
            // Give the Player the Potion and activate it
            player.givePotion(this);
            return true;
        } 
        // Otherwise, disallow the Player from picking up the Potion
        else {
           return false;
        }
    }
}