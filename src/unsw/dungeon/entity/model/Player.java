package unsw.dungeon.entity.model;

import unsw.dungeon.entity.Direction;
import unsw.dungeon.entity.Entity;
import unsw.dungeon.entity.Interactable;
import unsw.dungeon.entity.Moveable;
import unsw.dungeon.Dungeon;

/**
 * The player entity
 * @author Sean Smith & Austin Landry
 *
 */
public class Player extends Entity implements Moveable {

    private Dungeon dungeon;
    private Sword sword = null;

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

    public Boolean move(Direction direction) {
        int newX = getX() + direction.getX();
        int newY = getY() + direction.getY();

        // Get the entity at the new X & Y coordinates.
        Entity checkEntity = this.getDungeon().getEntity(newX, newY);

        // Assume that the player cannot interact with the new entity.
        Boolean canInteract = false;
        if (checkEntity instanceof Interactable) {
            System.out.println(checkEntity);
            // The interact method will return true if the player can interact with it.
            canInteract = ((Interactable)checkEntity).interact(this);
        }

        // If the player can interact with an entity, it can move onto the entities coordinates, so allow the player to change its coordinates to the new X & Y.
        // If the player cannot interact with an entity, maybe its because there is no entity to interact with, and the player is moving to an empty space. If the space is empty, allow the player to move there.
        // Always check that the new spot is within the dungeon boundaries.
        if ( (canInteract || checkEntity == null) && this.dungeon.checkBoundaries(newX, newY)) {
            x().set(newX);
            y().set(newY);
            return true;
        }
        return false;
    }

    public boolean hasSword() {
        return this.sword != null;
    }

    public void giveSword(Sword newSword) {
        this.sword = newSword;
    }
}
