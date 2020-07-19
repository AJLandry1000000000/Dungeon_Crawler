package unsw.dungeon.entity.model;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entity.Collectable;
import unsw.dungeon.entity.Entity;
import unsw.dungeon.entity.Interactable;

public class Key extends Entity implements Interactable, Collectable {

    private Dungeon dungeon;
    private int id;
    
    public Key(Dungeon dungeon, int x, int y, int id) {
        super(x, y);
        this.dungeon = dungeon;
        this.id = id;
    }

    @Override
    public Boolean interact(Entity entity) {
        return false;
    }
}