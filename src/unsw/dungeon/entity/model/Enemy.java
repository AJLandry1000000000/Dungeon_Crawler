package unsw.dungeon.entity.model;

import unsw.dungeon.entity.Entity;
import unsw.dungeon.entity.Interactable;
import unsw.dungeon.entity.Moveable;

public class Enemy extends Entity implements Moveable, Interactable {
    
    public Enemy(int x, int y) {
        super(x, y);
    }
}