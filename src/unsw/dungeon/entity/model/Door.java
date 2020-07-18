package unsw.dungeon.entity.model;

import unsw.dungeon.entity.Entity;
import unsw.dungeon.entity.Interactable;

public class Door extends Entity implements Interactable {

    public Door(int x, int y) {
        super(x, y);
    }
}