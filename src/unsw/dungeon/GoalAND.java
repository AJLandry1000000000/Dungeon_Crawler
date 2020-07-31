package unsw.dungeon;

import java.util.ArrayList;

/**
 * Composite Goal that allows for AND checking of sub goals
 * 
 * @author Sean Smith
 * @author Austin Landry
 */
public class GoalAND implements GoalComposite {
    
    private ArrayList<Goal> goals;

    public GoalAND() {
        this.goals = new ArrayList<Goal>();
    }

    public void add(Goal goal) {
        this.goals.add(goal);
    }
    
    /**
     * @return true if any sub-goal is completed, otherwise false if no goals have been completed
     */
    @Override
    public Boolean isCompleted(Dungeon dungeon) {
        for (Goal g : goals) {
            if (!g.isCompleted(dungeon)) {
                return false;
            }
        }
        return true;
    }
}