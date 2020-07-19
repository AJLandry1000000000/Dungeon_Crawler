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
        Player player = (Player)entity;
        if (!player.hasPotion()) {
            Dungeon dungeon = player.getDungeon();
            dungeon.removeEntity(this);
            player.givePotion();
            return true;
        } else {
           return false;
        }
    }
}