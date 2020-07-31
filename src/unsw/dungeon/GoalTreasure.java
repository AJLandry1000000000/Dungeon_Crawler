package unsw.dungeon;

/**
 * Leaf Goal that allows for specific checking if all Treasure has been collected
 * 
 * @author Sean Smith
 * @author Austin Landry
 */
public class GoalTreasure implements Goal {
        
    /**
     * Determine if all Treasures have been acquired
     * @return true if no Treasures left, otherwise false
     */
    @Override
    public Boolean isCompleted(Dungeon dungeon) {
        // Gather the count of all Treasure entities
        long totalTreasures = dungeon.getEntities()
            .stream()
            .filter(x -> x instanceof Treasure)
            .count();
        // Check if there are no Treasure entities remaining
        return (totalTreasures == 0) ? true : false;
    }
}