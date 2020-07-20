package unsw.dungeon;

import unsw.dungeon.Dungeon;
import unsw.dungeon.Enemy;

public class GoalEnemies implements Goal {
        
    private Dungeon dungeon;

    public GoalEnemies(Dungeon dungeon) {
        this.dungeon = dungeon;
    }

    public Dungeon getDungeon() {
        return this.dungeon;
    }

    @Override
    public Boolean isCompleted() {
        Dungeon dungeon = getDungeon();
        long totalEnemies = dungeon.getEntities()
            .stream()
            .filter(x -> x instanceof Enemy)
            .count();

        return (totalEnemies == 0) ? true : false;
    }
}