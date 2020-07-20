package unsw.dungeon;

public class GoalTreasure implements Goal {
        
    private Dungeon dungeon;
    
    public GoalTreasure(Dungeon dungeon) {
        this.dungeon = dungeon;
    }

    public Dungeon getDungeon() {
        return this.dungeon;
    }

    @Override
    public Boolean isCompleted() {
        Dungeon dungeon = getDungeon();
        long totalTreasures = dungeon.getEntities()
            .stream()
            .filter(x -> x instanceof Treasure)
            .count();
        return (totalTreasures == 0) ? true : false;
    }
}