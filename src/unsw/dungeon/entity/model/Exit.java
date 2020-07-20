package unsw.dungeon.entity.model;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entity.Entity;
import unsw.dungeon.entity.Interactable;

public class Exit extends Entity implements Interactable {

    private Dungeon dungeon;

    public Exit(Dungeon dungeon, int x, int y) {
        super(x, y);
        this.dungeon = dungeon;
    }

    public Dungeon getDungeon() {
        return this.dungeon;
    }

    @Override
    public Boolean interact(Entity entity) {
        Dungeon dungeon = this.getDungeon();
        dungeon.reachExit();
        // If goals are completed
        if (dungeon.goalsCompleted()) {
            System.out.println("Level is complete!");
            return true;
        }
        System.out.println("Goals must be completed before entering exit");
        return false;
    }
}