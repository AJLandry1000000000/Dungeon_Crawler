package unsw.dungeon.entity.goals;

import unsw.dungeon.Dungeon;

public class GoalOR extends GoalComposite {
    
    private Dungeon dungeon;

    public GoalOR(Dungeon dungeon) {
        super();
        this.dungeon = dungeon;
    }

    @Override
    public Boolean completed() {
        return false;
    }
}