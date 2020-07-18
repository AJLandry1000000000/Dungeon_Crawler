package unsw.dungeon.entity.model;

import unsw.dungeon.entity.Entity;
import unsw.dungeon.entity.Moveable;
import unsw.dungeon.entity.Interactable;
import unsw.dungeon.Dungeon;

public class Enemy extends Entity implements Moveable, Interactable {

    private Dungeon dungeon;
    
    public Enemy(Dungeon dungeon, int x, int y) {
        super(x, y);
        this.dungeon = dungeon;
    }

    @Override
    public void moveUp() {
        if (getY() > 0)
            y().set(getY() - 1);
    }
    
    @Override
    public void moveDown() {
        if (getY() < dungeon.getHeight() - 1)
            y().set(getY() + 1);
    }

    @Override
    public void moveLeft() {
        if (getX() > 0)
            x().set(getX() - 1);
    }

    @Override
    public void moveRight() {
        if (getX() < dungeon.getWidth() - 1)
            x().set(getX() + 1);
    }
}