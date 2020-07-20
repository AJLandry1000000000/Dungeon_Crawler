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
    private Dungeon dungeon;

    public GoalAND(Dungeon dungeon) {
        this.goals = new ArrayList<Goal>();
        this.dungeon = dungeon;
    }

    public void add(Goal goal) {
        this.goals.add(goal);
    }

    public Dungeon getDungeon() {
        return this.dungeon;
    }
    
    /**
     * @return true if any sub-goal is completed, otherwise false if no goals have been completed
     */
    @Override
    public Boolean isCompleted() {
        for (Goal g : goals) {
            if (!g.isCompleted()) {
                return false;
            }
        }
        return true;
    }
}