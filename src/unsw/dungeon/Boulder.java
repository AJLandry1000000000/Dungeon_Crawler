package unsw.dungeon;

/**
 * Boulders are used to activate Switches and manipulate the map.
 * @author Sean Smith & Austin Landry
 */
public class Boulder extends Entity implements Moveable, Interactable {

    private Dungeon dungeon;
    private Switch floor;

    public Boulder(Dungeon dungeon, int x, int y) {
        super(x, y);
        this.dungeon = dungeon;
    }

    public Dungeon getDungeon() {
        return this.dungeon;
    }

    public Switch getSwitch() {
        return this.floor;
    }

    public void addSwitch(Switch floor) {
        this.floor = floor;
    }

    public void resetSwitch() {
        this.floor = null;
    }

    public Boolean canMove(Direction direction) {
        Dungeon dungeon = getDungeon();
        // Calculate new position of boulder based on player's direction
        int newPositionX = getX() + direction.getX();
        int newPositionY = getY() + direction.getY();

        // Determine if there is an entity at the new position
        Entity checkEntity = dungeon.getEntity(newPositionX, newPositionY);
        // If the entity is a floor switch, but already has a Boulder on it
        if (checkEntity instanceof Switch) { 
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
     * 
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
     * @param entity - This is a Player. If it is anything else, don't let it interact with the Boulder.
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