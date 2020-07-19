package unsw.dungeon.entity.model;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entity.Direction;
import unsw.dungeon.entity.Entity;
import unsw.dungeon.entity.Interactable;
import unsw.dungeon.entity.Moveable;

public class Enemy extends Entity implements Moveable, Interactable {

    private Dungeon dungeon;
    
    public Enemy(Dungeon dungeon, int x, int y) {
        super(x, y);
        this.dungeon = dungeon;
    }

    public Dungeon getDungeon() {
        return this.dungeon;
    }

    public Entity entityAtPosition(int x, int y) {
        return this.getDungeon().getEntity(x, y);
    }

    public void move(Direction direction) {
        int newX = getX() + direction.getX();
        int newY = getY() + direction.getY();

        Entity checkEntity = entityAtPosition(newX, newY);
        if (checkEntity != null) {
            System.out.println(checkEntity);
        }

        x().set(newX);
        y().set(newY);
    }
}