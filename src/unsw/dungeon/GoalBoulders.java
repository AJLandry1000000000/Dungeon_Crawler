package unsw.dungeon;

import java.util.List;
import java.util.stream.Collectors;

public class GoalBoulders implements Goal {

    private Dungeon dungeon;

    public GoalBoulders(Dungeon dungeon) {
        this.dungeon = dungeon;
    }

    public Dungeon getDungeon() {
        return this.dungeon;
    }

    /**
     * 
     */
    @Override
    public Boolean isCompleted() {
        Dungeon dungeon = getDungeon();
        List<Entity> switches = dungeon.getEntities()
            .stream()
            .filter(x -> x instanceof Switch)
            .collect(Collectors.toList());   
            
        for (Entity e : switches) {
            if (!((Switch)e).isActivated()) {
                return false;
            }
        }
        return true;
    }
}