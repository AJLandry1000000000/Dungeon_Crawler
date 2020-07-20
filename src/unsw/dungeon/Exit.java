package unsw.dungeon;

import unsw.dungeon.Dungeon;
import unsw.dungeon.Entity;
import unsw.dungeon.Interactable;

public class Exit extends Entity implements Interactable {

    private Dungeon dungeon;

    public Exit(Dungeon dungeon, int x, int y) {
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
        Dungeon dungeon = getDungeon();
        // Set the Exit as temporarily reached
        dungeon.reachExit();
        // If all goals conditions are completed, the level is deemed as complete
        if (dungeon.goalsCompleted()) {
            System.out.println("Level is complete!");
            return true;
        }
        // Otherwise, goals must be reached before the player can access the exit
        System.out.println("Goals must be completed before entering exit");
        return false;
    }
}