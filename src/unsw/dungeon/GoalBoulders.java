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

    /**
     * Determine if every Floor Switch is activated (i.e. with a Boulder on top of it)
     * @return true if all Switches are activated, otherwise false
     */
    @Override
    public Boolean isCompleted(Dungeon dungeon) {
        // Gather all switches
        List<Entity> switches = dungeon.getEntities()
            .stream()
            .filter(x -> x.getClass().equals(Switch.class))
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