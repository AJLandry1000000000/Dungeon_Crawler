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
        // If goals are not completed
        if (false) {
            return false;
        }
        // If goals are completed
        this.getDungeon().setLevelCompleted();
        System.out.println("Level is complete!");
        return true;
    }
}