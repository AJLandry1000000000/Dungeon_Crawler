package unsw.dungeon.entity.model;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entity.Collectable;
import unsw.dungeon.entity.Entity;
import unsw.dungeon.entity.Interactable;

public class Treasure extends Entity implements Interactable, Collectable {

    private Dungeon dungeon;

    public Treasure(Dungeon dungeon, int x, int y) {
        super(x, y);
        this.dungeon = dungeon;
    }

    public Dungeon getDungeon() {
        return this.dungeon;
    }

    @Override
    public Boolean interact(Entity entity) {
        // If the entity interacting with the treasure is not the player, return false.
        if (!(entity instanceof Player)) {
            return false;
        }
        // Give the player the treasure, remove it from the dungeon list of entities, then return true.
        this.getDungeon().removeEntity(this);
        System.out.println("Player has picked up a treasure");
        this.getDungeon().goalsCompleted();
        return true;
    }

}
