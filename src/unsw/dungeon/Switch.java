package unsw.dungeon;

/**
 * Floor Switch entities that allow Boulders to be pushed on top of them in order to complete Goals
 * @author Sean Smith
 * @author Austin Landry
 */
public class Switch extends Entity implements Interactable {

    private Boolean activated;
    private Boulder boulder;

    public Switch(int x, int y, Dungeon dungeon) {
        super(x, y, dungeon);
        this.activated = false;
    }

    /**
     * @param entity Boulder entity to be saved
     */
    public void activateSwitch(Boulder boulder) {
        this.boulder = boulder;
        this.activated = true;
    }

    /**
     * Remove saved Boulder and change Floor Switch activation state
     */
    public void deactivateSwitch() {
        this.boulder = null;
        this.activated = false;
    }

    /**
     * @return the saved Boulder that is on top of the Floor Switch Entity
     */
    public Boulder getBoulder() {
        return this.boulder;
    }

    /**
     * @return true if Switch is activated, otherwise false
     */
    public Boolean isActivated() {
        return this.activated;
    }

    /**
     * Interaction method that allows a Player or Boulder to either walk over or push existing Boulder
     * @param entity - This is a Player. If it is anything else, don't let it interact with the Switch.
     */
    @Override
    public Boolean interact(Entity entity) {
        // If a Player is interacting with the Switch
        if (entity.getClass().equals(Player.class)) {
            // If the Switch is not activated
            if (isActivated()) {
                int newX = getX() - entity.getX();
                int newY = getY() - entity.getY();
                Boulder boulder = getBoulder();
                // Determine if the Boulder can be moved in the given Direction
                if (boulder.canMove(Direction.getDirection(newX, newY))) {
                    // Deactivate the Floor Switch
                    deactivateSwitch();
                    // Move the Boulder off the switch
                    boulder.move(Direction.getDirection(newX, newY));
                    dungeon.setConsoleText("Floor Switch has been deactivated");
                } else {
                    dungeon.setConsoleText("Path is blocked for Boulder move");
                    return false;
                }
            } 
            return true;
        } 
        // If a Boulder is interacting with the Switch
        else if (entity.getClass().equals(Boulder.class)) {
            // If the Switch does not have a current Boulder on it
            if (!isActivated()) {
                // Activate the Floor Switch with the given Boulder
                activateSwitch((Boulder)entity);
                // Check if all goal conditions have been completed
                getDungeon().goalsCompleted();
                dungeon.setConsoleText("Player has activated a Floor Switch");
                return true;
            } 
        }
        return false;
    }
}