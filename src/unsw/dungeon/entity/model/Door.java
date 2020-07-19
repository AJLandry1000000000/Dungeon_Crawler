package unsw.dungeon.entity.model;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entity.Entity;
import unsw.dungeon.entity.Interactable;

public class Door extends Entity implements Interactable {

    private Dungeon dungeon;
    private int id;
    
    public Door(Dungeon dungeon, int x, int y, int id) {
        super(x, y);
        this.dungeon = dungeon;
        this.id = id;
    }

    @Override
    public Boolean interact(Entity entity) {
        return false;
    }
}