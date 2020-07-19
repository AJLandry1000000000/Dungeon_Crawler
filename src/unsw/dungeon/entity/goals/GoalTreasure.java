package unsw.dungeon.entity.goals;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entity.model.Player;
import unsw.dungeon.entity.model.Treasure;

public class GoalTreasure implements Goal {
        
    private Dungeon dungeon;
    
    public GoalTreasure(Dungeon dungeon) {
        this.dungeon = dungeon;
    }

    public Dungeon getDungeon() {
        return this.dungeon;
    }

    @Override
    public Boolean completed() {
        Dungeon dungeon = getDungeon();
        long totalTreasures = dungeon.getEntities()
            .stream()
            .filter(x -> x instanceof Treasure)
            .count();
        Player player = dungeon.getPlayer();
        long playerTreasures = player.getAmountTreasures();
        return (totalTreasures == playerTreasures) ? true : false;
    }
}