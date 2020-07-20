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
    private Dungeon dungeon;

    public GoalOR(Dungeon dungeon) {
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
     * @return true if any sub-goals are completed, otherwise false if all sub goals are incomplete
     */
    @Override
    public Boolean isCompleted() {
        for (Goal g : goals) {
            if (g.isCompleted()) {
                return true;
            }
        }
        return false;
    }
}