package unsw.dungeon;

/**
 * Boulder entity is used to activate Switches and manipulate the level.
 * Boulders are able to Move and be Interacted with
 * @author Sean Smith
 * @author Austin Landry
 */
public class Boulder extends Entity implements Moveable, Interactable {

    private Switch floor;

    public Boulder(int x, int y, Dungeon dungeon) {
        super(x, y, dungeon);
        this.floor = null;
    }


    public Switch getSwitch() {
        return this.floor;
    }

    /**
     * Add a Floor Switch to the Boulder Entity
     */
    public void addSwitch(Switch floor) {
        this.floor = floor;
    }

    /**
     * Remove Floor Switch from the Boulder Entity
     */
    public void resetSwitch() {
        this.floor = null;
    }

    /**
     * Check if the Boulder is allowed to move in a desired Direction
     */
    public Boolean canMove(Direction direction) {
        Dungeon dungeon = getDungeon();
        // Calculate new position of boulder based on player's direction
        int newPositionX = getX() + direction.getX();
        int newPositionY = getY() + direction.getY();

        // Determine if there is an entity at the new position
        Entity checkEntity = dungeon.getEntity(newPositionX, newPositionY);
        // If the entity is a floor switch, but already has a Boulder on it
        if (checkEntity.getClass().equals(Switch.class)) { 
            if (((Switch)checkEntity).isActivated()) {
                return false;
            }
        }
        // If the new position contains any other entity
        else if (checkEntity != null) {
            return false;
        } 

        // If the new position is not out of bounds of the level
        if (dungeon.checkBoundaries(newPositionX, newPositionY)) {
            return true;
        }
        return false;
    }

    /**
     * Move the boulder to a new position based on a given Direction
     */
    @Override
    public Boolean move(Direction direction) {
        Dungeon dungeon = getDungeon();
        // Calculate new position of boulder based on player's direction
        int newPositionX = getX() + direction.getX();
        int newPositionY = getY() + direction.getY();

        // Determine if there is an entity at the new position
        Entity checkEntity = dungeon.getEntity(newPositionX, newPositionY);

        // If the entity is a floor switch, but already has a Boulder on it
        if (checkEntity instanceof Switch) {
            if (!((Switch)checkEntity).interact(this)) {
                return false;
            }
        }
        // If the new position contains any other entity
        else if (checkEntity != null) {
            return false;
        } 

        // If the new position is not out of bounds of the level
        if (dungeon.checkBoundaries(newPositionX, newPositionY)) {
            // Move the Boulder onto the new position
            x().set(newPositionX);
            y().set(newPositionY);

            // Update former Switch's activation
            if (getSwitch() != null) {
                getSwitch().deactivateSwitch();
                resetSwitch();
            }
            // If boulder is moving onto a switch
            if (checkEntity instanceof Switch) {
                addSwitch((Switch)checkEntity);
            }
            return true;
        }
        return false;
    }

    /**
     * Process Entity interaction when an Entity interacts with a Boulder
     * @param entity Entity that will interact with a Boulder where only a Player is allowed to
     */
    @Override
    public Boolean interact(Entity entity) {
        // Only a Player can interact with a Boulder
        if (!(entity.getClass().equals(Player.class))) {
            return false;
        }

        // Determine and process the Direction the boulder will move
        int newX = getX() - entity.getX();
        int newY = getY() - entity.getY();
        return move(Direction.getDirection(newX, newY));
    }
}