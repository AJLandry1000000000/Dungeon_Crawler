package unsw.dungeon.entity.model;

import unsw.dungeon.entity.Entity;
import unsw.dungeon.Dungeon;
import unsw.dungeon.entity.Collectable;
import unsw.dungeon.entity.Interactable;

public class Potion extends Entity implements Interactable, Collectable {

    public Potion(int x, int y) {
        super(x, y);
    }

    @Override
    public Boolean interact(Entity entity) {
        if (!(entity instanceof Player)) {
            return false;
        }
        Player player = (Player) entity;
        
        // If the player has no sword, give it to the player and remove this sword from the dungeons list of entities. Then return true.
        if (!player.hasPotion()) {
            Dungeon dungeon = player.getDungeon();
            dungeon.removeEntity(this);
            return true;
        } else {
            // If the player has a sword, do not let them interact with the sword. Just return false.
           return false;
        }
    }
}