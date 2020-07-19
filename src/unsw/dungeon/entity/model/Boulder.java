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

    public Boolean move(Direction direction) {
        int newPositionX = this.getX() + direction.getX();
        int newPositionY = this.getY() + direction.getY();

        Entity checkEntity = this.getDungeon().getEntity(newPositionX, newPositionY);
        
        // Check if the entity is a floor switch
        
        if (checkEntity instanceof Switch) {
            if (!((Interactable)checkEntity).interact(this)) {
                return false;
            }
        }
        else if (checkEntity != null) {
            return false;
        } 

        if (this.dungeon.checkBoundaries(newPositionX, newPositionY)) {
            x().set(newPositionX);
            y().set(newPositionY);
            return true;
        }
        return false;
    }

    @Override
    public Boolean interact(Entity entity) {
        if (!(entity instanceof Player)) {
            return false;
        }
        int newX = this.getX() - entity.getX();
        int newY = this.getY() - entity.getY();
        return this.move(Direction.getDirection(newX, newY));
    }
}