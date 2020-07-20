package unsw.dungeon;

import java.util.ArrayList;

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