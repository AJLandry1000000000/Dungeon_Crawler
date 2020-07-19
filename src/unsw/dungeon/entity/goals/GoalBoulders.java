package unsw.dungeon.entity.goals;

import java.util.List;
import java.util.stream.Collectors;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entity.Entity;
import unsw.dungeon.entity.model.Switch;

public class GoalBoulders implements Goal {

    private Dungeon dungeon;

    public GoalBoulders(Dungeon dungeon) {
        this.dungeon = dungeon;
    }

    public Dungeon getDungeon() {
        return this.dungeon;
    }

    @Override
    public Boolean completed() {
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