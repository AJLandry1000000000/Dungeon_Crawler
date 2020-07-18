package unsw.dungeon.entity.model;

import unsw.dungeon.entity.Collectable;
import unsw.dungeon.entity.Entity;
import unsw.dungeon.entity.Interactable;

public class Sword extends Entity implements Interactable, Collectable {

    public Sword(int x, int y) {
        super(x, y);
    }
}