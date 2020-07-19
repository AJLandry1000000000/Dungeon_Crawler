package unsw.dungeon.entity.model;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entity.Entity;
import unsw.dungeon.entity.Interactable;

public class Portal extends Entity implements Interactable {

    private Dungeon dungeon;
    private int id;
    
    public Portal(Dungeon dungeon, int x, int y, int id) {
        super(x, y);
        this.dungeon = dungeon;
        this.id = id;
    }

    public int getID() {
        return this.id;
    }

    public Dungeon getDungeon() {
        return this.dungeon;
    }

    @Override
    public Boolean interact(Entity entity) {
        if (!(entity instanceof Player)) {
            return false;
        }
        Portal portal = this.getDungeon().getPortal(this);
        if (portal == null) {
            return false;
        }
        ((Player)entity).teleport(portal.getX(), portal.getY());
        return true;
    }
}