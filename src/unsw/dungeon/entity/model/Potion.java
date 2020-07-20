package unsw.dungeon.entity.model;

import unsw.dungeon.entity.Entity;
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
            player.givePotion(this);
            System.out.println("Player has picked up a Potion");
            return true;
        } else {
            System.out.println("Player already has a Potion activated");
           return false;
        }
    }
}