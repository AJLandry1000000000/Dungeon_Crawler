package unsw.dungeon.entity.model;

import unsw.dungeon.entity.Entity;
import unsw.dungeon.entity.Moveable;
import unsw.dungeon.entity.Interactable;

public class Enemy extends Entity implements Moveable, Interactable {
    
    public Enemy(int x, int y) {
        super(x, y);
    }
}