package unsw.dungeon.entity.model;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entity.Direction;
import unsw.dungeon.entity.Entity;
import unsw.dungeon.entity.Interactable;


public class Switch extends Entity implements Interactable {

    private Dungeon dungeon;
    private Boolean activated;
    private Boulder boulder;

    public Switch(Dungeon dungeon, int x, int y) {
        super(x, y);
        this.dungeon = dungeon;
        this.activated = false;
    }

    public Dungeon getDungeon() {
        return this.dungeon;
    }

    /**
     * 
     * @param entity
     */
    public void activateSwitch(Entity entity) {
        this.boulder = (Boulder)entity;
        this.activated = true;
    }

    /**
     * 
     */
    public void deactivateSwitch() {
        this.boulder = null;
        this.activated = false;
    }

    /**
     * 
     * @return
     */
    public Boolean isActivated() {
        return this.activated;
    }

    /**
     * 
     * @return
     */
    public Boulder getBoulder() {
        return this.boulder;
    }


    /**
     * 
     */
    @Override
    public Boolean interact(Entity entity) {
        // If a Player is interacting with the Switch
        if (entity instanceof Player) {
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
                }
            } 
            return true;
        } 
        // If a Boulder is interacting with the Switch
        else if (entity instanceof Boulder) {
            // If the Switch does not have a current Boulder on it
            if (!isActivated()) {
                // Activate the Floor Switch with the given Boulder
                activateSwitch(entity);
                // Check if all goal conditions have been completed
                getDungeon().goalsCompleted();
                return true;
            } 
        }
        return false;
    }
}