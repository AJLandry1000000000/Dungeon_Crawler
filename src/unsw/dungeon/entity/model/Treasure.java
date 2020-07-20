package unsw.dungeon.entity.model;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entity.Entity;
import unsw.dungeon.entity.Interactable;

public class Treasure extends Entity implements Interactable {

    private Dungeon dungeon;

    public Treasure(Dungeon dungeon, int x, int y) {
        super(x, y);
        this.dungeon = dungeon;
    }

    public Dungeon getDungeon() {
        return this.dungeon;
    }

    /**
     * 
     */
    @Override
    public Boolean interact(Entity entity) {
        // Only a Player is allowed to interact with a Treasure
        if (!(entity instanceof Player)) {
            return false;
        }
        // Player acquires the treasure which is removed from the level
        getDungeon().removeEntity(this);
        // Check is all goal conditions have been met
        getDungeon().goalsCompleted();
        return true;
    }

}
