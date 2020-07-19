package unsw.dungeon.entity.goals;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entity.model.Enemy;
import unsw.dungeon.entity.model.Player;

public class GoalEnemies implements Goal {
        
    private Dungeon dungeon;

    public GoalEnemies(Dungeon dungeon) {
        this.dungeon = dungeon;
    }

    public Dungeon getDungeon() {
        return this.dungeon;
    }

    @Override
    public Boolean completed() {
        Dungeon dungeon = getDungeon();
        long totalEnemies = dungeon.getEntities()
            .stream()
            .filter(x -> x instanceof Enemy)
            .count();
        Player player = dungeon.getPlayer();
        long enemiesDefeated = player.getAmountTreasures();
        return (totalEnemies == enemiesDefeated) ? true : false;
    }
}