package unsw.dungeon;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Leaf Goal that allows for specific checking if all floor switches are activated
 * 
 * @author Sean Smith
 * @author Austin Landry
 */
public class GoalBoulders implements Goal {

    private Dungeon dungeon;

    public GoalBoulders(Dungeon dungeon) {
        this.dungeon = dungeon;
    }

    public Dungeon getDungeon() {
        return this.dungeon;
    }

    /**
     * Determine if every Floor Switch is activated (i.e. with a Boulder on top of it)
     * @return true if all Switches are activated, otherwise false
     */
    @Override
    public Boolean isCompleted() {
        Dungeon dungeon = getDungeon();
        // Gather all switches
        List<Entity> switches = dungeon.getEntities()
            .stream()
            .filter(x -> x instanceof Switch)
            .collect(Collectors.toList());   
        // Check if all switches are activated
        for (Entity e : switches) {
            if (!((Switch)e).isActivated()) {
                return false;
            }
        }
        return true;
    }
}