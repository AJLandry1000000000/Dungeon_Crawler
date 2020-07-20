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

    /**
     * 
     */
    @Override
    public Boolean move(Direction direction) {
        // Calculate new position of boulder based on player's direction
        int newPositionX = getX() + direction.getX();
        int newPositionY = getY() + direction.getY();

        // Determine if there is an entity at the new position
        Entity checkEntity = getDungeon().getEntity(newPositionX, newPositionY);
        // If the entity is a floor switch, but already has a Boulder on it
        if (checkEntity instanceof Switch) {
            if (!((Interactable)checkEntity).interact(this)) {
                return false;
            }
        }
        // If the new position contains is any other entity
        else if (checkEntity != null) {
            return false;
        } 

        // If the new position is not out of bounds of the level
        if (getDungeon().checkBoundaries(newPositionX, newPositionY)) {
            // Move the Boulder onto the new position
            x().set(newPositionX);
            y().set(newPositionY);
            return true;
        }
        return false;
    }

    /**
     * 
     */
    @Override
    public Boolean interact(Entity entity) {
        // Only a Player can interact with a Boulder
        if (!(entity instanceof Player)) {
            return false;
        }

        // Determine and process the Direction the boulder will move
        int newX = getX() - entity.getX();
        int newY = getY() - entity.getY();
        return move(Direction.getDirection(newX, newY));
    }
}