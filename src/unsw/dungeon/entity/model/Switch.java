package unsw.dungeon.entity.model;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entity.Direction;
import unsw.dungeon.entity.Entity;
import unsw.dungeon.entity.Interactable;


public class Switch extends Entity implements Interactable {

    private Boolean activated;
    private Boulder boulder;
    private Dungeon dungeon;

    public Switch(Dungeon dungeon, int x, int y) {
        super(x, y);
        this.activated = false;
        this.dungeon = dungeon;
    }

    public Dungeon getDungeon() {
        return this.dungeon;
    }

    public void activateSwitch(Entity entity) {
        this.boulder = (Boulder)entity;
        this.activated = true;
    }

    public void deactivateSwitch() {
        this.boulder = null;
        this.activated = false;
    }

    public Boolean isActivated() {
        return this.activated;
    }

    public Boulder getBoulder() {
        return this.boulder;
    }

    @Override
    public Boolean interact(Entity entity) {
        // If a player walks on a switch with a boulder on it
        if (entity instanceof Player && isActivated()) {
            int newX = this.getX() - entity.getX();
            int newY = this.getY() - entity.getY();
            // Check if boulder can be moved
            if (getBoulder().move(Direction.getDirection(newX, newY))) {
                this.deactivateSwitch();
                return true;
            } else {
                return false;
            }
        } 
        // If a boulder is pushed onto an empty switch
        else if (entity instanceof Boulder && !isActivated()) {
            this.activateSwitch(entity);
            this.getDungeon().goalsCompleted();
        }
        return true;
    }
}