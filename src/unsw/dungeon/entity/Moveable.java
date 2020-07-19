package unsw.dungeon.entity;

import unsw.dungeon.Dungeon;

public interface Moveable {

    public Dungeon getDungeon();
    public Entity entityAtPosition(int x, int y);
    public Boolean move(Direction direction);

}