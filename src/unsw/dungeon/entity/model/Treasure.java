package unsw.dungeon.entity.model;

import unsw.dungeon.entity.Entity;
import unsw.dungeon.entity.Interactable;

public class Treasure extends Entity implements Interactable {

    public Treasure(int x, int y) {
        super(x, y);
    }
}