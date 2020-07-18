package unsw.dungeon.entity.model;

import unsw.dungeon.entity.Moveable;
import unsw.dungeon.entity.Interactable;
import unsw.dungeon.entity.Entity; 

public class Boulder extends Entity implements Moveable, Interactable {
    
    public Boulder(int x, int y) {
        super(x, y);
    }
}