package unsw.dungeon.entity.model;

import unsw.dungeon.entity.Entity;
import unsw.dungeon.entity.Interactable;

public class Portal extends Entity implements Interactable {

    public Portal(int x, int y) {
        super(x, y);
    }
    
    @Override
    public Boolean interact(Entity entity) {
        return false;
    }
}