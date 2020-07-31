package unsw.dungeon;

/**
 * Leaf Goal that allows for specific checking if all Enemies have been killed
 * 
 * @author Sean Smith
 * @author Austin Landry
 */
public class GoalEnemies implements Goal {
        
    /**
     * Determine if all enemies have been killed
     * @return true if no enemies active, otherwise false
     */
    @Override
    public Boolean isCompleted(Dungeon dungeon) {
        // Gather the count of all current Enemy entities
        long totalEnemies = dungeon.getEntities()
            .stream()
            .filter(x -> x.getClass().equals(Enemy.class))
            .count();
        // Check if there no Enemies remaining
        return (totalEnemies == 0) ? true : false;
    }
}