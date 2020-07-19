package unsw.dungeon.entity.goals;

import unsw.dungeon.Dungeon;

public class GoalAND extends GoalComposite {
    
    private Dungeon dungeon;

    public GoalAND(Dungeon dungeon) {
        super();
        this.dungeon = dungeon;
    }

    @Override
    public Boolean completed() {
        return false;
    }
}