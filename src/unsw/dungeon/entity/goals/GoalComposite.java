package unsw.dungeon.entity.goals;

import java.util.ArrayList;

public abstract class GoalComposite implements Goal {
    
    private ArrayList<Goal> goals;

    public GoalComposite() {
        this.goals = new ArrayList<Goal>();
    }

    public void add(Goal goal) {
        this.goals.add(goal);
    }
}
