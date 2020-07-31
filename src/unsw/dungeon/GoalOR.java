package unsw.dungeon;

import java.util.ArrayList;

/**
 * Composite Goal that allows for OR checking of sub goals
 * 
 * @author Sean Smith
 * @author Austin Landry
 */
public class GoalOR implements GoalComposite {
    
    private ArrayList<Goal> goals;

    public GoalOR() {
        this.goals = new ArrayList<Goal>();
    }

    public void add(Goal goal) {
        this.goals.add(goal);
    }

    /**
     * @return true if any sub-goals are completed, otherwise false if all sub goals are incomplete
     */
    @Override
    public Boolean isCompleted(Dungeon dungeon) {
        for (Goal g : goals) {
            if (g.isCompleted(dungeon)) {
                return true;
            }
        }
        return false;
    }
}