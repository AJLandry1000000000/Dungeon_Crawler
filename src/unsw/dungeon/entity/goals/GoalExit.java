package unsw.dungeon.entity.goals;

import unsw.dungeon.Dungeon;

public class GoalExit implements Goal {

    private Dungeon dungeon;

    public GoalExit(Dungeon dungeon) {
        this.dungeon = dungeon;
    }

    public Dungeon getDungeon() {
        return this.dungeon;
    }

    @Override
    public Boolean completed() {
        Dungeon dungeon = getDungeon();
        return (dungeon.checkLevelCompleted()) ? true : false;
    }
}