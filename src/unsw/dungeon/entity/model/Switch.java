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
        // If a player interacts with a switch with a current boulder on it
        if (entity instanceof Player && isActivated()) {
            // Determine the direction that the Boulder may potentially move
            int newX = getX() - entity.getX();
            int newY = getY() - entity.getY();
            // Determine if the Boulder can be moved in the given Direction
            if (getBoulder().move(Direction.getDirection(newX, newY))) {
                // Deactivate the Floor Switch
                deactivateSwitch();
                return true;
            } 
            // Otherwise the Boulder cannot be moved in the given Direction
            else {
                return false;
            }
        } 
        // If the switch is not activated and a Boulder is being pushed onto it
        else if (!isActivated() && entity instanceof Boulder) {
            // Activate the Floor Switch with the given Boulder
            activateSwitch(entity);
            // Check if all goal conditions have been completed
            this.getDungeon().goalsCompleted();
        }
        return true;
    }
}