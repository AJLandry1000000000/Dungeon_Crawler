package unsw.dungeon.entity.model;

import unsw.dungeon.entity.Collectable;
import unsw.dungeon.entity.Entity;
import unsw.dungeon.entity.Interactable;

public class Potion extends Entity implements Interactable, Collectable {

    public Potion(int x, int y) {
        super(x, y);
    }
}