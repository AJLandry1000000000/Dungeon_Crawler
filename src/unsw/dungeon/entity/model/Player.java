package unsw.dungeon.entity.model;

import unsw.dungeon.entity.Direction;
import unsw.dungeon.entity.Entity;
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

    public void move(Direction direction) {
        int newX = getX() + direction.getX();
        int newY = getY() + direction.getY();

        Entity checkEntity = entityAtPosition(newX, newY);
        if (checkEntity != null) {
            System.out.println(checkEntity);
        }

        x().set(newX);
        y().set(newY);
    }
}
