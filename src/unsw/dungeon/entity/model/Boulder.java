package unsw.dungeon.entity.model;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entity.Direction;
import unsw.dungeon.entity.Entity;
import unsw.dungeon.entity.Interactable;
import unsw.dungeon.entity.Moveable;

public class Boulder extends Entity implements Moveable, Interactable {

    private Dungeon dungeon;

    public Boulder(Dungeon dungeon, int x, int y) {
        super(x, y);
        this.dungeon = dungeon;
    }

    public Dungeon getDungeon() {
        return this.dungeon;
    }

    // entityAtPosition is just a wrapper function which is implemented the same everywhere. Why include it in the Moveable interface.
    public Entity entityAtPosition(int x, int y) {
        return this.getDungeon().getEntity(x, y);
    }

    public Boolean move(Direction direction) {
        int newX = getX() + direction.getX();
        int newY = getY() + direction.getY();

        Entity checkEntity = entityAtPosition(newX, newY);
        if (checkEntity != null) {
            return false;
        }

        if (this.dungeon.checkBoundaries(newX, newY)) {
            x().set(newX);
            y().set(newY);
            return true;
        }
        return false;
    }

    @Override
    public Boolean interact(Entity entity) {
        if (!(entity instanceof Player)) {
            return false;
        }
        int newX = getX() - entity.getX();
        int newY = getY() - entity.getY();
        return this.move(Direction.getDirection(newX, newY));
    }
}