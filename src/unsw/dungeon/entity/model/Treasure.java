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
        return false;
    }

}
