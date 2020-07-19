package unsw.dungeon.entity.model;

import unsw.dungeon.entity.Direction;
import unsw.dungeon.entity.Entity;
import unsw.dungeon.entity.Interactable;
import unsw.dungeon.entity.Moveable;
import unsw.dungeon.Dungeon;

/**
 * The player entity
 * @author Robert Clifton-Everest
 *
 */
public class Player extends Entity implements Moveable {

    private Dungeon dungeon;

    /**
     * Create a player positioned in square (x,y)
     * @param x
     * @param y
     */
    public Player(Dungeon dungeon, int x, int y) {
        super(x, y);
        this.dungeon = dungeon;
    }

    public Dungeon getDungeon() {
        return this.dungeon;
    }

    public Entity entityAtPosition(int x, int y) {
        return this.getDungeon().getEntity(x, y);
    }

    public Boolean move(Direction direction) {
        int newX = getX() + direction.getX();
        int newY = getY() + direction.getY();

        Entity checkEntity = entityAtPosition(newX, newY);
        Boolean canInteract = true;
        if (checkEntity instanceof Interactable) {
            System.out.println(checkEntity);
            canInteract = ((Interactable)checkEntity).interact(this);
        }

        if (canInteract && this.dungeon.checkBoundaries(newX, newY)) {
            x().set(newX);
            y().set(newY);
            return true;
        }
        return false;
    }
}
