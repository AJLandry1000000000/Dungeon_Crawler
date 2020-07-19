package unsw.dungeon.entity.model;

import unsw.dungeon.entity.Entity;
import unsw.dungeon.entity.Collectable;
import unsw.dungeon.entity.Interactable;

public class Key extends Entity implements Interactable, Collectable {

    public Key(int x, int y) {
        super(x, y);
    }
    
    @Override
    public Boolean interact(Entity entity) {
        return false;
    }
}