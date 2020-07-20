package unsw.dungeon;

/**
 * Leaf Goal that allows for specific checking if exit has been reached
 * 
 * @author Sean Smith
 * @author Austin Landry
 */
public class GoalExit implements Goal {

    private Dungeon dungeon;

    public GoalExit(Dungeon dungeon) {
        this.dungeon = dungeon;
    }

    public Dungeon getDungeon() {
        return this.dungeon;
    }

    /**
     * @return true if the exit has been reached, otherwise false
     */
    @Override
    public Boolean isCompleted() {
        return getDungeon().checkExitReached();
    }
}