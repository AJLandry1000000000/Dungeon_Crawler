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
            int newX = this.getX() - entity.getX();
            int newY = this.getY() - entity.getY();

            // If the Boulder is can be moved in the given Direction
            if (getBoulder().move(Direction.getDirection(newX, newY))) {
                // Deactivate the Floor Switch
                this.deactivateSwitch();
                return true;
            } 
            // Otherwise the Boulder is cannot be moved in the given Direction
            else {
                return false;
            }
        } 
        // If the switch is not activated and a Boulder is being pushed onto it
        else if (!this.isActivated() && entity instanceof Boulder) {
            this.activateSwitch(entity);
            this.getDungeon().goalsCompleted();
        }
        return true;
    }
}