package unsw.dungeon;

/**
 * Leaf Goal that allows for specific checking if exit has been reached
 * 
 * @author Sean Smith
 * @author Austin Landry
 */
public class GoalExit implements Goal {

    /**
     * @return true if the exit has been reached, otherwise false
     */
    @Override
    public Boolean isCompleted(Dungeon dungeon) {
        return dungeon.checkExitReached();
    }
}