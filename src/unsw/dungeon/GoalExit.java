package unsw.dungeon;

public class GoalExit implements Goal {

    private Dungeon dungeon;

    public GoalExit(Dungeon dungeon) {
        this.dungeon = dungeon;
    }

    public Dungeon getDungeon() {
        return this.dungeon;
    }

    @Override
    public Boolean isCompleted() {
        return getDungeon().checkExitReached();
    }
}