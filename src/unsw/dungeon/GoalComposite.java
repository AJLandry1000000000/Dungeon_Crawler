package unsw.dungeon;

/**
 * Interface representing the Composite Goal condition
 * 
 * @author Sean Smith
 * @author Austin Landry
 */
public interface GoalComposite extends Goal {

    public void add(Goal goal);
}
