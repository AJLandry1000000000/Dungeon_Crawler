package unsw.dungeon.entity.model;

import unsw.dungeon.entity.Collectable;
import unsw.dungeon.entity.Entity;
import unsw.dungeon.entity.Interactable;

public class Treasure extends Entity implements Interactable, Collectable {

    public Treasure(int x, int y) {
        super(x, y);
    }

    @Override
    public Boolean interact(Entity entity) {
        // If the entity interacting with the treasure is not the player, return false.
        if (!(entity instanceof Player)) {
            return false;
        }
        Player player = (Player) entity;
        // Give the player the treasure, remove it from the dungeon list of entities, then return true.
        player.giveTreasure(this);
        return true;
    }

}
